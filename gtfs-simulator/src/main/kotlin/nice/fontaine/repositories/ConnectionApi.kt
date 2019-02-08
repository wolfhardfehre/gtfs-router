package nice.fontaine.repositories

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import nice.fontaine.models.network.Connection
import nice.fontaine.models.network.Request
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

private const val BASE_URL = ""
private const val TIMEOUT = 30L

interface ConnectionApi {

    @POST("")
    fun getConnection(@Body request: Request): Deferred<Connection>

    companion object {
        fun create(): ConnectionApi {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson()))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .baseUrl(BASE_URL)
                    .client(httpClient())
                    .build()
            return retrofit.create(ConnectionApi::class.java)
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
    }
}
