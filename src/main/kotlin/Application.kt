package com.nmichail.groovy.com.nmichail.groovy

import com.nmichail.groovy.com.nmichail.groovy.plugins.configureDatabase
import com.nmichail.groovy.com.nmichail.groovy.plugins.configureSecurity
import com.nmichail.groovy.com.nmichail.groovy.plugins.configureSerialization
import com.nmichail.groovy.com.nmichail.groovy.plugins.configureSwagger
import com.nmichail.groovy.com.nmichail.groovy.routing.authRoutes
import com.nmichail.groovy.com.nmichail.groovy.routing.trackRoutes
import data.di.appModule
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
    configureSwagger()

    routing {
        authRoutes()
        trackRoutes()
    }
}
