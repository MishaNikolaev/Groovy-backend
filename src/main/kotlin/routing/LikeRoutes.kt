package com.nmichail.groovy.com.nmichail.groovy.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import services.LikeService

fun Route.likeRoutes() {
    val likeService: LikeService by inject()

    route("/tracks/{id}/like") {
        post {
            try {
                val trackId = call.parameters["id"] ?: throw IllegalArgumentException("Track ID is required")
                val userId = call.request.headers["X-User-Id"] ?: throw IllegalArgumentException("User ID is required")
                
                likeService.addLike(userId, trackId, "track")
                call.respond(HttpStatusCode.OK)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to like track"))
                )
            }
        }

        delete {
            try {
                val trackId = call.parameters["id"] ?: throw IllegalArgumentException("Track ID is required")
                val userId = call.request.headers["X-User-Id"] ?: throw IllegalArgumentException("User ID is required")
                
                likeService.removeLike(userId, trackId, "track")
                call.respond(HttpStatusCode.OK)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to unlike track"))
                )
            }
        }
    }

    route("/albums/{id}/like") {
        post {
            try {
                val albumId = call.parameters["id"] ?: throw IllegalArgumentException("Album ID is required")
                val userId = call.request.headers["X-User-Id"] ?: throw IllegalArgumentException("User ID is required")
                
                likeService.addLike(userId, albumId, "album")
                call.respond(HttpStatusCode.OK)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to like album"))
                )
            }
        }

        delete {
            try {
                val albumId = call.parameters["id"] ?: throw IllegalArgumentException("Album ID is required")
                val userId = call.request.headers["X-User-Id"] ?: throw IllegalArgumentException("User ID is required")
                
                likeService.removeLike(userId, albumId, "album")
                call.respond(HttpStatusCode.OK)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to unlike album"))
                )
            }
        }
    }
} 