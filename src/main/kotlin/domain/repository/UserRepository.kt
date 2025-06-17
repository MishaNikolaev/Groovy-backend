package com.nmichail.groovy.com.nmichail.groovy.domain.repository

import com.nmichail.groovy.com.nmichail.groovy.data.entities.User
import java.util.UUID

interface UserRepository {
    fun findByEmail(email: String): User?
    fun save(user: User): User
    fun findById(id: UUID): User?
}