package services

import kotlinx.coroutines.test.runTest
import models.Track
import kotlin.test.*
import kotlinx.coroutines.delay
import java.time.Instant

class CacheServiceTest {
    private lateinit var cacheService: CacheService

    @BeforeTest
    fun setup() {
        cacheService = CacheService()
    }

    @Test
    fun `test getTrack with successful cache hit`() = runTest {
        // Arrange
        val testTrack = Track(
            id = "test-id",
            title = "Test Track",
            artist = "Test Artist",
            albumId = "test-album-id",
            coverUrl = "http://example.com/cover.jpg",
            storagePath = "tracks/test-track.mp3",
            createdAt = Instant.now()
        )
        var loaderCalls = 0

        // Act - First call should use loader
        val result1 = cacheService.getTrack("test-id") {
            loaderCalls++
            testTrack
        }

        // Second call should use cache
        val result2 = cacheService.getTrack("test-id") {
            loaderCalls++
            testTrack
        }

        // Assert
        assertEquals(testTrack, result1)
        assertEquals(testTrack, result2)
        assertEquals(1, loaderCalls, "Loader should only be called once due to caching")
    }

    @Test
    fun `test getTrack with null result from loader`() = runTest {
        // Act
        val result = cacheService.getTrack("non-existent-id") {
            null
        }

        // Assert
        assertNull(result)
    }

    @Test
    fun `test getPopularTracks with cache hit`() = runTest {
        // Arrange
        val testTracks = listOf(
            Track(
                id = "test-id-1",
                title = "Test Track 1",
                artist = "Test Artist",
                albumId = "test-album-id",
                coverUrl = "http://example.com/cover1.jpg",
                storagePath = "tracks/test-track1.mp3",
                createdAt = Instant.now()
            ),
            Track(
                id = "test-id-2",
                title = "Test Track 2",
                artist = "Test Artist",
                albumId = "test-album-id",
                coverUrl = "http://example.com/cover2.jpg",
                storagePath = "tracks/test-track2.mp3",
                createdAt = Instant.now()
            )
        )
        var loaderCalls = 0

        // Act - First call should use loader
        val result1 = cacheService.getPopularTracks(2) {
            loaderCalls++
            testTracks
        }

        // Second call should use cache
        val result2 = cacheService.getPopularTracks(2) {
            loaderCalls++
            testTracks
        }

        // Assert
        assertEquals(testTracks, result1)
        assertEquals(testTracks, result2)
        assertEquals(1, loaderCalls, "Loader should only be called once due to caching")
    }

    @Test
    fun `test getPopularTracks with different limits uses different caches`() = runTest {
        // Arrange
        val testTracks = List(5) { index ->
            Track(
                id = "test-id-$index",
                title = "Test Track $index",
                artist = "Test Artist",
                albumId = "test-album-id",
                coverUrl = "http://example.com/cover$index.jpg",
                storagePath = "tracks/test-track$index.mp3",
                createdAt = Instant.now()
            )
        }
        var loaderCalls = 0

        // Act
        val result1 = cacheService.getPopularTracks(2) {
            loaderCalls++
            testTracks.take(2)
        }

        val result2 = cacheService.getPopularTracks(3) {
            loaderCalls++
            testTracks.take(3)
        }

        // Assert
        assertEquals(2, result1.size)
        assertEquals(3, result2.size)
        assertEquals(2, loaderCalls, "Different limits should use different cache entries")
    }
} 