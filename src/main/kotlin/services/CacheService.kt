package services

import io.github.reactivecircus.cache4k.Cache
import kotlin.time.Duration.Companion.minutes
import models.Track
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CacheService {
    private val mutex = Mutex()
    private val trackCache: Cache<String, Track> = Cache.Builder<String, Track>()
        .maximumCacheSize(1000)
        .expireAfterWrite(30.minutes)
        .build()
        
    private val popularTracksCache: Cache<Int, List<Track>> = Cache.Builder<Int, List<Track>>()
        .maximumCacheSize(100)
        .expireAfterWrite(60.minutes)
        .build()

    suspend fun getTrack(id: String, loader: suspend () -> Track?): Track? = withContext(Dispatchers.IO) {
        mutex.withLock {
            try {
                trackCache.get(id) {
                    runCatching { loader() }.getOrNull() ?: throw CacheEntryNotFoundException()
                }
            } catch (e: CacheEntryNotFoundException) {
                null
            }
        }
    }

    suspend fun getPopularTracks(limit: Int, loader: suspend () -> List<Track>): List<Track> = withContext(Dispatchers.IO) {
        mutex.withLock {
            popularTracksCache.get(limit) { loader() }
        }
    }

    suspend fun delete(id: String) = withContext(Dispatchers.IO) {
        mutex.withLock {
            trackCache.invalidate(id)
        }
    }
}

private class CacheEntryNotFoundException : Exception() 