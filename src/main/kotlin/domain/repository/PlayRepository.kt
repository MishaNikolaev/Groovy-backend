package com.nmichail.groovy.domain.repository

import models.Play

interface PlayRepository {
    suspend fun recordPlay(play: Play): Play
    suspend fun getPlaysByTrackId(trackId: String): List<Play>
    suspend fun getPlaysByUserId(userId: String): List<Play>
    suspend fun getPlayCount(trackId: String): Long
} 