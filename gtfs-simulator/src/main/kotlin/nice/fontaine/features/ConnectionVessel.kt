package nice.fontaine.features

import nice.fontaine.extensions.toShapePositions
import nice.fontaine.extensions.toStopPositions
import nice.fontaine.gui.map.*
import nice.fontaine.models.additions.Segment
import nice.fontaine.models.csv.CsvTrip
import nice.fontaine.models.network.Connection
import org.jxmapviewer.viewer.GeoPosition

class ConnectionVessel(private val handler: MapHandler, private val connection: Connection) {
    private val stops: MutableList<GeoPosition> = mutableListOf()
    init {
        connection.legs.forEach {
            stops.add(it.origin.position)
            stops.add(it.destination.position)
        }
    }

    fun update(timestamp: Int) {
        if (!path.timeRange.contains(timestamp)) return
        path.step()
        val style = Element.Point(ORANGE, 200.0)
        handler.update("VEHICLE", path.currentPosition(), style)
    }

    fun show() {
        handler.updates("SHAPE", stops, Element.Line(GREEN, 4F))
        handler.updates("STOPS", stops, Element.Point(BLUE, 50.0))
    }
}
