package data.di

import com.nmichail.groovy.com.nmichail.groovy.domain.repository.UserRepository
import com.nmichail.groovy.data.repository.UserRepositoryImpl
import com.nmichail.groovy.domain.repository.*
import data.repository.*
import org.koin.dsl.module
import services.*
import security.AccessControl
import com.nmichail.groovy.com.nmichail.groovy.services.AuthService

val appModule = module {
    single<AlbumRepository> { AlbumRepositoryImpl() }
    single<TrackRepository> { TrackRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    single<PlaylistRepository> { PlaylistRepositoryImpl() }
    single<LikeRepository> { LikeRepositoryImpl() }
    single<PlayRepository> { PlayRepositoryImpl() }

    single { CacheService() }
    single { AccessControl() }
    single { AlbumService(get()) }
    single { TrackService(get(), get()) }
    single { PlaylistService(get()) }
    single { LikeService(get()) }
    single { PlayService(get()) }
    single { AuthService(get()) }
} 