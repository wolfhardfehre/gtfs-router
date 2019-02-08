from collections import defaultdict

import pandas as pd
 

class Graph:

    def __init__(self, merged_tables):      
        self.graph = self._build_adjacancies(merged_tables)

    def _build_adjacancies(self, merged_tables):

        adjacancy_dict = defaultdict(list)

        trip_groups = merged_tables.groupby('trip_id')

        merged_tables[['to_stop_id', 'to_arrival_time']] = trip_groups[['stop_id', 'arrival_time']].shift(-1)
        merged_tables = merged_tables.dropna(subset = ['to_stop_id', 'to_arrival_time'])
        merged_tables['weight'] = (pd.to_datetime(merged_tables['to_arrival_time']) - pd.to_datetime(merged_tables['arrival_time'])).dt.total_seconds()

        for _, row in merged_tables.iterrows():            
            adjacancy_dict[row.stop_id].append((row.to_stop_id, row.weight, row.arrival_time, row.trip_id))

        for stop_id, node_adjacancies in adjacancy_dict.items():
            node_adjacancies.sort(key = lambda x: x[1])
            adjacancy_dict[stop_id] = list(map(
                lambda x: {'to_stop_id': int(x[0]), 'weight': x[1], 'arrival_time': x[2], 'trip_id' : x[3]}, node_adjacancies))

        return adjacancy_dict

 
class Dijkstra:

    def __init__(self, graph, source_id, trip_id, started_at, dest_id):
        self.graph = graph.graph
        self.nodes = self._nodes()
        self.not_visited = self.nodes.copy()
        self.min_distances = dict(zip(self.nodes, [float('inf')] * len(self.nodes)))
        self.dest_id = dest_id
        self.shortest_path = []
        self.total_distance = float('inf')
        self.visit(source_id, 0, [] + [(source_id, trip_id)], started_at)


    def _nodes(self):
        s = set(self.graph.keys())
        for node_id in s.copy():
            for node in self.graph[node_id]:
                s.add(node['to_stop_id'])
        return list(s)

    def visit(self, node_id, total_distance, current_sequence, current_time):
        # import pdb; pdb.set_trace()
        if self.shortest_path:
            return self.shortest_path
        self.not_visited.remove(node_id)
        if node_id == self.dest_id:
            self.shortest_path = current_sequence
            self.total_distance = total_distance
            return
        if total_distance > self.min_distances[node_id]:
            return
        self.min_distances[node_id] = total_distance

        for next_node in self.graph[node_id]:
            next_node_id = next_node['to_stop_id']
            next_node_trip_id = next_node['trip_id']
            distance_to_next_node = next_node['weight']

            if next_node_id in self.not_visited:
                self.visit(next_node_id, distance_to_next_node + total_distance, current_sequence + [(next_node_id, next_node_trip_id)], current_time)

    def time_to_next_node(node_id, next_node):
        pass
    
#  1  function Dijkstra(Graph, source):
#  2
#  3      create vertex set Q
#  4
#  5      for each vertex v in Graph:             // Initialization
#  6          dist[v] ← INFINITY                  // Unknown distance from source to v
#  7          prev[v] ← UNDEFINED                 // Previous node in optimal path from source
#  8          add v to Q                          // All nodes initially in Q (unvisited nodes)
#  9
# 10      dist[source] ← 0                        // Distance from source to source
# 11      
# 12      while Q is not empty:
# 13          u ← vertex in Q with min dist[u]    // Node with the least distance
# 14                                              // will be selected first
# 15          remove u from Q 
# 16          
# 17          for each neighbor v of u:           // where v is still in Q.
# 18              alt ← dist[u] + length(u, v)
# 19              if alt < dist[v]:               // A shorter path to v has been found
# 20                  dist[v] ← alt 
# 21                  prev[v] ← u 
# 22
# 23      return dist[], prev[]