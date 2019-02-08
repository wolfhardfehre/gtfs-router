import pandas as pd


def main():
    data = read_data()


def read_data():
    tables = ['routes', 'trips', 'stop_times', 'stops']
    data = {}
    for table in tables:
        data[table] = pd.read_csv(f'partial/{table}.txt')
    return data


if __name__ == '__main__':
    main()