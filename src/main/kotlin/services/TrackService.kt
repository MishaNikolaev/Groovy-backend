package services

import com.nmichail.groovy.domain.repository.TrackRepository
import models.Track

class TrackService(
    private val trackRepository: TrackRepository,
    private val cacheService: CacheService
) {
    suspend fun getAllTracks(): List<Track> {
        return trackRepository.getAllTracks()
    }

    suspend fun getTrackById(id: String): Track? {
        return cacheService.getTrack(id) {
            trackRepository.getTrackById(id)
        }
    }

    suspend fun getTracksByAlbumId(albumId: String): List<Track> {
        return trackRepository.getTracksByAlbumId(albumId)
    }

    suspend fun getTracksByArtistId(artistId: String): List<Track> {
        return trackRepository.getTracksByArtistId(artistId)
    }

    suspend fun getLikedTracks(userId: String): List<Track> {
        return trackRepository.getLikedTracks(userId)
    }

    suspend fun searchTracksByTitle(query: String): List<Track> {
        return trackRepository.searchTracksByTitle(query)
    }

    suspend fun getTopTracks(limit: Int = 10): List<Track> {
        return cacheService.getPopularTracks(limit) {
            trackRepository.getTopTracks(limit)
        }
    }

    suspend fun getRecentTracks(limit: Int = 10): List<Track> {
        return trackRepository.getRecentTracks(limit)
    }

    suspend fun getTracksByArtist(artist: String): List<Track> {
        return trackRepository.getTracksByArtist(artist)
    }
} 