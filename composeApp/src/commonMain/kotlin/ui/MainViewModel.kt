package ui

import data.CctvItem
import data.RequestState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class MainViewModel : ViewModel() {
    private val _listCctv : MutableStateFlow<RequestState<List<CctvItem>>> = MutableStateFlow(RequestState.Idle)
    val listCctv : StateFlow<RequestState<List<CctvItem>>> = _listCctv.asStateFlow()

    private val json = Json { ignoreUnknownKeys = true }
    private val httpClient : HttpClient = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 10000
        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryIf { _, response -> !response.status.isSuccess() }
            retryOnExceptionIf { _, cause -> cause is HttpRequestTimeoutException }
            delayMillis { 3000L }
        }

        install(HttpCallValidator) {
            handleResponseExceptionWithRequest { cause, request -> println("exception $cause with request $request") }
        }

        install(ContentNegotiation) {
            json(json)
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    fun getListCctv() = viewModelScope.launch {
        _listCctv.emit(RequestState.Loading)
        try {
            val request = withContext(Dispatchers.IO) {
                httpClient
                    .get("https://pelindung.bandung.go.id:8443/api/cek")
                    .body<List<CctvItem>>()
            }
            _listCctv.emit(RequestState.Success(request))
        } catch (e: Exception) {
            _listCctv.emit(RequestState.Error(e.message.toString()))
        }
    }

    override fun onCleared() {
        httpClient.close()
    }
}