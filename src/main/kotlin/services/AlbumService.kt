package services

import com.nmichail.groovy.domain.repository.AlbumRepository
import models.Album

class AlbumService(
    private val albumRepository: AlbumRepository
) {
    suspend fun getAllAlbums(): List<Album> {
        return albumRepository.getAllAlbums()
    }

    suspend fun getAlbumById(id: String): Album? {
        return albumRepository.getAlbumById(id)
    }

    suspend fun searchAlbumsByTitle(query: String): List<Album> {
        return albumRepository.searchAlbumsByTitle(query)
    }

    suspend fun getAlbumsByGenre(genre: String): List<Album> {
        return albumRepository.getAlbumsByGenre(genre)
    }

    suspend fun getAlbumsByArtist(artist: String): List<Album> {
        return albumRepository.getAlbumsByArtist(artist)
    }
} 