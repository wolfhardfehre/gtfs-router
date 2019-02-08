package nice.fontaine.repositories

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import nice.fontaine.models.network.Connection
import nice.fontaine.models.network.Request
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

private const val TIMEOUT = 30L

interface MockConnectionApi {

    @POST("/connection")
    fun getConnection(@Body request: Request): Deferred<Connection>

    companion object {
        var mockWebServer = MockWebServer()

        fun createAndStart(): MockConnectionApi {
            startServer()
            mockResponse()
            val url = mockWebServer.url("/").toString()
            val retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson()))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(httpClient())
                    .build()
            return retrofit.create(MockConnectionApi::class.java)
        }

        fun startServer() {
            mockWebServer = MockWebServer()
            val logger = Logger.getLogger(MockWebServer::class.java.getName())
            logger.setUseParentHandlers(false)
            mockWebServer.start(6666)
        }

        fun stopServer() {
            mockWebServer.shutdown()
        }

        fun mockResponse() {
            mockWebServer.enqueue(MockResponse()
                    .setResponseCode(201)
                    .setBody(bodyResponse()))
        }

        private fun httpClient() = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(getLoggingInterceptor())
                .build()

        private fun getLoggingInterceptor() = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.NONE)

        private fun gson() = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        private fun bodyResponse() = "{\n" +
                "  \"legs\": [\n" +
                "    {\n" +
                "      \"line\": \"U3\",\n" +
                "      \"type\": \"subway\",\n" +
                "      \"origin\": {\n" +
                "        \"id\": \"731494\",\n" +
                "        \"name\": \"Onkel Toms Hütte (U), Berlin\",\n" +
                "        \"time\": 1523298720,\n" +
                "        \"latitude\": 52.449771,\n" +
                "        \"longitude\": 13.253128\n" +
                "      },\n" +
                "      \"destination\": {\n" +
                "        \"id\": \"731366\",\n" +
                "        \"name\": \"Spichernstr. (U), Berlin\",\n" +
                "        \"time\": 1523299800,\n" +
                "        \"latitude\": 52.49705,\n" +
                "        \"longitude\": 13.33073\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"line\": \"U8\",\n" +
                "      \"type\": \"subway\",\n" +
                "      \"origin\": {\n" +
                "        \"id\": \"731366\",\n" +
                "        \"name\": \"Spichernstr. (U), Berlin\",\n" +
                "        \"time\": 1523300100,\n" +
                "        \"latitude\": 52.49705,\n" +
                "        \"longitude\": 13.33073\n" +
                "      },\n" +
                "      \"destination\": {\n" +
                "        \"id\": \"731019\",\n" +
                "        \"name\": \"Zoologischer Garten Bahnhof (S+U), Berlin\",\n" +
                "        \"time\": 1523300160,\n" +
                "        \"latitude\": 52.6782,\n" +
                "        \"longitude\": 13.3977\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}\n"
    }
}
