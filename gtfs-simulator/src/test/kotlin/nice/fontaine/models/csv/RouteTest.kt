package nice.fontaine.models.csv

import assertk.assertions.isEqualTo
import nice.fontaine.GtfsReader
import org.junit.Before
import org.junit.Test

class RouteTest {
    private lateinit var routes: MutableList<CsvRoute>
    private lateinit var gtfs: GtfsReader

    @Before fun setUp() {
        gtfs = GtfsReader()
        routes = gtfs.read("full/routes.txt", CsvRoute::class.java)
    }

    @Test fun `should read routes size`() {
        assertk.assert(routes.size).isEqualTo(3)
    }

    @Test fun `should read routes id`() {
        assertk.assert(routes[0].id).isEqualTo("17522_700")
    }

    @Test fun `should read routes agency id`() {
        assertk.assert(routes[0].agencyId).isEqualTo(796)
    }

    @Test fun `should read routes route short name`() {
        assertk.assert(routes[0].shortName).isEqualTo("U7")
    }

    @Test fun `should read routes route long name`() {
        assertk.assert(routes[0].longName).isEqualTo("")
    }

    @Test fun `should read routes type`() {
        assertk.assert(routes[0].type).isEqualTo(700)
    }

    @Test fun `should read routes color`() {
        assertk.assert(routes[0].color).isEqualTo("")
    }

    @Test fun `should read routes textColor`() {
        assertk.assert(routes[0].textColor).isEqualTo("")
    }

    @Test fun `should read routes description`() {
        assertk.assert(routes[0].description).isEqualTo("")
    }

    @Test fun `should get correct agency`() {
        val agency = CsvAgency(1)
        val route = routes[0]
        route.agency = agency
        assertk.assert(route.agency).isEqualTo(agency)
    }
}
