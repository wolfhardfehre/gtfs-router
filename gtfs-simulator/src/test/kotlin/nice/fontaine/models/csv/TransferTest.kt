package nice.fontaine.models.csv

import assertk.assertions.isEqualTo
import nice.fontaine.GtfsReader
import org.junit.Before
import org.junit.Test

class TransferTest {
    private lateinit var transfers: MutableList<CsvTransfer>

    @Before fun setUp() {
        val gtfs = GtfsReader()
        transfers = gtfs.read("full/transfers.txt", CsvTransfer::class.java)
    }

    @Test fun `should read transfers size`() {
        assertk.assert(transfers.size).isEqualTo(2)
    }

    @Test fun `should read transfer from stop id`() {
        assertk.assert(transfers[0].fromStopId).isEqualTo("000008012713")
    }

    @Test fun `should read transfer to stop id`() {
        assertk.assert(transfers[0].toStopId).isEqualTo("000008012713")
    }

    @Test fun `should read transfer type`() {
        assertk.assert(transfers[0].type).isEqualTo(2)
    }

    @Test fun `should read transfer time in seconds`() {
        assertk.assert(transfers[0].seconds).isEqualTo(300)
    }

    @Test fun `should read transfer from route id`() {
        assertk.assert(transfers[0].fromRouteId).isEqualTo("")
    }

    @Test fun `should read transfer to route id`() {
        assertk.assert(transfers[0].toRouteId).isEqualTo("")
    }

    @Test fun `should read transfer from trip id`() {
        assertk.assert(transfers[0].fromTripId).isEqualTo("")
    }

    @Test fun `should read transfer to trip id`() {
        assertk.assert(transfers[0].toTripId).isEqualTo("")
    }
}
