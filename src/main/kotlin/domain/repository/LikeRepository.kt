package com.nmichail.groovy.domain.repository

import models.Like

interface LikeRepository {
    suspend fun addLike(like: Like): Like
    suspend fun removeLike(userId: String, itemId: String, itemType: String)
    suspend fun getLike(userId: String, itemId: String, itemType: String): Like?
    suspend fun getLikesByUserId(userId: String): List<Like>
    suspend fun getLikesByItemId(itemId: String, itemType: String): List<Like>
} 