package com.nmichail.groovy.data.repository

import com.nmichail.groovy.data.entities.User
import com.nmichail.groovy.data.entities.Users
import com.nmichail.groovy.domain.repository.UserRepository
import java.util.UUID

class UserRepositoryImpl : UserRepository {
    override fun findByEmail(email: String): User? {
        return User.find { Users.email eq email }.firstOrNull()
    }

    override fun save(user: User): User {
        return user
    }

    override fun findById(id: UUID): User? {
        return User.findById(id)
    }
}