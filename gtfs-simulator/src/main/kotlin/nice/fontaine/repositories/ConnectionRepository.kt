package nice.fontaine.repositories

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import nice.fontaine.models.network.Connection
import nice.fontaine.models.network.Request

class ConnectionRepository {
    private val connectionApi = ConnectionApi.create()
    private var job: Job? = null

    fun getConnection(request: Request): Deferred<Connection> = connectionApi.getConnection(request)
    fun cancel() = job?.cancel()
}
