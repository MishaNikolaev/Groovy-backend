package models

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: String = "",
    val title: String = "",
    val artist: String = "",
    val coverUrl: String = "",
    val createdAt: String = "",
    val genre: String = "",
    val artistPhotoUrl: String = "",
    val coverColor: String = "",
)