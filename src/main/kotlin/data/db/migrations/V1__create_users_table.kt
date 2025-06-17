package com.nmichail.groovy.com.nmichail.groovy.data.db.migrations

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.nmichail.groovy.com.nmichail.groovy.data.entities.Users

fun createUsersTable() {
    transaction {
        SchemaUtils.create(Users)
    }
} 