package nice.fontaine.models.csv

import assertk.assertions.isEqualTo
import nice.fontaine.GtfsReader
import nice.fontaine.extensions.toPeriod
import org.junit.Before
import org.junit.Test

class StopTimeTest {
    private lateinit var stopTimes: MutableList<CsvStopTime>

    @Before fun setUp() {
        val gtfs = GtfsReader()
        stopTimes = gtfs.read("full/stop_times.txt", CsvStopTime::class.java)
    }

    @Test fun `should read stop times size`() {
        assertk.assert(stopTimes.size).isEqualTo(61)
    }

    @Test fun `should read stop time trip id`() {
        assertk.assert(stopTimes[0].tripId).isEqualTo("104089740")
    }

    @Test fun `should read stop time sequence`() {
        assertk.assert(stopTimes[0].seqId).isEqualTo(0)
    }

    @Test fun `should read stop time arrival time`() {
        assertk.assert(stopTimes[0].arrivalTime).isEqualTo("11:42:00")
    }

    @Test fun `should read stop time departure time`() {
        assertk.assert(stopTimes[0].departureTime).isEqualTo("11:42:00")
    }

    @Test fun `should read stop time stop id`() {
        assertk.assert(stopTimes[0].stopId).isEqualTo("070101051865")
    }

    @Test fun `should read stop time pickup type`() {
        assertk.assert(stopTimes[0].pickupType).isEqualTo(0)
    }

    @Test fun `should read stop time drop off type`() {
        assertk.assert(stopTimes[0].dropOffType).isEqualTo(0)
    }

    @Test fun `should read stop time headsign`() {
        assertk.assert(stopTimes[0].headsign).isEqualTo("ERSATZ U RUDOW -> 260 ADLERSHOF")
    }

    @Test fun `should read stop time arrival time from method`() {
        val period = "11:42:00".toPeriod()
        assertk.assert(stopTimes[0].arrival).isEqualTo(period)
    }

    @Test fun `should read stop time departure time from method`() {
        val period = "11:42:00".toPeriod()
        assertk.assert(stopTimes[0].departure).isEqualTo(period)
    }

    @Test fun `should contain correct stop when add`() {
        val stop = CsvStop()
        val stopTime = stopTimes[0]
        stopTime.stop = stop
        assertk.assert(stopTime.stop).isEqualTo(stop)
    }
}
