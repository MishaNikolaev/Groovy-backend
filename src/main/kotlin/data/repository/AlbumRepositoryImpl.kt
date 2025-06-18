package data.repository

import com.google.cloud.firestore.Firestore
import com.nmichail.groovy.domain.repository.AlbumRepository
import data.firebase.FirebaseAdmin
import models.Album
import org.koin.core.component.KoinComponent

class AlbumRepositoryImpl : AlbumRepository, KoinComponent {
    private val db: Firestore = FirebaseAdmin.getDb()

    override suspend fun getAllAlbums(): List<Album> {
        return db.collection("albums")
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                doc.toObject(Album::class.java)
            }
    }

    override suspend fun getAlbumById(id: String): Album? {
        return db.collection("albums")
            .document(id)
            .get()
            .get()
            .toObject(Album::class.java)
    }

    override suspend fun searchAlbumsByTitle(query: String): List<Album> {
        return db.collection("albums")
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                doc.toObject(Album::class.java)
            }
            .filter { album ->
                album.title.contains(query, ignoreCase = true)
            }
    }

    override suspend fun getAlbumsByGenre(genre: String): List<Album> {
        return db.collection("albums")
            .whereEqualTo("genre", genre)
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                doc.toObject(Album::class.java)
            }
    }

    override suspend fun getAlbumsByArtist(artist: String): List<Album> {
        return db.collection("albums")
            .whereEqualTo("artist", artist)
            .get()
            .get()
            .documents
            .mapNotNull { doc ->
                doc.toObject(Album::class.java)
            }
    }

} 