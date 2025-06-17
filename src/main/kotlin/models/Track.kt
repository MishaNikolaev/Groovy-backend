package models

import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val albumId: String = "",
    val artist: String = "",
    val artistId: String = "",
    val coverUrl: String = "",
    val duration: Int = 0,
    val id: String = "",
    val storagePath: String = "",
    val title: String = ""
)