package com.nmichail.groovy.utils

import models.Album
import models.Playlist
import models.Track
import models.User

object TestData {
    fun createTestTrack(
        id: String = "test-track-id",
        title: String = "Test Track",
        artist: String = "Test Artist",
        artistId: String = "test-artist-id",
        albumId: String = "test-album-id",
        coverUrl: String = "https://test.com/cover.jpg",
        storagePath: String = "tracks/test-track.mp3",
        duration: Int = 180
    ) = Track(
        id = id,
        title = title,
        artist = artist,
        artistId = artistId,
        albumId = albumId,
        coverUrl = coverUrl,
        storagePath = storagePath,
        duration = duration
    )


    fun createTestPlaylist(
        id: String = "test-playlist-id",
        name: String = "Test Playlist",
        description: String = "Test Description",
        userId: String = "test-user-id",
        coverUrl: String = "https://test.com/playlist-cover.jpg",
        tracks: List<String> = listOf("test-track-id"),
        createdAt: Long = System.currentTimeMillis(),
        updatedAt: Long = System.currentTimeMillis()
    ) = Playlist(
        id = id,
        name = name,
        description = description,
        userId = userId,
        coverUrl = coverUrl,
        tracks = tracks,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun createTestAlbum(
        id: String = "test-album-id",
        title: String = "Test Album",
        artist: String = "Test Artist",
        coverUrl: String = "https://test.com/album-cover.jpg",
        createdAt: String = "2024-03-20",
        genre: String = "Test Genre"
    ) = Album(
        id = id,
        title = title,
        artist = artist,
        coverUrl = coverUrl,
        createdAt = createdAt,
        genre = genre
    )
} 