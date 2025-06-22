package com.nmichail.groovy

import com.nmichail.groovy.com.nmichail.groovy.plugins.*
import com.nmichail.groovy.com.nmichail.groovy.routing.*
import data.di.appModule
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.origin
import io.ktor.server.plugins.ratelimit.RateLimit
import io.ktor.server.plugins.ratelimit.RateLimitName
import io.ktor.server.routing.*
import kotlin.time.Duration.Companion.seconds
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main() {
    embeddedServer(
        Netty,
        port = System.getenv("PORT")?.toInt() ?: 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    
    configureSerialization()
    configureSecurity()
    configureDatabase()
    configureSwagger()

    install(RateLimit) {
        register(RateLimitName("global")) {
            requestKey { call -> call.request.origin.remoteHost }
            rateLimiter(limit = 100, refillPeriod = 60000L.seconds)
        }
    }

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowHeader("X-User-Id")
        allowHost("localhost:3000", schemes = listOf("http", "https"))
        allowCredentials = true
        maxAgeInSeconds = 3600
    }

    routing {
        authRoutes()
        trackRoutes()
        albumRoutes()
        playlistRoutes()
        likeRoutes()
        playRoutes()
    }
}
