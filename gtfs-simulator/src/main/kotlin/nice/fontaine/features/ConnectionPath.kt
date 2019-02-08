package nice.fontaine.features

import nice.fontaine.models.network.Stop
import org.jxmapviewer.viewer.GeoPosition

class ConnectionPath(val stop: MutableList<Stop>) {
    val startTime = stop[0].time
    val endTime = stop[stop.size - 1].time
    val timeRange = startTime..endTime


    val currentPosition: GeoPosition = stop[0].position
}
