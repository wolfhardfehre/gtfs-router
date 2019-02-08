package nice.fontaine.models.additions

import nice.fontaine.models.csv.CsvShapePoint
import org.jxmapviewer.viewer.GeoPosition
import java.lang.IllegalStateException
import kotlin.math.pow
import kotlin.math.sqrt

/** Partial geometry between two nodes**/
class Segment(pair: Pair<CsvShapePoint, CsvShapePoint>) {
    private val origin = pair.first
    private val destination = pair.second
    private val distanceLat = destination.lat - origin.lat
    private val distanceLon = destination.lon - origin.lon
    private var currentSection = 0.0
    private var isOnSegment = true
    private var ratio = 0.0
    val length: Double = sqrt(distanceLat.pow(2.0) + distanceLon.pow(2.0))

    fun update(current: Double) {
        ratio = current / length
        isOnSegment = length > current
        currentSection = current
    }

    fun isOnSegment() = isOnSegment

    fun surplus() = if (isOnSegment) 0.0 else currentSection - length

    fun position(): GeoPosition {
        if (!isOnSegment) throw IllegalStateException("Position is not on Segment!")
        val currentLat = origin.lat + ratio * distanceLat
        val currentLon = origin.lon + ratio * distanceLon
        return GeoPosition(currentLat, currentLon)
    }

    fun destination() = GeoPosition(destination.lat, destination.lon)
}
