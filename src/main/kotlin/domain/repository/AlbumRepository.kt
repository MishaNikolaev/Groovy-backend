package com.nmichail.groovy.domain.repository

import models.Album

interface AlbumRepository {
    suspend fun getAllAlbums(): List<Album>
    suspend fun getAlbumById(id: String): Album?
    suspend fun searchAlbumsByTitle(query: String): List<Album>
    suspend fun getAlbumsByGenre(genre: String): List<Album>
    suspend fun getAlbumsByArtist(artist: String): List<Album>
} 