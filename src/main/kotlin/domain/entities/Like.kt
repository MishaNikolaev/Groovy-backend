package models

import kotlinx.serialization.Serializable

@Serializable
data class Like(
    val id: String = "",
    val userId: String = "",
    val itemId: String = "",
    val itemType: String = "",
    val createdAt: Long = System.currentTimeMillis()
) 