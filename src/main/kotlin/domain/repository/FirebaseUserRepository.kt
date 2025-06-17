package com.nmichail.groovy.domain.repository

import models.User

interface FirebaseUserRepository {
    suspend fun findByEmail(email: String): User?
    suspend fun getUserById(id: String): User?
    suspend fun createUser(user: User): User
    suspend fun updateUser(user: User): User
    suspend fun deleteUser(id: String)
}