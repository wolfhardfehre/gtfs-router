package nice.fontaine.models.postgresql

import nice.fontaine.extensions.toPeriod
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.joda.time.Period
import org.postgis.PGgeometry
import org.postgis.Point
import org.postgresql.util.PGInterval
import java.sql.SQLException

fun Table.point(name: String, srid: Int = 4326): Column<Point> = registerColumn(name, PointColumnType(srid))

fun Table.interval(name: String): Column<PGInterval> = registerColumn(name, IntervalColumnType())

class PointColumnType(val srid: Int = 4326): ColumnType() {

    override fun sqlType() = "GEOMETRY(Point, $srid)"

    override fun valueFromDB(value: Any) = (if (value is PGgeometry) value.geometry else value)!!

    override fun notNullValueToDB(value: Any): Any {
        if (value is Point) {
            if (value.srid == Point.UNKNOWN_SRID) value.srid = srid
            return PGgeometry(value)
        }
        return value
    }
}

class IntervalColumnType : ColumnType() {

    override fun sqlType() = "INTERVAL"

    override fun valueFromDB(value: Any): Period {
        try {
            val interval = value as PGInterval
            return interval.toPeriod()
        } catch (e: SQLException) {
            throw RuntimeException("Invalid value as interval is passed.", e)
        }
    }

    override fun notNullValueToDB(value: Any): Any {
        if (value is String) return value
        return value.toString()
    }
}
