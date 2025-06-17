package com.nmichail.groovy.data.entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object Users : UUIDTable() {
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val username = varchar("username", 255)
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users)
    
    var email by Users.email
    var passwordHash by Users.passwordHash
    var username by Users.username
    var createdAt by Users.createdAt
    var updatedAt by Users.updatedAt
} 