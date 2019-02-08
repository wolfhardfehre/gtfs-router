package nice.fontaine.models.csv

import assertk.assertions.isEqualTo
import nice.fontaine.GtfsReader
import org.junit.Before
import org.junit.Test

class ShapePointTest {
    private lateinit var points: MutableList<CsvShapePoint>

    @Before fun setUp() {
        val gtfs = GtfsReader()
        points = gtfs.read("full/shapes.txt", CsvShapePoint::class.java)
    }

    @Test fun `should read points size`() {
        assertk.assert(points.size).isEqualTo(418)
    }

    @Test fun `should read point id`() {
        assertk.assert(points[0].id).isEqualTo("8756")
    }

    @Test fun `should read point sequence id`() {
        assertk.assert(points[0].seqId).isEqualTo(0)
    }

    @Test fun `should read point latitude`() {
        assertk.assert(points[0].lat).isEqualTo(52.595623)
    }

    @Test fun `should read point longitude`() {
        assertk.assert(points[0].lon).isEqualTo(13.334410)
    }
}
