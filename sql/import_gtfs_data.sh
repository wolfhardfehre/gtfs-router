#!/bin/bash -e

# you should have a user with system name inside PostgreSQL

# createuser $USER -s -P
dropdb gtfs_production
createdb -E UTF8 -O $USER gtfs_production
psql -d gtfs_production -f schema.sql

psql -d gtfs_production -c 'COPY stops FROM STDIN WITH CSV HEADER' < ../data/VBB-GTFS/stops.txt
psql -d gtfs_production -c 'COPY agencies FROM STDIN WITH CSV HEADER' < ../data/VBB-GTFS/agency.txt
psql -d gtfs_production -c 'COPY routes FROM STDIN WITH CSV HEADER' < ../data/VBB-GTFS/routes.txt
psql -d gtfs_production -c 'COPY calendar FROM STDIN WITH CSV HEADER' < ../data/VBB-GTFS/calendar.txt
psql -d gtfs_production -c 'COPY calendar_dates FROM STDIN WITH CSV HEADER' < ../data/VBB-GTFS/calendar_dates.txt
psql -d gtfs_production -c 'COPY trips FROM STDIN WITH CSV HEADER' < ../data/VBB-GTFS/trips.txt
psql -d gtfs_production -c 'COPY stop_times FROM STDIN WITH CSV HEADER' < ../data/VBB-GTFS/stop_times.txt
psql -d gtfs_production -c 'COPY transfers FROM STDIN WITH CSV HEADER' < ../data/VBB-GTFS/transfers.txt
psql -d gtfs_production -c 'COPY shapes FROM STDIN WITH CSV HEADER' < ../data/VBB-GTFS/shapes.txt
