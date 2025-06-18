package services

import com.nmichail.groovy.domain.repository.PlayRepository
import models.Play

class PlayService(
    private val playRepository: PlayRepository
) {
    suspend fun recordPlay(userId: String, trackId: String): Play {
        val play = Play(
            userId = userId,
            trackId = trackId
        )
        return playRepository.recordPlay(play)
    }

    suspend fun getPlayCount(trackId: String): Long {
        return playRepository.getPlayCount(trackId)
    }
} 