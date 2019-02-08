#!/bin/bash -e

# you should have a user with system name inside PostgreSQL

# createuser $USER -s -P
dropdb gtfs_production
createdb -E UTF8 -O $USER gtfs_production
psql -d gtfs_production -f schema.sql

psql -d gtfs_production -c 'COPY stops FROM STDIN WITH CSV HEADER' < ../data/partial/stops.txt
psql -d gtfs_production -c 'COPY agencies FROM STDIN WITH CSV HEADER' < ../data/partial/agency.txt
psql -d gtfs_production -c 'COPY routes FROM STDIN WITH CSV HEADER' < ../data/partial/routes.txt
psql -d gtfs_production -c 'COPY calendar FROM STDIN WITH CSV HEADER' < ../data/partial/calendar.txt
psql -d gtfs_production -c 'COPY calendar_dates FROM STDIN WITH CSV HEADER' < ../data/partial/calendar_dates.txt
psql -d gtfs_production -c 'COPY trips FROM STDIN WITH CSV HEADER' < ../data/partial/trips.txt
psql -d gtfs_production -c 'COPY stop_times FROM STDIN WITH CSV HEADER' < ../data/partial/stop_times.txt
psql -d gtfs_production -c 'COPY transfers FROM STDIN WITH CSV HEADER' < ../data/partial/transfers.txt
psql -d gtfs_production -c 'COPY shapes FROM STDIN WITH CSV HEADER' < ../data/partial/shapes.txt
