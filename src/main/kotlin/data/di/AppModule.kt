package data.di

import com.nmichail.groovy.domain.repository.TrackRepository
import com.nmichail.groovy.com.nmichail.groovy.domain.repository.UserRepository
import com.nmichail.groovy.data.repository.FirebaseUserRepositoryImpl
import com.nmichail.groovy.data.repository.UserRepositoryImpl
import com.nmichail.groovy.domain.repository.FirebaseUserRepository
import com.nmichail.groovy.domain.repository.AlbumRepository
import data.repository.AlbumRepositoryImpl
import data.repository.TrackRepositoryImpl
import org.koin.dsl.module
import services.AlbumService
import services.TrackService

val appModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<TrackRepository> { TrackRepositoryImpl() }
    single<AlbumRepository> { AlbumRepositoryImpl() }
    single { TrackService(get()) }
    single { AlbumService(get()) }
    single<FirebaseUserRepository> { FirebaseUserRepositoryImpl() }
} 