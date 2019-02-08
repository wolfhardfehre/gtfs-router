CREATE EXTENSION postgis;

DROP TABLE IF EXISTS stops CASCADE;
DROP TABLE IF EXISTS agencies CASCADE;
DROP TABLE IF EXISTS routes CASCADE;
DROP TABLE IF EXISTS calendar CASCADE;
DROP TABLE IF EXISTS calendar_dates CASCADE;
DROP TABLE IF EXISTS trips CASCADE;
DROP TABLE IF EXISTS stop_times CASCADE;
DROP TABLE IF EXISTS transfers CASCADE;
DROP TABLE IF EXISTS shapes CASCADE;

DROP INDEX IF EXISTS stop_id_index CASCADE;
DROP INDEX IF EXISTS agency_id_index CASCADE;
DROP INDEX IF EXISTS route_id_index CASCADE;
DROP INDEX IF EXISTS calendar_id_index CASCADE;
DROP INDEX IF EXISTS calendar_date_id_index CASCADE;
DROP INDEX IF EXISTS trip_id_index CASCADE;
DROP INDEX IF EXISTS stop_times_id_index CASCADE;
DROP INDEX IF EXISTS transfer_id_index CASCADE;
DROP INDEX IF EXISTS shape_id_index CASCADE;

----------------------------
-- create tables and indices
----------------------------
 
create table agencies (
	agency_id integer PRIMARY KEY,
	agency_name char(100) NOT NULL,
	agency_url char(100) NOT NULL,
	agency_timezone char(30) NOT NULL,
	agency_lang char(10),
    agency_phone char(20)
);
CREATE INDEX agency_id_index ON agencies (agency_id);

create table routes (
	route_id char(30) PRIMARY KEY,
	agency_id integer references agencies(agency_id),
	route_short_name char(30),
	route_long_name char(100),
	route_desc char(30),
	route_type char(10),
	route_color char(8),
	route_text_color char(8)
);
CREATE INDEX route_id_index ON routes (route_id);

create table stops (
	stop_id char(30) PRIMARY KEY,
    stop_code char(30),
	stop_name char(100) NOT NULL,
	stop_desc char(100),
	stop_lat double precision NOT NULL,
	stop_lon double precision NOT NULL,
	location_type integer,
	parent_station char(30)
);
CREATE INDEX stop_id_index ON stops (stop_id);

create table trips (
    trip_id char(30) PRIMARY KEY,
	route_id char(30),
	service_id integer NOT NULL,
	trip_short_name char(100) NOT NULL,
	trip_headsign char(100) NOT NULL,
	direction_id integer NOT NULL,
	block_id integer,
	shape_id integer,
	wheelchair_accessible boolean NOT NULL,
	bikes_allowed boolean NOT NULL
);
CREATE INDEX trip_id_index ON trips (trip_id);

create table stop_times (
	trip_id char(30) references trips(trip_id),
	arrival_time time NOT NULL,
	departure_time time NOT NULL,
	stop_id char(30) references stops(stop_id),
	stop_sequence integer NOT NULL,
	stop_headsign char(100),
	pickup_type integer NOT NULL,
	drop_off_type char(50) NOT NULL
);
CREATE INDEX stop_times_id_index ON stop_times (stop_id);

create table calendar (
	service_id integer PRIMARY KEY,
	monday boolean NOT NULL,
	tuesday boolean NOT NULL,
	wednesday boolean NOT NULL,
	thursday boolean NOT NULL,
	friday boolean NOT NULL,
	saturday boolean NOT NULL,
	sunday boolean NOT NULL,
	start_date integer NOT NULL,
	end_date integer NOT NULL
);
CREATE INDEX calendar_id_index ON calendar (service_id);

create table calendar_dates (
	service_id integer NOT NULL,
	date char(10) NOT NULL,
	exception_type integer NOT NULL
);
CREATE INDEX calendar_date_id_index ON calendar_dates (service_id);

create table shapes (
	shape_id integer PRIMARY KEY,
	shape_pt_sequence integer NOT NULL,
	shape_pt_lat double precision NOT NULL,
	shape_pt_lon double precision NOT NULL
);
CREATE INDEX shape_id_index ON shapes (shape_id);

create table transfers (
	id integer PRIMARY KEY,
	from_stop_id char(30) NOT NULL,
	to_stop_id char(30) NOT NULL,
	transfer_type integer NOT NULL,
	min_transfer_time integer NOT NULL,
	from_route_id char(30),
	to_route_id char(30),
	from_trip_id char(30),
	to_trip_id char(30)
);
CREATE INDEX transfer_id_index ON transfers (id);
