package nice.fontaine.extensions

import nice.fontaine.models.additions.Segment
import nice.fontaine.models.csv.CsvShapePoint
import nice.fontaine.models.csv.CsvStopTime

fun List<CsvStopTime>.toDuration() = (this[this.size - 1].arrival - this[0].departure).toSecs()

fun List<CsvShapePoint>.toSegments() = this.windowed(size = 2, step = 1).map { pair -> Segment(pair.toPair()) }

fun <T> List<T>.toPair() = Pair(this[0], this[1])

fun List<CsvShapePoint>.toShapePositions() = this.map { point -> point.position }.toMutableList()

fun List<Segment>.length() = this.map { segment ->  segment.length}.sum()

fun List<CsvStopTime>.toStopPositions() = this.map { it.stop!!.position() }.toMutableList()
