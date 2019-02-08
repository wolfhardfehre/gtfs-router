package nice.fontaine.models.base

import assertk.assert
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import nice.fontaine.GtfsReader
import nice.fontaine.models.csv.CsvStopTime
import nice.fontaine.extensions.toPeriod
import org.junit.Before
import org.junit.Test

class TimeRangeTest {
    private lateinit var stopTimes: MutableList<CsvStopTime>
    private lateinit var timeRange: TimeRange

    @Before fun setUp() {
        val gtfs = GtfsReader()
        stopTimes = gtfs.read("full/stop_times.txt", CsvStopTime::class.java)
        timeRange = TimeRange(stopTimes[0], stopTimes[1])
    }

    @Test fun `should contain time when in range`() {
        val period = "11:43:00".toPeriod()
        assert(timeRange.contains(period)).isTrue()
    }

    @Test fun `should contain time when exactly at range start time`() {
        val period = "11:42:00".toPeriod()
        assert(timeRange.contains(period)).isTrue()
    }

    @Test fun `should contain time when exactly at range end time`() {
        val period = "11:44:00".toPeriod()
        assert(timeRange.contains(period)).isTrue()
    }

    @Test fun `should not contain time when time before range`() {
        val period = "11:41:00".toPeriod()
        assert(timeRange.contains(period)).isFalse()
    }

    @Test fun `should not contain time when time after range`() {
        val period = "11:45:00".toPeriod()
        assert(timeRange.contains(period)).isFalse()
    }
}
