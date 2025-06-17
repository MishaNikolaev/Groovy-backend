package com.nmichail.groovy.com.nmichail.groovy.data.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import java.util.*

object JwtConfig {
    const val SECRET = "your-very-secure-secret-key-here"
    const val ISSUER = "your-issuer"
    const val AUDIENCE = "groovy-users"
    const val REALM = "groovy app"
    const val EXPIRATION_TIME = 25L * 60L * 60L * 1000L 

    fun generateToken(userId: String): String {
        return JWT.create()
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .withClaim("userId", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(Algorithm.HMAC256(SECRET))
    }

    fun validateToken(token: String): String? {
        return try {
            val verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .withAudience(AUDIENCE)
                .withIssuer(ISSUER)
                .build()
            
            val decodedJWT = verifier.verify(token)
            decodedJWT.getClaim("userId").asString()
        } catch (e: Exception) {
            null
        }
    }
} 