package nice.fontaine.extensions

import assertk.assert
import assertk.assertions.isEqualTo
import nice.fontaine.GtfsReader
import nice.fontaine.models.additions.Shape
import nice.fontaine.models.csv.CsvStopTime
import org.junit.Before
import org.junit.Test

class ListExtensionsTest {
    private lateinit var stopTimes: MutableList<CsvStopTime>
    private lateinit var shape: Shape

    @Before fun setUp() {
        val gtfs = GtfsReader()
        stopTimes = gtfs.read("full/stop_times.txt", CsvStopTime::class.java)
        shape = gtfs.load("full").shapes["8756"]!!
    }

    @Test fun `should compute duration in seconds when a list of stop times is given`() {
        val actual = stopTimes.subList(0, 2).toDuration()

        assert(actual).isEqualTo(120)
    }

    @Test fun `should compute segments when fed with shape points`() {
        val segments = shape.points().toSegments()

        assert(segments.size).isEqualTo(178)
    }

    @Test fun `should get first value of pair when fed with two shape points`() {
        val points = shape.points().subList(0, 2)
        val pair = points.toPair()

        assert(pair.first).isEqualTo(points[0])
    }

    @Test fun `should get second value of pair when fed with two shape points`() {
        val points = shape.points().subList(0, 2)
        val pair = points.toPair()

        assert(pair.second).isEqualTo(points[1])
    }
}
