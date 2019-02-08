package nice.fontaine

import nice.fontaine.extensions.toSeconds
import nice.fontaine.gui.MapWindow
import nice.fontaine.gui.map.*
import nice.fontaine.gui.map.styles.MapStyle

private val RANGE = "3:04:00".toSeconds().."3:0:00".toSeconds()

class Application {
    private val token = Tokens().tokens["MAPBOX_TOKEN"]!!
    private val mapStyle = MapStyle(tileStyle = Mapbox.Base.LIGHT, zoom = 7)
    private val window = MapWindow(token, mapStyle)
    private val gtfs = GtfsReader(debug = true).load("partial")
    private val vessels = gtfs.vessels(window.mapHandler)

    fun simulate() {
        vessels.forEach { it.show() }

        RANGE.forEach { second ->
            vessels.forEach { vessel -> vessel.update(second) }
            Thread.sleep(10)
        }
    }
}

fun main(args: Array<String>) {
    val app = Application()
    app.simulate()
}
