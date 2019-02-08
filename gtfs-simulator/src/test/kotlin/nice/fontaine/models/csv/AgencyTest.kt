package nice.fontaine.models.csv

import org.junit.Test
import assertk.assert
import assertk.assertions.*
import nice.fontaine.GtfsReader
import org.joda.time.DateTimeZone
import org.junit.Before

class AgencyTest {
    private lateinit var agencies: MutableList<CsvAgency>

    @Before fun setUp() {
        val gtfs = GtfsReader()
        agencies = gtfs.read("full/agency.txt", CsvAgency::class.java)
    }

    @Test fun `should read agency size`() {
        assert(agencies.size).isEqualTo(1)
    }

    @Test fun `should read agency id`() {
        assert(agencies[0].id).isEqualTo(796)
    }

    @Test fun `should read agency name`() {
        assert(agencies[0].name).isEqualTo("Berliner Verkehrsbetriebe")
    }

    @Test fun `should read agency url`() {
        assert(agencies[0].url).isEqualTo("http://www.bvg.de")
    }

    @Test fun `should read agency timezone`() {
        assert(agencies[0].tZone).isEqualTo("Europe/Berlin")
    }

    @Test fun `should read agency timezone from method`() {
        val timeOne = DateTimeZone.forID("Europe/Berlin")
        assert(agencies[0].timezone()).isEqualTo(timeOne)
    }

    @Test fun `should read agency language`() {
        assert(agencies[0].language).isEqualTo("de")
    }

    @Test fun `should read agency phone`() {
        assert(agencies[0].phone).isEqualTo("")
    }
}
