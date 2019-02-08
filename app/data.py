import pandas as pd


def main():
    data = read_data()
    routes_with_stops = merge_tables(data)
    return routes_with_stops


def read_data():
    tables = ['routes', 'trips', 'stop_times', 'stops']
    data = {}
    for table in tables:
        data[table] = pd.read_csv(f'partial/{table}.txt')
    return data

def merged_tables(data):
    return data['routes'].merge(
        data['trips'], on='route_id', how='inner').merge(
            data['stop_times'], on='trip_id', how='inner').merge(
                data['stops'], on='stop_id', how='inner')

if __name__ == '__main__':
    main()