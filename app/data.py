import pandas as pd
from sqlalchemy import create_engine
import getpass

engine = create_engine(f'postgresql://{getpass.getuser()}@localhost:5432/gtfs_production')


def read_data():
    tables = ['routes', 'trips', 'stop_times', 'stops']
    data = {}
    for table in tables:
        data[table] = pd.read_sql_query(f'SELECT * FROM {table}', con=engine)
        #data[table] = pd.read_csv(f'partial/{table}.txt')
    return data


def merge_tables(data):
    return data['routes'].merge(
        data['trips'], on='route_id', how='inner').merge(
        data['stop_times'], on='trip_id', how='inner').merge(
        data['stops'], on='stop_id', how='inner')


def main():
    data = read_data()
    routes_with_stops = merge_tables(data)
    return routes_with_stops


if __name__ == '__main__':
    print(main())
