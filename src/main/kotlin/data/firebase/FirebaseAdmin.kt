package data.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import java.io.FileInputStream

object FirebaseAdmin {
    private var db: Firestore? = null

    init {
        val serviceAccount = FileInputStream("src/main/resources/firebase-service-account.json")
        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
        db = FirestoreClient.getFirestore()
    }

    fun getDb(): Firestore {
        return db ?: throw IllegalStateException("Firestore not initialized")
    }
} 