package data.repository

import com.google.cloud.firestore.Firestore
import com.nmichail.groovy.domain.repository.PlayRepository
import data.firebase.FirebaseAdmin
import models.Play
import org.koin.core.component.KoinComponent

class PlayRepositoryImpl : PlayRepository, KoinComponent {
    private val db: Firestore = FirebaseAdmin.getDb()

    override suspend fun recordPlay(play: Play): Play {
        val docRef = db.collection("plays").document()
        val newPlay = play.copy(id = docRef.id)
        docRef.set(newPlay).get()
        return newPlay
    }

    override suspend fun getPlaysByTrackId(trackId: String): List<Play> {
        return db.collection("plays")
            .whereEqualTo("trackId", trackId)
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                doc.toObject(Play::class.java)
            }
    }

    override suspend fun getPlaysByUserId(userId: String): List<Play> {
        return db.collection("plays")
            .whereEqualTo("userId", userId)
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                doc.toObject(Play::class.java)
            }
    }

    override suspend fun getPlayCount(trackId: String): Long {
        return db.collection("plays")
            .whereEqualTo("trackId", trackId)
            .get()
            .get()
            .size()
            .toLong()
    }
} 