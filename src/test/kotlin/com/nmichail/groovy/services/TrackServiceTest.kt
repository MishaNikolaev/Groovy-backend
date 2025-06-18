package com.nmichail.groovy.services

import com.nmichail.groovy.base.BaseTest
import com.nmichail.groovy.domain.repository.TrackRepository
import com.nmichail.groovy.utils.TestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import models.Track
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import services.CacheService
import services.TrackService
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TrackServiceTest : BaseTest() {
    private lateinit var trackRepository: TrackRepository
    private lateinit var cacheService: CacheService
    private lateinit var trackService: TrackService

    @BeforeEach
    override fun beforeEach() {
        trackRepository = mockk()
        cacheService = mockk()
        trackService = TrackService(trackRepository, cacheService)
    }

    @Test
    fun `getTrackById should return cached track when available`() = runTest {
        val trackId = "test-track-id"
        val cachedTrack = TestData.createTestTrack(id = trackId)

        coEvery { cacheService.getTrack(any(), any()) } returns cachedTrack

        val result = trackService.getTrackById(trackId)

        assertNotNull(result)
        assertEquals(trackId, result.id)
        coVerify(exactly = 1) { cacheService.getTrack(any(), any()) }
        coVerify(exactly = 0) { trackRepository.getTrackById(any()) }
    }

    @Test
    fun `getTrackById should fetch from repository when cache miss`() = runTest {
        val trackId = "test-track-id"
        val track = TestData.createTestTrack(id = trackId)

        coEvery { cacheService.getTrack(any(), any()) } returns track
        coEvery { trackRepository.getTrackById(trackId) } returns track

        val result = trackService.getTrackById(trackId)

        assertNotNull(result)
        assertEquals(trackId, result.id)
        coVerify(exactly = 1) { cacheService.getTrack(any(), any()) }
        coVerify(exactly = 1) { trackRepository.getTrackById(trackId) }
    }

    @Test
    fun `getTrackById should throw exception when track not found`() = runTest {
        val trackId = "nonexistent-track-id"

        coEvery { cacheService.getTrack(any(), any()) } returns null
        coEvery { trackRepository.getTrackById(trackId) } returns null

        assertThrows<IllegalArgumentException> {
            trackService.getTrackById(trackId)
        }

        coVerify(exactly = 1) { cacheService.getTrack(any(), any()) }
        coVerify(exactly = 1) { trackRepository.getTrackById(trackId) }
    }

    @Test
    fun `getTracksByAlbumId should return tracks from repository`() = runTest {
        val albumId = "test-album-id"
        val tracks = listOf(
            TestData.createTestTrack(id = "track-1", albumId = albumId),
            TestData.createTestTrack(id = "track-2", albumId = albumId)
        )

        coEvery { trackRepository.getTracksByAlbumId(albumId) } returns tracks

        val result = trackService.getTracksByAlbumId(albumId)

        assertEquals(2, result.size)
        assertEquals(albumId, result[0].albumId)
        assertEquals(albumId, result[1].albumId)
        coVerify(exactly = 1) { trackRepository.getTracksByAlbumId(albumId) }
    }

} 