package nice.fontaine.models.csv

import assertk.assertions.isEqualTo
import nice.fontaine.GtfsReader
import nice.fontaine.models.additions.Shape
import org.junit.Before
import org.junit.Test

class TripTest {
    private lateinit var trips: MutableList<CsvTrip>

    @Before fun setUp() {
        val gtfs = GtfsReader()
        trips = gtfs.read("full/trips.txt", CsvTrip::class.java)
    }

    @Test fun `should read trips size`() {
        assertk.assert(trips.size).isEqualTo(3)
    }

    @Test fun `should read trip id`() {
        assertk.assert(trips[0].id).isEqualTo("104089740")
    }

    @Test fun `should read trip route id`() {
        assertk.assert(trips[0].routeId).isEqualTo("17522_700")
    }

    @Test fun `should read trip service id`() {
        assertk.assert(trips[0].serviceId).isEqualTo("1593")
    }

    @Test fun `should read trip headsign`() {
        assertk.assert(trips[0].headsign).isEqualTo("U Rudow")
    }

    @Test fun `should read trip short name`() {
        assertk.assert(trips[0].shortName).isEqualTo("")
    }

    @Test fun `should read trip direction id`() {
        assertk.assert(trips[0].directionId).isEqualTo(0)
    }

    @Test fun `should read trip block id`() {
        assertk.assert(trips[0].blockId).isEqualTo(-1)
    }

    @Test fun `should read trip shape id`() {
        assertk.assert(trips[0].shapeId).isEqualTo("8674")
    }

    @Test fun `should read trip wheelchair accessible`() {
        assertk.assert(trips[0].wheelchairAccessible).isEqualTo(true)
    }

    @Test fun `should read trip bikes allowed`() {
        assertk.assert(trips[0].bikesAllowed).isEqualTo(false)
    }

    @Test fun `should contain correct route when add`() {
        val route = CsvRoute()
        val trip = trips[0]
        trip.route = route
        assertk.assert(trip.route).isEqualTo(route)
    }

    @Test fun `should contain correct calendar when add`() {
        val calendar = CsvCalendar()
        val trip = trips[0]
        trip.calendar = calendar
        assertk.assert(trip.calendar).isEqualTo(calendar)
    }

    @Test fun `should contain correct exclusion dates when add`() {
        val exclusionDates = arrayOf(CsvCalendarDate())
        val trip = trips[0]
        trip.exclusionDates = exclusionDates
        assertk.assert(trip.exclusionDates).isEqualTo(exclusionDates)
    }
}
