package nice.fontaine.models.csv

import assertk.assertions.isEqualTo
import nice.fontaine.GtfsReader
import nice.fontaine.extensions.toDate
import org.junit.Before
import org.junit.Test

class CalendarDateTest {
    private lateinit var exclusionDates: MutableList<CsvCalendarDate>

    @Before fun setUp() {
        val gtfs = GtfsReader()
        exclusionDates = gtfs.read("full/calendar_dates.txt", CsvCalendarDate::class.java)
    }

    @Test fun `should read calendar dates size`() {
        assertk.assert(exclusionDates.size).isEqualTo(33)
    }

    @Test fun `should read calendar dates service id`() {
        assertk.assert(exclusionDates[0].id).isEqualTo("39")
    }

    @Test fun `should read calendar dates date`() {
        assertk.assert(exclusionDates[0].dateString).isEqualTo("20181224")
    }

    @Test fun `should read calendar dates date from method`() {
        val date = "2018-12-24".toDate()
        assertk.assert(exclusionDates[0].date).isEqualTo(date)
    }

    @Test fun `should read calendar dates exception type`() {
        assertk.assert(exclusionDates[0].exceptionType).isEqualTo(1)
    }
}
