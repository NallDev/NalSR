package di

import data.ApiService
import data.KtorApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ui.MainViewModel

val dataModule = module {
    val json = Json { ignoreUnknownKeys = true }
    HttpClient {
        install(ContentNegotiation) {
            json(json)
        }
    }

    single<ApiService> { KtorApiConfig(get()) }
}

fun initKoin() {
    startKoin {
        modules(dataModule)
    }
}