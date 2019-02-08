package nice.fontaine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nice.fontaine.extensions.toSeconds
import nice.fontaine.gui.MapWindow
import nice.fontaine.gui.map.*
import nice.fontaine.gui.map.styles.MapStyle
import nice.fontaine.models.network.Request
import nice.fontaine.repositories.MockConnectionApi
import okhttp3.mockwebserver.MockResponse
import retrofit2.HttpException

private val RANGE = "3:04:00".toSeconds().."4:04:00".toSeconds()

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
    /*val app = Application()
    app.simulate()*/

    val mockApi = MockConnectionApi.createAndStart()
    GlobalScope.launch(Dispatchers.IO) {
        val request = mockApi.getConnection(Request())
        try {
            val response = request.await()
            println(response)
        } catch (e: HttpException) {
            println(e.code())
        } catch (e: Throwable) {
            println("Ooops: Something else went wrong: $e")
        }
    }
}


