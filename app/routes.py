from flask import request, jsonify
import os
import sys
from shapely.geometry import Point
from app.app import app

sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '../..')))


@app.route('/route')
def route():
    raw_start_position = Point(float(request.form['start_lon']), float(request.form['start_lat']))
    raw_end_position = Point(float(request.form['end_lon']), float(request.form['end_lat']))

    start_station = g.get_closest_node(raw_start_position)
    end_station = g.get_closest_node(raw_end_position)

    connection = g.shortest_path(start_station, end_station)
    return jsonify(connection)


if __name__ == '__main__':
    g = None #Graph.load_default()
    app.run()
