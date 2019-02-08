package nice.fontaine.models.additions

import nice.fontaine.models.Entity
import nice.fontaine.models.csv.CsvShapePoint

data class Shape(override val id: Any,
                 val pointCsvs: MutableList<CsvShapePoint>) : Entity {

    init { pointCsvs.sortBy { it.seqId } }

    fun points() = pointCsvs.toList()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Shape
        if (id != other.id) return false
        return true
    }

    override fun hashCode() = id.hashCode()
}
