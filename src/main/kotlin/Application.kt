package com.nmichail.groovy

import com.nmichail.groovy.data.di.appModule
import com.nmichail.groovy.plugins.*
import com.nmichail.groovy.routing.authRoutes
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main() {
    embeddedServer(
        Netty,
        port = System.getenv("PORT")?.toInt() ?: 8080,
        host = "127.0.0.1",
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

    routing {
        authRoutes()
    }
}
