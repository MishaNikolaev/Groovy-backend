package com.nmichail.groovy.com.nmichail.groovy.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import services.AlbumService

fun Route.albumRoutes() {
    val albumService: AlbumService by inject()

    route("/albums") {
        get {
            try {
                val albums = albumService.getAllAlbums()
                call.respond(HttpStatusCode.OK, albums)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to fetch albums"))
                )
            }
        }

        get("/{id}") {
            try {
                val albumId = call.parameters["id"] ?: throw IllegalArgumentException("Album ID is required")
                val album = albumService.getAlbumById(albumId)
                
                if (album != null) {
                    call.respond(HttpStatusCode.OK, album)
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to "Album not found")
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
                    mapOf("error" to (e.message ?: "Failed to fetch album"))
                )
            }
        }

        get("/artist/{artist}") {
            try {
                val artist = call.parameters["artist"] ?: throw IllegalArgumentException("Artist name is required")
                val albums = albumService.getAlbumsByArtist(artist)
                call.respond(HttpStatusCode.OK, albums)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to fetch artist's albums"))
                )
            }
        }

        get("/search") {
            try {
                val query = call.parameters["q"] ?: throw IllegalArgumentException("Search query is required")
                val albums = albumService.searchAlbumsByTitle(query)
                call.respond(HttpStatusCode.OK, albums)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to search albums"))
                )
            }
        }

        get("/genre/{genre}") {
            try {
                val genre = call.parameters["genre"] ?: throw IllegalArgumentException("Genre is required")
                val albums = albumService.getAlbumsByGenre(genre)
                call.respond(HttpStatusCode.OK, albums)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to (e.message ?: "Failed to fetch albums by genre"))
                )
            }
        }
    }
} 