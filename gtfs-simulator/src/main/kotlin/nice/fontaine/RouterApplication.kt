package nice.fontaine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nice.fontaine.extensions.toSeconds
import nice.fontaine.features.ConnectionVessel
import nice.fontaine.gui.MapWindow
import nice.fontaine.gui.map.Mapbox
import nice.fontaine.gui.map.Tokens
import nice.fontaine.gui.map.styles.MapStyle
import nice.fontaine.models.network.Request
import nice.fontaine.repositories.MockConnectionApi
import retrofit2.HttpException

private val RANGE = "3:04:00".toSeconds().."4:04:00".toSeconds()
private val TOKEN = Tokens().tokens["MAPBOX_TOKEN"]!!
private val MAP_STYLE = MapStyle(tileStyle = Mapbox.Base.LIGHT, zoom = 7)

class RouterApplication {
    fun simulate(vessels: List<ConnectionVessel>) {
        vessels.forEach { it.show() }

        RANGE.forEach { second ->
            vessels.forEach { vessel -> vessel.update(second) }
            Thread.sleep(10)
        }
    }
}

fun main(args: Array<String>) {
    val window = MapWindow(TOKEN, MAP_STYLE)
    val mockApi = MockConnectionApi.createAndStart()
    GlobalScope.launch(Dispatchers.IO) {
        val request = mockApi.getConnection(Request())
        try {
            val response = request.await()
            val vessels = mutableListOf(ConnectionVessel(window.mapHandler, response))
            val app = RouterApplication()
            app.simulate(vessels)
        } catch (e: HttpException) {
            println(e.code())
        }
    }
}
