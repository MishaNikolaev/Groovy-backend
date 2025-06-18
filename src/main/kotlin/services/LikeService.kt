package services

import com.nmichail.groovy.domain.repository.LikeRepository
import models.Like

class LikeService(
    private val likeRepository: LikeRepository
) {
    suspend fun addLike(userId: String, itemId: String, itemType: String): Like {
        val like = Like(
            userId = userId,
            itemId = itemId,
            itemType = itemType
        )
        return likeRepository.addLike(like)
    }

    suspend fun removeLike(userId: String, itemId: String, itemType: String) {
        likeRepository.removeLike(userId, itemId, itemType)
    }

} 