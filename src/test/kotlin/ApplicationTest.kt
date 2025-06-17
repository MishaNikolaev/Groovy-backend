package com.nmichail

import com.nmichail.groovy.module
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import io.ktor.server.plugins.openapi.*
import io.ktor.server.http.content.*

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

}

fun Application.configureSwagger() {
    routing {
        swaggerUI(path = "/", swaggerFile = "openapi/documentation.yaml")
        static("/openapi") {
            resources("openapi")
        }
    }
}
