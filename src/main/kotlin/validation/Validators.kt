package validation

import org.valiktor.functions.*
import org.valiktor.validate
import models.Playlist
import models.User

object Validators {
    fun validatePlaylist(playlist: Playlist) {
        validate(playlist) {
            validate(Playlist::name).isNotEmpty().hasSize(min = 1, max = 100)
            validate(Playlist::description).hasSize(max = 500)
            validate(Playlist::userId).isNotEmpty()
            validate(Playlist::tracks).hasSize(max = 1000)
        }
    }
} 