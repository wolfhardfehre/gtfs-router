package nice.fontaine.extensions

import assertk.assertions.isEqualTo
import com.github.debop.kodatimes.asUtc
import org.junit.Test

class TimeExtensionsTest {

    @Test fun `should convert to period when string is given`() {
        val actual = "01:01:00".toPeriod()

        assertk.assert(actual.toString()).isEqualTo("PT1H1M")
    }

    @Test fun `should convert to date when compact string is given`() {
        val actual = "20190119".toDate().asUtc()

        assertk.assert(actual.toString()).isEqualTo("2019-01-18T23:00:00.000Z")
    }

    @Test fun `should convert to date when iso string with just date is given`() {
        val actual = "2019-01-19".toDate().asUtc()

        assertk.assert(actual.toString()).isEqualTo("2019-01-18T23:00:00.000Z")
    }

    @Test fun `should convert to seconds when period is given`() {
        val actual = "01:01:00".toPeriod().toSecs()

        assertk.assert(actual).isEqualTo(3660)
    }
}
