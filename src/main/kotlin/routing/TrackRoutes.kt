package com.nmichail.groovy.com.nmichail.groovy.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import services.TrackService

fun Route.trackRoutes() {
    val trackService: TrackService by inject()

    route("/tracks") {
        get {
            try {
                val tracks = trackService.getAllTracks()
                call.respond(HttpStatusCode.OK, tracks)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to fetch tracks"))
                )
            }
        }

        get("/{id}") {
            try {
                val trackId = call.parameters["id"] ?: throw IllegalArgumentException("Track ID is required")
                val track = trackService.getTrackById(trackId)
                
                if (track != null) {
                    call.respond(HttpStatusCode.OK, track)
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to "Track not found")
                    )
                }
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to fetch track"))
                )
            }
        }
    }
} 