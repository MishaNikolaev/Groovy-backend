package com.nmichail.groovy.com.nmichail.groovy.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import services.PlayService

fun Route.playRoutes() {
    val playService: PlayService by inject()

    route("/tracks/{id}/play") {
        post {
            try {
                val trackId = call.parameters["id"] ?: throw IllegalArgumentException("Track ID is required")
                val userId = call.request.headers["X-User-Id"] ?: throw IllegalArgumentException("User ID is required")
                
                playService.recordPlay(userId, trackId)
                call.respond(HttpStatusCode.OK)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to record play"))
                )
            }
        }

        get("/count") {
            try {
                val trackId = call.parameters["id"] ?: throw IllegalArgumentException("Track ID is required")
                val count = playService.getPlayCount(trackId)
                call.respond(HttpStatusCode.OK, mapOf("count" to count))
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to get play count"))
                )
            }
        }
    }
} 