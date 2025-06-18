package security

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.http.*
import io.ktor.server.response.*
import models.Playlist

class AccessControl {
    suspend fun checkPlaylistAccess(call: ApplicationCall, playlistId: String, playlist: Playlist?): Boolean {
        val userId = call.request.headers["X-User-Id"]
        
        if (userId == null) {
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Authentication required"))
            return false
        }

        if (playlist == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Playlist not found"))
            return false
        }

        if (playlist.userId != userId) {
            call.respond(HttpStatusCode.Forbidden, mapOf("error" to "You don't have permission to modify this playlist"))
            return false
        }

        return true
    }
} 