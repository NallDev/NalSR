package data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface ApiService {
    suspend fun getData() : List<CctvItem>
}

class KtorApiConfig(private val client : HttpClient) : ApiService {
    override suspend fun getData() : List<CctvItem> {
        return try {
            client.get("$API_URL/cek").body()
        } catch (e: Exception) {
            emptyList()
        }
    }

    companion object {
        private const val API_URL =
            "https://raw.githubusercontent.com/Kotlin/KMP-App-Template/main/list.json"
    }
}