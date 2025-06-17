package com.nmichail.groovy.domain.repository


import models.Track

interface TrackRepository {
    suspend fun getAllTracks(): List<Track>
    suspend fun getTrackById(id: String): Track?
    suspend fun getTracksByAlbumId(albumId: String): List<Track>
    suspend fun getTracksByArtistId(artistId: String): List<Track>
    suspend fun getLikedTracks(userId: String): List<Track>
}