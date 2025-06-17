package com.nmichail.groovy.com.nmichail.groovy.services

import com.nmichail.groovy.com.nmichail.groovy.data.config.JwtConfig
import com.nmichail.groovy.com.nmichail.groovy.data.dtos.auth.AuthResponse
import com.nmichail.groovy.com.nmichail.groovy.data.dtos.auth.LoginRequest
import com.nmichail.groovy.com.nmichail.groovy.data.dtos.auth.RegisterRequest
import com.nmichail.groovy.com.nmichail.groovy.data.dtos.auth.UserDto
import com.nmichail.groovy.com.nmichail.groovy.data.entities.User
import com.nmichail.groovy.com.nmichail.groovy.domain.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

class AuthService(
    private val userRepository: UserRepository
) {
    
    fun register(request: RegisterRequest): AuthResponse = transaction {
        userRepository.findByEmail(request.email)?.let {
            throw IllegalStateException("User with this email already exists")
        }
        
        val user = User.new {
            email = request.email
            passwordHash = BCrypt.hashpw(request.password, BCrypt.gensalt())
            username = request.username
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }

        val savedUser = userRepository.save(user)
        val token = JwtConfig.generateToken(savedUser.id.value.toString())

        AuthResponse(
            user = savedUser.toDto(),
            token = token
        )
    }
    
    fun login(request: LoginRequest): AuthResponse = transaction {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalStateException("Invalid email or password")
            
        if (!BCrypt.checkpw(request.password, user.passwordHash)) {
            throw IllegalStateException("Invalid email or password")
        }

        val token = JwtConfig.generateToken(user.id.value.toString())

        AuthResponse(
            user = user.toDto(),
            token = token
        )
    }
    
    private fun User.toDto() = UserDto(
        id = id.value.toString(),
        email = email,
        username = username
    )
} 