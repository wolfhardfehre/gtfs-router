package nice.fontaine.models.additions

import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import nice.fontaine.GtfsReader
import org.junit.Before
import org.junit.Test

class ShapeTest {
    private lateinit var shapeMap: Map<Any, Shape>

    @Before fun setUp() {
        val gtfs = GtfsReader()
        shapeMap = gtfs.load("full").shapes
    }

    @Test fun `should read shape map size`() {
        assertk.assert(shapeMap.size).isEqualTo(3)
    }

    @Test fun `should read shape id`() {
        assertk.assert(shapeMap["8756"]!!.id).isEqualTo("8756")
    }

    @Test fun `should read shape points size`() {
        assertk.assert(shapeMap.getValue("8756").points().size).isEqualTo(179)
    }

    @Test fun `should read shape get points array`() {
        assertk.assert(shapeMap.getValue("8756").points()).isInstanceOf(List::class.java)
    }
}
