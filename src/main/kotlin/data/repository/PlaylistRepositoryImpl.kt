package data.repository

import com.google.cloud.firestore.Firestore
import com.nmichail.groovy.domain.repository.PlaylistRepository
import data.firebase.FirebaseAdmin
import models.Playlist
import org.koin.core.component.KoinComponent

class PlaylistRepositoryImpl : PlaylistRepository, KoinComponent {
    private val db: Firestore = FirebaseAdmin.getDb()

    override suspend fun createPlaylist(playlist: Playlist): Playlist {
        val docRef = db.collection("playlists").document()
        val newPlaylist = playlist.copy(id = docRef.id)
        docRef.set(newPlaylist).get()
        return newPlaylist
    }

    override suspend fun getPlaylistById(id: String): Playlist? {
        return db.collection("playlists")
            .document(id)
            .get()
            .get()
            .toObject(Playlist::class.java)
    }

    override suspend fun updatePlaylist(playlist: Playlist): Playlist {
        db.collection("playlists")
            .document(playlist.id)
            .set(playlist)
            .get()
        return playlist
    }

    override suspend fun deletePlaylist(id: String) {
        db.collection("playlists")
            .document(id)
            .delete()
            .get()
    }

    override suspend fun addTracksToPlaylist(playlistId: String, trackIds: List<String>) {
        val playlist = getPlaylistById(playlistId) ?: throw IllegalArgumentException("Playlist not found")
        val updatedTracks = (playlist.tracks + trackIds).distinct()
        updatePlaylist(playlist.copy(tracks = updatedTracks))
    }

    override suspend fun removeTracksFromPlaylist(playlistId: String, trackIds: List<String>) {
        val playlist = getPlaylistById(playlistId) ?: throw IllegalArgumentException("Playlist not found")
        val updatedTracks = playlist.tracks.filter { it !in trackIds }
        updatePlaylist(playlist.copy(tracks = updatedTracks))
    }

    override suspend fun getPlaylistsByUserId(userId: String): List<Playlist> {
        return db.collection("playlists")
            .whereEqualTo("userId", userId)
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                doc.toObject(Playlist::class.java)
            }
    }
} 