package models

import kotlinx.serialization.Serializable

@Serializable
data class Play(
    val id: String = "",
    val userId: String = "",
    val trackId: String = "",
    val timestamp: Long = System.currentTimeMillis()
) 