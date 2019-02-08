package nice.fontaine.features

import nice.fontaine.gui.map.*
import nice.fontaine.models.network.Connection
import nice.fontaine.models.network.Stop
import org.jxmapviewer.viewer.GeoPosition

class ConnectionVessel(private val handler: MapHandler, private val connection: Connection) {
    private lateinit var stops: MutableList<Stop>

    init {
        stops = mutableListOf()
        connection.legs.forEach {
            stops.add(it.origin)
            stops.add(it.destination)
        }
    }

    fun update(timestamp: Int) {
       //TODO:
    }

    fun show() {
        handler.updates("SHAPE", stops.map{it.position}.toMutableList(), Element.Line(GREEN, 4F))
        handler.updates("STOPS", stops.map{it.position}.toMutableList(), Element.Point(BLUE, 50.0))
    }
}
