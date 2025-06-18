package services

import com.nmichail.groovy.domain.repository.PlaylistRepository
import models.Playlist

class PlaylistService(
    private val playlistRepository: PlaylistRepository
) {
    suspend fun createPlaylist(playlist: Playlist): Playlist {
        return playlistRepository.createPlaylist(playlist)
    }

    suspend fun getPlaylistById(id: String): Playlist? {
        return playlistRepository.getPlaylistById(id)
    }

    suspend fun updatePlaylist(playlist: Playlist): Playlist {
        return playlistRepository.updatePlaylist(playlist)
    }

    suspend fun deletePlaylist(id: String) {
        playlistRepository.deletePlaylist(id)
    }

    suspend fun addTracksToPlaylist(playlistId: String, trackIds: List<String>) {
        playlistRepository.addTracksToPlaylist(playlistId, trackIds)
    }

    suspend fun removeTracksFromPlaylist(playlistId: String, trackIds: List<String>) {
        playlistRepository.removeTracksFromPlaylist(playlistId, trackIds)
    }

    suspend fun getPlaylistsByUserId(userId: String): List<Playlist> {
        return playlistRepository.getPlaylistsByUserId(userId)
    }
} 