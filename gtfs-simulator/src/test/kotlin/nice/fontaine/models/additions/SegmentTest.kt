package nice.fontaine.models.additions

import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isGreaterThan
import assertk.assertions.isTrue
import nice.fontaine.models.csv.CsvShapePoint
import org.junit.Before
import org.junit.Test
import org.jxmapviewer.viewer.GeoPosition
import kotlin.math.sqrt

private val FULL_LENGTH = sqrt(32.0)

class SegmentTest {
    private val pair = Pair(point(0.0, 0.0), point(4.0,4.0))
    private lateinit var segment: Segment

    @Before fun setUp() {
        segment = Segment(pair)
    }

    @Test fun `should get length when build`() {
        assertk.assert(segment.length).isEqualTo(FULL_LENGTH)
    }

    @Test fun `should get origin when never updated`() {
        assertk.assert(segment.position()).isEqualTo(GeoPosition(0.0, 0.0))
    }

    @Test fun `should get first quarter position when updated once`() {
        segment.update(FULL_LENGTH / 4)

        assertk.assert(segment.position()).isEqualTo(GeoPosition(1.0, 1.0))
    }

    @Test fun `should be on segment when update is shorter than length`() {
        segment.update(FULL_LENGTH / 4)

        assertk.assert(segment.isOnSegment()).isTrue()
    }

    @Test fun `should not be on segment when update is equal to length`() {
        segment.update(FULL_LENGTH)

        assertk.assert(segment.isOnSegment()).isFalse()
    }

    @Test fun `should have a surplus of zero length equal to section`() {
        segment.update(FULL_LENGTH)

        assertk.assert(segment.surplus()).isEqualTo(0.0)
    }

    @Test fun `should have a surplus section bigger than length`() {
        val surplus = 0.4
        segment.update(FULL_LENGTH + surplus)

        assertk.assert(segment.surplus()).isGreaterThan(surplus)
    }

    @Test fun `should have a zero surplus section smaller than length`() {
        segment.update(FULL_LENGTH / 4)

        assertk.assert(segment.surplus()).isEqualTo(0.0)
    }

    @Test fun `should also work inn the opposite direction`() {
        val pair = Pair(point(4.0,4.0), point(0.0, 0.0))
        segment = Segment(pair)

        segment.update(FULL_LENGTH / 4)

        assertk.assert(segment.position()).isEqualTo(GeoPosition(3.0, 3.0))
    }

    private fun point(lat: Double, lon: Double): CsvShapePoint {
        val point = CsvShapePoint()
        point.lat = lat
        point.lon = lon
        return point
    }
}
