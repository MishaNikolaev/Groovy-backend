package com.nmichail.groovy.plugins

import com.nmichail.groovy.data.db.migrations.createUsersTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Database")

fun Application.configureDatabase() {
    try {
        val driverClassName = environment.config.propertyOrNull("database.driverClassName")?.getString() 
            ?: "org.postgresql.Driver"
        val jdbcURL = environment.config.propertyOrNull("database.jdbcURL")?.getString() 
            ?: "jdbc:postgresql://localhost:5432/groovy"
        val username = environment.config.propertyOrNull("database.username")?.getString() 
            ?: "postgres"
        val password = environment.config.propertyOrNull("database.password")?.getString() 
            ?: "qw123"
        
        logger.info("Connecting to database: $jdbcURL")
        
        Database.connect(
            url = jdbcURL,
            driver = driverClassName,
            user = username,
            password = password
        )
        
        createUsersTable()
        logger.info("Database migrations completed successfully")
        
    } catch (e: Exception) {
        logger.error("Failed to configure database: ${e.message}", e)
        throw e
    }
} 