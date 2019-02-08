package nice.fontaine.models.postgresql

import assertk.assert
import assertk.assertions.hasMessage
import assertk.assertions.isEqualTo
import org.junit.Test
import java.io.File

class PgPassReaderTest {

    @Test fun `should return login when inserting database name`() {
        val expected = Credentials("gtfs", "localhost", "5432", "wflintstone", "s:cr1t")
        val basePath = basePathOf("pgpass/")

        val actual = PgPassReader.getDbCredentials("gtfs", basePath)

        assert(actual).isEqualTo(expected)
    }

    @Test fun `should throw illegal state exception when inserting unknown database name`() {
        val basePath = basePathOf("pgpass/")

        assert { PgPassReader.getDbCredentials("unknown_name", basePath) }
                .thrownError { hasMessage(UNKNOWN_DB) }
    }

    private fun basePathOf(subFolder: String): String {
        val url = this.javaClass.classLoader.getResource(subFolder).file
        val baseFolder = File(url).absoluteFile
        return baseFolder.toPath().toString()
    }
}
