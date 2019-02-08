package nice.fontaine.persistence

import nice.fontaine.Agencies
import nice.fontaine.Routes
import nice.fontaine.Stops
import nice.fontaine.Trips
import nice.fontaine.models.csv.CsvAgency
import nice.fontaine.models.csv.CsvRoute
import nice.fontaine.models.csv.CsvStop
import nice.fontaine.models.csv.CsvTrip
import nice.fontaine.models.postgresql.Credentials
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgis.Point

const val POSTGRESQL_DRIVER = "org.postgresql.Driver"

class DataStorage(private val credentials: Credentials) {

    fun connect() {
        val url = "jdbc:postgresql://${credentials.host}:${credentials.port}/${credentials.db}"
        Database.connect(url, driver = POSTGRESQL_DRIVER, user = credentials.user, password = credentials.pw)
    }

    fun reset() {
        transaction {
            SchemaUtils.drop(Agencies, Routes, Trips, Stops)
            SchemaUtils.create(Agencies, Routes, Trips, Stops)
        }
    }

    fun insertAgencies(agencies: MutableList<CsvAgency>) {
        transaction {
            agencies.forEach { agency ->
                Agencies.insert {
                    it[id] = agency.id
                    it[name] = agency.name
                    it[url] = agency.url
                    it[timezone] = agency.tZone
                    it[language] = agency.language
                    it[phone] = agency.phone
                }
            }
        }
    }

    fun insertRoutes(routes: MutableList<CsvRoute>) {
        transaction {
            routes.forEach { route ->
                Routes.insert {
                    it[id] = route.id
                    it[agencyId] = route.agencyId
                    it[shortName] = route.shortName
                    it[longName] = route.longName
                    it[routeType] = route.type
                    it[color] = route.color
                    it[textColor] = route.textColor
                    it[description] = route.description
                }
            }
        }
    }

    fun insertTrips(trips: MutableList<CsvTrip>) {
        transaction {
            trips.forEach { trip ->
                try {
                    Trips.insert {
                        it[id] = trip.id
                        it[routeId] = trip.routeId
                        it[serviceId] = trip.serviceId
                        it[tripShortName] = trip.shortName
                        it[tripHeadsign] = trip.headsign
                        it[directionId] = trip.directionId
                        it[blockId] = trip.blockId
                        it[shapeId] = trip.shapeId
                        it[wheelchairAccessible] = trip.wheelchairAccessible
                        it[bikesAllowed] = trip.bikesAllowed
                    }
                } catch (e: Exception) {
                    println("trip: ${trip.id}")
                }

            }
        }
    }

    fun insertStops(stops: MutableList<CsvStop>) {
        transaction {
            stops.forEach { stop ->
                Stops.insert {
                    it[id] = stop.id
                    it[name] = stop.name
                    it[code] = stop.code
                    it[description] = stop.description
                    it[locationType] = stop.locationType
                    it[parentStation] = stop.parentStation
                    it[geom] = Point(stop.lon, stop.lat)
                }
            }
        }
    }
}
