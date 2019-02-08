package nice.fontaine.models.network

import org.jxmapviewer.viewer.GeoPosition

data class Stop(val id: String, val name: String, val time: Long, val latitude: Double, val longitude: Double) {
    val position: GeoPosition by lazy { GeoPosition(latitude, longitude) }
    constructor() : this("", "",-1L, -1.0, -1.0)
}
