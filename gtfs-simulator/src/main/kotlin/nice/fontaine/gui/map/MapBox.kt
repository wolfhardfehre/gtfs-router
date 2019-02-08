package nice.fontaine.gui.map

import org.jxmapviewer.viewer.TileFactoryInfo

private const val MAX = 19
private const val BASE_URL = "https://api.tiles.mapbox.com/v4/"

class Mapbox(private val token: String, base: Base = Base.STREETS) :
        TileFactoryInfo("Mapbox", 1, MAX - 2, MAX, 256,
                true, true, BASE_URL + base.mapid, "x", "y", "z") {

    enum class Base(var mapid: String) {
        BRIGHT("mapbox.bright"),
        COMIC("mapbox.comic"),
        DARK("mapbox.dark"),
        HIGH_CONTRAST("mapbox.high-contrast"),
        LIGHT("mapbox.light"),
        OUTDOORS("mapbox.outdoors"),
        PENCIL("mapbox.pencil"),
        RUN_BIKE_HIKE("mapbox.run-bike-hike"),
        SATELLITE("mapbox.satellite"),
        STREETS("mapbox.streets"),
        STREETS_SATELLITE("mapbox.streets-satellite")
    }

    override fun getTileUrl(x: Int, y: Int, zoom: Int): String = "${baseURL}/${MAX - zoom}/$x/$y.png?access_token=$token"
}
