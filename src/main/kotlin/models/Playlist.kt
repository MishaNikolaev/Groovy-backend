package models

import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val userId: String = "",
    val coverUrl: String = "",
    val tracks: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) 