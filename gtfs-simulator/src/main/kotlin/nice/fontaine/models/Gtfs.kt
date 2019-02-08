package nice.fontaine.models

import nice.fontaine.features.Path
import nice.fontaine.features.Vessel
import nice.fontaine.gui.map.MapHandler
import nice.fontaine.models.additions.Shape
import nice.fontaine.models.csv.*

data class Gtfs(val agencies: Map<Any, CsvAgency>,
                val routes: Map<Any, CsvRoute>,
                val csvCalendar: Map<Any, CsvCalendar>,
                val stops: Map<Any, CsvStop>,
                val shapes: Map<Any, Shape>,
                val exclusionDates: Map<Any, List<CsvCalendarDate>>,
                val csvStopTimes: Map<Any, List<CsvStopTime>>,
                val trips: Map<Any, CsvTrip>,
                val transfers: Map<Any, CsvTransfer>) {

    init { buildTrips() }

    fun vessels(handler: MapHandler) = trips.map { Vessel(handler, it.value) }.toList()

    private fun buildTrips() {
        trips.forEach { _, trip ->
            val times = csvStopTimes[trip.id]!!.toList()
            val shape = shapes[trip.shapeId]
            trip.path = Path(times, shape!!.points())
            trip.route = routes[trip.routeId]

            trip.calendar = csvCalendar[trip.serviceId]
            trip.exclusionDates = exclusionDates[trip.serviceId]?.toTypedArray()
        }
    }
}
