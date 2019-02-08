package nice.fontaine.features

import nice.fontaine.gui.map.*
import nice.fontaine.models.network.Connection
import nice.fontaine.models.network.Stop
import org.jxmapviewer.viewer.GeoPosition

class ConnectionVessel(private val handler: MapHandler, private val connection: Connection) {
    private lateinit var stops: MutableList<Stop>
    private lateinit var path: ConnectionPath

    init {
        stops = mutableListOf()
        connection.legs.forEach {
            stops.add(it.origin)
            stops.add(it.destination)
        }
        path = ConnectionPath(stops)
    }

    fun update(timestamp: Int) {
        if (!path.timeRange.contains(timestamp)) return
        /*if (!path.timeRange.contains(timestamp)) return
        path.step()
        val style = Element.Point(ORANGE, 200.0)
        handler.update("VEHICLE", path.currentPosition(), style)*/
    }

    fun show() {
        handler.updates("SHAPE", stops.map{it.position}.toMutableList(), Element.Line(GREEN, 4F))
        handler.updates("STOPS", stops.map{it.position}.toMutableList(), Element.Point(BLUE, 50.0))
    }




}
