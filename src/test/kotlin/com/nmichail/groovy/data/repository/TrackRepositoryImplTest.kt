package com.nmichail.groovy.data.repository

import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QuerySnapshot
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.QueryDocumentSnapshot
import com.nmichail.groovy.base.BaseTest
import com.nmichail.groovy.utils.TestData
import data.repository.TrackRepositoryImpl
import data.firebase.FirebaseAdmin
import io.mockk.*
import kotlinx.coroutines.test.runTest
import models.Track
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class TrackRepositoryImplTest : BaseTest() {
    private lateinit var firestore: Firestore
    private lateinit var trackRepository: TrackRepositoryImpl

    @BeforeEach
    override fun beforeEach() {
        firestore = mockk()
        mockkObject(FirebaseAdmin)
        every { FirebaseAdmin.getDb() } returns firestore
        trackRepository = TrackRepositoryImpl()
    }

    @Test
    fun `getTrackById should return track when exists`() = runTest {
        val trackId = "test-track-id"
        val track = TestData.createTestTrack(id = trackId)
        val documentSnapshot = mockk<DocumentSnapshot>()
        val documentReference = mockk<DocumentReference>()

        coEvery { firestore.collection("tracks").document(trackId) } returns documentReference
        coEvery { documentReference.get().get() } returns documentSnapshot
        coEvery { documentSnapshot.exists() } returns true
        coEvery { documentSnapshot.toObject(Track::class.java) } returns track

        val result = trackRepository.getTrackById(trackId)

        assertNotNull(result)
        assertEquals(trackId, result.id)
        coVerify(exactly = 1) { firestore.collection("tracks").document(trackId) }
        coVerify(exactly = 1) { documentReference.get().get() }
        coVerify(exactly = 1) { documentSnapshot.toObject(Track::class.java) }
    }

    @Test
    fun `getTrackById should return null when track does not exist`() = runTest {
        val trackId = "nonexistent-track-id"
        val documentSnapshot = mockk<DocumentSnapshot>()
        val documentReference = mockk<DocumentReference>()

        coEvery { firestore.collection("tracks").document(trackId) } returns documentReference
        coEvery { documentReference.get().get() } returns documentSnapshot
        coEvery { documentSnapshot.exists() } returns false

        val result = trackRepository.getTrackById(trackId)

        assertNull(result)
        coVerify(exactly = 1) { firestore.collection("tracks").document(trackId) }
        coVerify(exactly = 1) { documentReference.get().get() }
        coVerify(exactly = 0) { documentSnapshot.toObject(any<Class<*>>()) }
    }

    @Test
    fun `getTracksByAlbumId should return list of tracks`() = runTest {
        val albumId = "test-album-id"
        val tracks = listOf(
            TestData.createTestTrack(id = "track-1", albumId = albumId),
            TestData.createTestTrack(id = "track-2", albumId = albumId)
        )
        val querySnapshot = mockk<QuerySnapshot>()
        val documentSnapshots = tracks.map { track ->
            mockk<DocumentSnapshot>().apply {
                every { toObject(Track::class.java) } returns track
                every { exists() } returns true
            }
        }

        coEvery { 
            firestore.collection("tracks")
                .whereEqualTo("albumId", albumId)
                .get()
                .get()
        } returns querySnapshot
        every { querySnapshot.documents } returns documentSnapshots as List<QueryDocumentSnapshot?>
        every { querySnapshot.isEmpty } returns false

        val result = trackRepository.getTracksByAlbumId(albumId)

        assertEquals(2, result.size)
        assertEquals(albumId, result[0].albumId)
        assertEquals(albumId, result[1].albumId)
        coVerify(exactly = 1) { 
            firestore.collection("tracks")
                .whereEqualTo("albumId", albumId)
                .get()
                .get()
        }
        documentSnapshots.forEach { 
            verify(exactly = 1) { it.toObject(Track::class.java) }
        }
    }

    @Test
    fun `createTrack should save track to Firestore`() = runTest {
        val track = TestData.createTestTrack()
        val documentReference = mockk<DocumentReference>()

        coEvery { firestore.collection("tracks").document(track.id) } returns documentReference
        coEvery { documentReference.set(track).get() } returns mockk()
        val result = trackRepository.createTrack(track)

        assertNotNull(result)
        assertEquals(track.id, result.id)
        assertEquals(track.title, result.title)
        coVerify(exactly = 1) { firestore.collection("tracks").document(track.id) }
        coVerify(exactly = 1) { documentReference.set(track).get() }
    }

    @Test
    fun `deleteTrack should remove track from Firestore`() = runTest {
        val trackId = "test-track-id"
        val documentReference = mockk<DocumentReference>()

        coEvery { firestore.collection("tracks").document(trackId) } returns documentReference
        coEvery { documentReference.delete().get() } returns mockk()

        trackRepository.deleteTrack(trackId)

        coVerify(exactly = 1) { firestore.collection("tracks").document(trackId) }
        coVerify(exactly = 1) { documentReference.delete().get() }
    }
} 