package services

import com.nmichail.groovy.domain.repository.TrackRepository
import com.nmichail.groovy.com.nmichail.groovy.domain.repository.UserRepository
import models.Track

class TrackService(
    private val trackRepository: TrackRepository,
) {
    suspend fun getAllTracks(): List<Track> {
        return trackRepository.getAllTracks()
    }

    suspend fun getTrackById(id: String): Track? {
        return trackRepository.getTrackById(id)
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
} 