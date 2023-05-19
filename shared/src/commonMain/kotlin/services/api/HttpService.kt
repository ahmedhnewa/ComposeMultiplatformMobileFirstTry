package services.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json

object HttpService {
    val client: HttpClient by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
            install(HttpTimeout)
            install(HttpCache)
            install(Resources)
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }
}