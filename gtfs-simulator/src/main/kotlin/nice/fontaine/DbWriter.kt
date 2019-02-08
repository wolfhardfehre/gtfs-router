package nice.fontaine

import nice.fontaine.models.csv.CsvAgency
import nice.fontaine.models.csv.CsvRoute
import nice.fontaine.models.csv.CsvStop
import nice.fontaine.models.csv.CsvTrip
import nice.fontaine.models.postgresql.PgPassReader
import nice.fontaine.models.postgresql.interval
import nice.fontaine.models.postgresql.point
import nice.fontaine.persistence.DataStorage
import org.jetbrains.exposed.sql.*

object Agencies : Table() {
    val id = integer("id").primaryKey()
    val name = varchar("name", 60)
    val url = varchar("url", 100)
    val timezone = varchar("timezone", 30)
    val language = varchar("language", 5)
    val phone = (varchar("phone", 10)).nullable()
}

object Calendar : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val serviceId = integer("service_id").references(Trips.serviceId)
    val monday = bool("monday")
    val tuesday = bool("tuesday")
    val wednesday = bool("wednesday")
    val thursday = bool("thursday")
    val friday = bool("friday")
    val saturday = bool("saturday")
    val sunday = bool("sunday")
    val startDate = date("start_date")
    val endDate = date("end_date")
}

object CalendarDates : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val serviceId = integer("service_id").references(Trips.serviceId)
    val date = date("date")
    val exceptionType = integer("exception_type")
}

object Routes : Table() {
    val id = varchar("id", 10).primaryKey()
    val agencyId = integer("agency_id").references(Agencies.id)
    val shortName = varchar("short_name", 10)
    val longName = (varchar("long_name", 50)).nullable()
    val routeType = integer("route_type")
    val color = (varchar("color", 10)).nullable()
    val textColor = (varchar("textColor", 10)).nullable()
    val description = (varchar("description", 10)).nullable()
}

object Shapes : Table() {
    val id = integer("id").primaryKey().references(Trips.shapeId)
    val geom = point("geom", srid = 4326).index("GIST")
    val sequenceId = integer("sequence_id")
}

object Stops : Table() {
    val id = varchar("id", 14).primaryKey()
    val code = (varchar("code", 10)).nullable()
    val name = varchar("name", 100)
    val description = (varchar("description", 100)).nullable()
    val locationType = integer("location_type")
    val parentStation = varchar("parent_station", 14)
    val geom = point("geom", srid = 4326).index("GIST")
}

object StopTimes : Table() {
    val id = varchar("id", 14).primaryKey()
    val tripId = varchar("trip_id", 9).references(Trips.id)
    val stop_id = varchar("stop_id", 14).references(Stops.id)
    val stopSequence = integer("stop_sequence")
    val arrivalTime = interval("arrival_time")
    val departureTime = interval("departure_time")
    val pickupType = integer("pickup_type")
    val dropOffType = integer("drop_off_type")
    val stopHeadsign = varchar("stop_headsign", 100)
}

object Transfers : Table() {
    val id = varchar("id", 14).primaryKey()
    val fromStopId = varchar("from_stop_id", 14).references(Stops.id)
    val toStopId = varchar("to_stop_id", 14).references(Stops.id)
    val fromRouteId = varchar("from_route_id", 10).references(Routes.id)
    val toRouteId = varchar("to_route_id", 10).references(Routes.id)
    val fromTripId = varchar("from_trip_id", 9).references(Trips.id)
    val toTripId = varchar("to_trip_id", 9).references(Trips.id)
    val minTransferTime = integer("min_transfer_time")
    val transferType = integer("transfer_type")
}

object Trips : Table() {
    val id = varchar("id", 9).primaryKey()
    val routeId = varchar("route_id", 9).references(Routes.id)
    val serviceId = integer("service_id").index()
    val tripShortName = (varchar("trip_short_name", 100)).nullable()
    val tripHeadsign = (varchar("trip_headsign", 100)).nullable()
    val directionId = integer("direction_id")
    val blockId = (integer("block_id")).nullable()
    val shapeId = integer("shape_id").index()
    val wheelchairAccessible = bool("wheelchair_accessible")
    val bikesAllowed = bool("bikes_allowed")
}

fun main(args: Array<String>) {
    val dir = "partial"

    val credentials = PgPassReader.getDbCredentials("gtfs")

    val storage = DataStorage(credentials)
    storage.connect()
    storage.reset()

    val reader = GtfsReader()

    val agencies = reader.read("$dir/agency.txt", CsvAgency::class.java)
    storage.insertAgencies(agencies)

    val routes = reader.read("$dir/routes.txt", CsvRoute::class.java)
    storage.insertRoutes(routes)

    val trips = reader.read("$dir/trips.txt", CsvTrip::class.java)
    storage.insertTrips(trips)

    val stops = reader.read("$dir/stops.txt", CsvStop::class.java)
    storage.insertStops(stops)
}
