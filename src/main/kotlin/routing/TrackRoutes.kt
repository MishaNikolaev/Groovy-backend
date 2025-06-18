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

        get("/album/{albumId}") {
            try {
                val albumId = call.parameters["albumId"] ?: throw IllegalArgumentException("Album ID is required")
                val tracks = trackService.getTracksByAlbumId(albumId)
                call.respond(HttpStatusCode.OK, tracks)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to fetch album tracks"))
                )
            }
        }

        get("/artist/{artist}") {
            try {
                val artist = call.parameters["artist"] ?: throw IllegalArgumentException("Artist name is required")
                val tracks = trackService.getTracksByArtist(artist)
                call.respond(HttpStatusCode.OK, tracks)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to fetch artist tracks"))
                )
            }
        }

        get("/search") {
            try {
                val query = call.parameters["q"] ?: throw IllegalArgumentException("Search query is required")
                val tracks = trackService.searchTracksByTitle(query)
                call.respond(HttpStatusCode.OK, tracks)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to search tracks"))
                )
            }
        }

        get("/top") {
            try {
                val limit = call.parameters["limit"]?.toIntOrNull() ?: 10
                val tracks = trackService.getTopTracks(limit)
                call.respond(HttpStatusCode.OK, tracks)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to fetch top tracks"))
                )
            }
        }

        get("/recent") {
            try {
                val limit = call.parameters["limit"]?.toIntOrNull() ?: 10
                val tracks = trackService.getRecentTracks(limit)
                call.respond(HttpStatusCode.OK, tracks)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to fetch recent tracks"))
                )
            }
        }

        get("/liked/{userId}") {
            try {
                val userId = call.parameters["userId"] ?: throw IllegalArgumentException("User ID is required")
                val tracks = trackService.getLikedTracks(userId)
                call.respond(HttpStatusCode.OK, tracks)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to fetch liked tracks"))
                )
            }
        }
    }
} 