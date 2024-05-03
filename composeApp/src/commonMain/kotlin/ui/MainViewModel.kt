package ui

import data.ApiService
import data.CctvItem
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MainViewModel : ViewModel() {
    private val json = Json { ignoreUnknownKeys = true }
    private val httpClient : HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(json)
        }
    }

    private suspend fun getImages(): List<CctvItem> {
        return httpClient
            .get("https://sebastianaigner.github.io/demo-image-api/pictures.json")
            .body<List<CctvItem>>()
    }

    override fun onCleared() {
        httpClient.close()
    }
}