package nice.fontaine.models.csv

import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import nice.fontaine.GtfsReader
import nice.fontaine.extensions.toDate
import org.junit.Before
import org.junit.Test

class CalendarTest {
    private lateinit var calendar: MutableList<CsvCalendar>

    @Before fun setUp() {
        val gtfs = GtfsReader()
        calendar = gtfs.read("full/calendar.txt", CsvCalendar::class.java)
    }

    @Test fun `should read calendar size`() {
        assertk.assert(calendar.size).isEqualTo(3)
    }

    @Test fun `should read calendar id`() {
        assertk.assert(calendar[0].id).isEqualTo("1593")
    }

    @Test fun `should read calendar moday`() {
        assertk.assert(calendar[0].moday).isEqualTo(false)
    }

    @Test fun `should read calendar tuesday`() {
        assertk.assert(calendar[0].tuesday).isEqualTo(false)
    }

    @Test fun `should read calendar wednesday`() {
        assertk.assert(calendar[0].wednesday).isEqualTo(false)
    }

    @Test fun `should read calendar thursday`() {
        assertk.assert(calendar[0].thursday).isEqualTo(false)
    }

    @Test fun `should read calendar friday`() {
        assertk.assert(calendar[0].friday).isEqualTo(false)
    }

    @Test fun `should read calendar saturday`() {
        assertk.assert(calendar[0].saturday).isEqualTo(false)
    }

    @Test fun `should read calendar sunday`() {
        assertk.assert(calendar[0].sunday).isEqualTo(false)
    }


    @Test fun `should read calendar start date`() {
        assertk.assert(calendar[0].startDate).isEqualTo("20181114")
    }

    @Test fun `should read calendar end date`() {
        assertk.assert(calendar[0].endDate).isEqualTo("20190608")
    }

    @Test fun `should not contain date before date range`() {
        val dateTime = "2018-11-13".toDate()
        assertk.assert(calendar[0].contains(dateTime)).isFalse()
    }

    @Test fun `should contain date within date range`() {
        val dateTime = "2018-12-11".toDate()
        assertk.assert(calendar[0].contains(dateTime)).isTrue()
    }

    @Test fun `should not contain date after date range`() {
        val dateTime = "2019-06-09".toDate()
        assertk.assert(calendar[0].contains(dateTime)).isFalse()
    }
}
