package com.nmichail.groovy.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*

fun Application.configureSwagger() {
    routing {
        get("/") {
            call.respondRedirect("/swagger-ui")
        }
        
        swaggerUI(path = "swagger-ui", swaggerFile = "openapi/documentation.yaml")
        
        static("/") {
            resources("openapi")
        }
    }
} 