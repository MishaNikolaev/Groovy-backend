package data.repository

import com.google.cloud.firestore.Firestore
import com.nmichail.groovy.domain.repository.TrackRepository
import data.firebase.FirebaseAdmin
import models.Track
import org.apache.commons.logging.Log
import org.koin.core.component.KoinComponent
import org.slf4j.LoggerFactory

class TrackRepositoryImpl : TrackRepository, KoinComponent {
    private val db: Firestore = FirebaseAdmin.getDb()
    private val logger = LoggerFactory.getLogger(TrackRepositoryImpl::class.java)

    override suspend fun getAllTracks(): List<Track> {
        return db.collection("tracks")
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                try {
                    logger.info("[TrackRepo] Trying to deserialize track: id=${doc.id}, data=${doc.data}")
                    val track = doc.toObject(Track::class.java)
                    if (track == null) {
                        logger.warn("[TrackRepo][WARN] Deserialized track is null! id=${doc.id}, data=${doc.data}")
                    } else {
                        logger.info("[TrackRepo][OK] Successfully deserialized track: id=${doc.id}, track=$track")
                    }
                    track
                } catch (e: Exception) {
                    logger.error("[TrackRepo][ERROR] Failed to deserialize track with id=${doc.id}, data=${doc.data}, error=${e.message}")
                    null
                }
            }
    }

    override suspend fun getTrackById(id: String): Track? {
        return try {
            db.collection("tracks")
                .document(id)
                .get()
                .get()
                .toObject(Track::class.java)
        } catch (e: Exception) {
            logger.error("[TrackRepo][ERROR] Failed to deserialize track with id=$id: ${e.message}")
            null
        }
    }

    override suspend fun getTracksByAlbumId(albumId: String): List<Track> {
        return db.collection("tracks")
            .whereEqualTo("albumId", albumId)
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                try {
                    logger.info("[TrackRepo] Trying to deserialize track: id=${doc.id}, data=${doc.data}")
                    val track = doc.toObject(Track::class.java)
                    if (track == null) {
                        logger.warn("[TrackRepo][WARN] Deserialized track is null! id=${doc.id}, data=${doc.data}")
                    } else {
                        logger.info("[TrackRepo][OK] Successfully deserialized track: id=${doc.id}, track=$track")
                    }
                    track
                } catch (e: Exception) {
                    logger.error("[TrackRepo][ERROR] Failed to deserialize track with id=${doc.id}, data=${doc.data}, error=${e.message}")
                    null
                }
            }
    }

    override suspend fun getTracksByArtistId(artistId: String): List<Track> {
        return db.collection("tracks")
            .whereEqualTo("artistId", artistId)
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                try {
                    logger.info("[TrackRepo] Trying to deserialize track: id=${doc.id}, data=${doc.data}")
                    val track = doc.toObject(Track::class.java)
                    if (track == null) {
                        logger.warn("[TrackRepo][WARN] Deserialized track is null! id=${doc.id}, data=${doc.data}")
                    } else {
                        logger.info("[TrackRepo][OK] Successfully deserialized track: id=${doc.id}, track=$track")
                    }
                    track
                } catch (e: Exception) {
                    logger.error("[TrackRepo][ERROR] Failed to deserialize track with id=${doc.id}, data=${doc.data}, error=${e.message}")
                    null
                }
            }
    }

    override suspend fun getLikedTracks(userId: String): List<Track> {
        val likedTracks = db.collection("likes")
            .whereEqualTo("userId", userId)
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                doc.getString("trackId")
            }

        return likedTracks.mapNotNull { trackId ->
            getTrackById(trackId)
        }
    }

    override suspend fun searchTracksByTitle(query: String): List<Track> {
        return db.collection("tracks")
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                try {
                    doc.toObject(Track::class.java)
                } catch (e: Exception) {
                    logger.error("[TrackRepo][ERROR] Failed to deserialize track with id=${doc.id}: ${e.message}")
                    null
                }
            }
            .filter { track ->
                track.title?.contains(query, ignoreCase = true) == true
            }
    }

    override suspend fun getTopTracks(limit: Int): List<Track> {

        return db.collection("tracks")
            .limit(limit.toLong().toInt())
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                try {
                    doc.toObject(Track::class.java)
                } catch (e: Exception) {
                    logger.error("[TrackRepo][ERROR] Failed to deserialize track with id=${doc.id}: ${e.message}")
                    null
                }
            }
    }

    override suspend fun getRecentTracks(limit: Int): List<Track> {
        return db.collection("tracks")
            .limit(limit.toLong().toInt())
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                try {
                    doc.toObject(Track::class.java)
                } catch (e: Exception) {
                    logger.error("[TrackRepo][ERROR] Failed to deserialize track with id=${doc.id}: ${e.message}")
                    null
                }
            }
    }

    override suspend fun getTracksByArtist(artist: String): List<Track> {
        return db.collection("tracks")
            .whereEqualTo("artist", artist)
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                try {
                    doc.toObject(Track::class.java)
                } catch (e: Exception) {
                    logger.error("[TrackRepo][ERROR] Failed to deserialize track with id=${doc.id}: ${e.message}")
                    null
                }
            }
    }

    override suspend fun createTrack(track: Track): Track {
        db.collection("tracks")
            .document(track.id ?: "")
            .set(track)
            .get()
        return track
    }

    override suspend fun deleteTrack(id: String) {
        db.collection("tracks")
            .document(id)
            .delete()
            .get()
    }
} 