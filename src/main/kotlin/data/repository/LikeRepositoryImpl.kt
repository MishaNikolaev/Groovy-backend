package data.repository

import com.google.cloud.firestore.Firestore
import com.nmichail.groovy.domain.repository.LikeRepository
import data.firebase.FirebaseAdmin
import models.Like
import org.koin.core.component.KoinComponent

class LikeRepositoryImpl : LikeRepository, KoinComponent {
    private val db: Firestore = FirebaseAdmin.getDb()

    override suspend fun addLike(like: Like): Like {
        val docRef = db.collection("likes").document()
        val newLike = like.copy(id = docRef.id)
        docRef.set(newLike).get()
        return newLike
    }

    override suspend fun removeLike(userId: String, itemId: String, itemType: String) {
        db.collection("likes")
            .whereEqualTo("userId", userId)
            .whereEqualTo("itemId", itemId)
            .whereEqualTo("itemType", itemType)
            .get()
            .get()
            .documents
            .forEach { doc ->
                doc.reference.delete().get()
            }
    }

    override suspend fun getLike(userId: String, itemId: String, itemType: String): Like? {
        return db.collection("likes")
            .whereEqualTo("userId", userId)
            .whereEqualTo("itemId", itemId)
            .whereEqualTo("itemType", itemType)
            .get()
            .get()
            .documents
            .firstOrNull()
            ?.toObject(Like::class.java)
    }

    override suspend fun getLikesByUserId(userId: String): List<Like> {
        return db.collection("likes")
            .whereEqualTo("userId", userId)
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                doc.toObject(Like::class.java)
            }
    }

    override suspend fun getLikesByItemId(itemId: String, itemType: String): List<Like> {
        return db.collection("likes")
            .whereEqualTo("itemId", itemId)
            .whereEqualTo("itemType", itemType)
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                doc.toObject(Like::class.java)
            }
    }
} 