package nice.fontaine.models.csv

import assertk.assertions.isEqualTo
import nice.fontaine.GtfsReader
import org.junit.Before
import org.junit.Test

class StopTest {
    private lateinit var stops: MutableList<CsvStop>

    @Before fun setUp() {
        val gtfs = GtfsReader()
        stops = gtfs.read("full/stops.txt", CsvStop::class.java)
    }

    @Test fun `should read stops size`() {
        assertk.assert(stops.size).isEqualTo(62)
    }

    @Test fun `should read stop id`() {
        assertk.assert(stops[0].id).isEqualTo("070101051865")
    }

    @Test fun `should read stop code`() {
        assertk.assert(stops[0].code).isEqualTo("")
    }

    @Test fun `should read stop name`() {
        assertk.assert(stops[0].name).isEqualTo("Berlin, Zittauer Str.")
    }

    @Test fun `should read stop description`() {
        assertk.assert(stops[0].description).isEqualTo("")
    }

    @Test fun `should read stop latitude`() {
        assertk.assert(stops[0].lat).isEqualTo(52.417873000000)
    }

    @Test fun `should read stop longitude`() {
        assertk.assert(stops[0].lon).isEqualTo(13.478031000000)
    }

    @Test fun `should read stop location type`() {
        assertk.assert(stops[0].locationType).isEqualTo(0)
    }

    @Test fun `should read stop parent station`() {
        assertk.assert(stops[0].parentStation).isEqualTo("900000083172")
    }
}

