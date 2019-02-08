package nice.fontaine

import com.opencsv.bean.CsvToBeanBuilder
import nice.fontaine.models.*
import nice.fontaine.models.additions.Shape
import nice.fontaine.models.base.StopWatch
import nice.fontaine.models.csv.*
import java.io.BufferedReader
import java.io.FileReader

class GtfsReader(private val debug: Boolean = false) {

    fun load(dir: String): Gtfs {
        val agencies = toMap("$dir/agency.txt", CsvAgency::class.java)
        val routes = toRoutes(dir, agencies)
        val calendar = toMap("$dir/calendar.txt", CsvCalendar::class.java)
        val stops = toMap("$dir/stops.txt", CsvStop::class.java)
        val shapes = toShapeMap(dir)
        val exclusionDates = toExclusionDates(dir)
        val stopTimes = toStopTimes(dir, stops)
        val trips = toMap("$dir/trips.txt", CsvTrip::class.java)
        val transfers = toMap("$dir/transfers.txt", CsvTransfer::class.java)
        return Gtfs(agencies, routes, calendar, stops, shapes, exclusionDates, stopTimes, trips, transfers)
    }

    fun <T> read(fileName: String, clazz: Class<T>): MutableList<T> {
        if (debug) {
            val watch = StopWatch()
            watch.start("Reading $fileName")
            val result = compute(fileName, clazz)
            watch.stop()
            return result
        }
        return compute(fileName, clazz)
    }

    private fun <T> compute(fileName: String, clazz: Class<T>): MutableList<T> {
        val file = this.javaClass.classLoader.getResource(fileName).file
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        return CsvToBeanBuilder<T>(bufferedReader)
                .withType(clazz)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse()
    }

    private fun <T : Entity> toMap(fileName: String, clazz: Class<T>) =
            read(fileName, clazz).map { it.id to it }.toMap()

    private fun toShapeMap(dir: String) = read("$dir/shapes.txt", CsvShapePoint::class.java)
            .groupBy { it.id }
            .map {entry -> Shape(entry.key, entry.value.toMutableList()) }
            .map { it.id to it }
            .toMap()

    private fun toExclusionDates(dir: String) = read("$dir/calendar_dates.txt", CsvCalendarDate::class.java)
            .groupBy { it.id }
            .map { it.key to it.value }
            .toMap()

    private fun toRoutes(dir: String, agencies: Map<Any, CsvAgency>): Map<Any, CsvRoute> {
        val routes = toMap("$dir/routes.txt", CsvRoute::class.java)
        routes.forEach { _, route -> route.agency = agencies[route.agencyId] }
        return routes
    }

    private fun toStopTimes(dir: String, stops: Map<Any, CsvStop>): Map<Any, List<CsvStopTime>> {
        val stopTimes = read("$dir/stop_times.txt", CsvStopTime::class.java)
        stopTimes.forEach { it.stop = stops[it.stopId] }
        return stopTimes.groupBy { it.tripId }
                .map { it.key to it.value.sortedBy { it.seqId } }
                .toMap()
    }
}

fun main(args: Array<String>?) {
    val gtfsReader = GtfsReader(true)
    gtfsReader.load("partial")
}
