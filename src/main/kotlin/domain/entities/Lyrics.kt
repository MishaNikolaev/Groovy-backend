package com.nmichail.groovy.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Lyrics(
    val lines: List<LyricLine> = emptyList()
)

@Serializable
data class LyricLine(
    val timeMs: Long = 0L,
    val text: String = ""
)