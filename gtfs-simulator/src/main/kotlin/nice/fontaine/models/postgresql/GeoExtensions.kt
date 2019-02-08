package nice.fontaine.models.postgresql

import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.ExpressionWithColumnType
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.QueryBuilder
import org.postgis.PGbox2d

infix fun ExpressionWithColumnType<*>.within(box: PGbox2d) : Op<Boolean> = Within(this, box)

class Within(val expr1: Expression<*>, val box: PGbox2d) : Op<Boolean>() {
    override fun toSQL(queryBuilder: QueryBuilder) = "${expr1.toSQL(queryBuilder)} && " +
            "ST_MakeEnvelope(${box.llb.x}, ${box.llb.y}, ${box.urt.x}, ${box.urt.y}, 4326)"
}
