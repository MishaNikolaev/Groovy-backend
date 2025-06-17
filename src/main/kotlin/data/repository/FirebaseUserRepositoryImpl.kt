package com.nmichail.groovy.data.repository

import com.google.cloud.firestore.Firestore
import com.nmichail.groovy.domain.repository.FirebaseUserRepository
import data.firebase.FirebaseAdmin
import models.User

class FirebaseUserRepositoryImpl : FirebaseUserRepository {
    private val db: Firestore = FirebaseAdmin.getDb()

    override suspend fun findByEmail(email: String): User? {
        return db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .get()
            .documents
            .firstOrNull()
            ?.toObject(User::class.java)
    }

    override suspend fun getUserById(id: String): User? {
        return db.collection("users")
            .document(id)
            .get()
            .get()
            .toObject(User::class.java)
    }

    override suspend fun createUser(user: User): User {
        val docRef = db.collection("users").document()
        val newUser = user.copy(id = docRef.id)
        docRef.set(newUser).get()
        return newUser
    }

    override suspend fun updateUser(user: User): User {
        db.collection("users")
            .document(user.id)
            .set(user)
            .get()
        return user
    }

    override suspend fun deleteUser(id: String) {
        db.collection("users")
            .document(id)
            .delete()
            .get()
    }
}