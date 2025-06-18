package com.nmichail.groovy.com.nmichail.groovy.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.Playlist
import org.koin.ktor.ext.inject
import services.PlaylistService
import validation.Validators
import security.AccessControl
import org.valiktor.ConstraintViolationException

fun Route.playlistRoutes() {
    val playlistService: PlaylistService by inject()
    val accessControl = AccessControl()

    route("/playlists") {
        post {
            try {
                val playlist = call.receive<Playlist>()
                Validators.validatePlaylist(playlist)
                val createdPlaylist = playlistService.createPlaylist(playlist)
                call.respond(HttpStatusCode.Created, createdPlaylist)
            } catch (e: ConstraintViolationException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Validation failed", "details" to e.constraintViolations.map { it.property })
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to create playlist"))
                )
            }
        }

        get("/{id}") {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("Playlist ID is required")
                val playlist = playlistService.getPlaylistById(id)
                
                if (playlist != null) {
                    call.respond(HttpStatusCode.OK, playlist)
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to "Playlist not found")
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
                    mapOf("error" to (e.message ?: "Failed to fetch playlist"))
                )
            }
        }

        put("/{id}") {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("Playlist ID is required")
                val playlist = call.receive<Playlist>()
                
                if (id != playlist.id) {
                    throw IllegalArgumentException("Playlist ID in path does not match ID in body")
                }

                val existingPlaylist = playlistService.getPlaylistById(id)
                if (!accessControl.checkPlaylistAccess(call, id, existingPlaylist)) {
                    return@put
                }
                
                Validators.validatePlaylist(playlist)
                val updatedPlaylist = playlistService.updatePlaylist(playlist)
                call.respond(HttpStatusCode.OK, updatedPlaylist)
            } catch (e: ConstraintViolationException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Validation failed", "details" to e.constraintViolations.map { it.property })
                )
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to update playlist"))
                )
            }
        }

        delete("/{id}") {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("Playlist ID is required")
                val existingPlaylist = playlistService.getPlaylistById(id)
                
                if (!accessControl.checkPlaylistAccess(call, id, existingPlaylist)) {
                    return@delete
                }

                playlistService.deletePlaylist(id)
                call.respond(HttpStatusCode.NoContent)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to delete playlist"))
                )
            }
        }

        post("/{id}/tracks") {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("Playlist ID is required")
                val trackIds = call.receive<List<String>>()
                playlistService.addTracksToPlaylist(id, trackIds)
                call.respond(HttpStatusCode.OK)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to add tracks to playlist"))
                )
            }
        }

        delete("/{id}/tracks") {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("Playlist ID is required")
                val trackIds = call.receive<List<String>>()
                playlistService.removeTracksFromPlaylist(id, trackIds)
                call.respond(HttpStatusCode.OK)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to remove tracks from playlist"))
                )
            }
        }

        get("/user/{userId}") {
            try {
                val userId = call.parameters["userId"] ?: throw IllegalArgumentException("User ID is required")
                val playlists = playlistService.getPlaylistsByUserId(userId)
                call.respond(HttpStatusCode.OK, playlists)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to fetch user's playlists"))
                )
            }
        }
    }
} 