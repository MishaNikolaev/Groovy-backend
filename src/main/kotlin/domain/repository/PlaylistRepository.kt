package com.nmichail.groovy.domain.repository

import models.Playlist

interface PlaylistRepository {
    suspend fun createPlaylist(playlist: Playlist): Playlist
    suspend fun getPlaylistById(id: String): Playlist?
    suspend fun updatePlaylist(playlist: Playlist): Playlist
    suspend fun deletePlaylist(id: String)
    suspend fun addTracksToPlaylist(playlistId: String, trackIds: List<String>)
    suspend fun removeTracksFromPlaylist(playlistId: String, trackIds: List<String>)
    suspend fun getPlaylistsByUserId(userId: String): List<Playlist>
} 