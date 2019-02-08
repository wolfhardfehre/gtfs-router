package nice.fontaine.models.postgresql

import java.io.File

const val UNKNOWN_DB = "Something went wrong when trying to read the local ~.pgpass file. Maybe the entry with this " +
        "particular database name doesn't exist."
private val HOME_FOLDER = System.getProperty("user.home")

data class Credentials(val db: String, val host: String, val port: String, val user: String, val pw: String)

object PgPassReader {

    fun getDbCredentials(databaseName: String, baseFolder: String = HOME_FOLDER): Credentials {
        val pattern = "^([^:]+):([^:]+):$databaseName:([^:]+):(.+)$".toPattern()
        File("$baseFolder/.pgpass").readLines()
                .forEach { line ->
                    val matcher = pattern.matcher(line)
                    if (matcher.find()) {
                        return Credentials(
                                databaseName,
                                matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4)
                        )
                    }
                }
        throw IllegalStateException(UNKNOWN_DB)
    }
}
