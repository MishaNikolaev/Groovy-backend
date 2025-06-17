package data.di

import com.nmichail.groovy.domain.repository.TrackRepository
import com.nmichail.groovy.com.nmichail.groovy.domain.repository.UserRepository
import com.nmichail.groovy.data.repository.FirebaseUserRepositoryImpl
import com.nmichail.groovy.data.repository.UserRepositoryImpl
import com.nmichail.groovy.domain.repository.FirebaseUserRepository
import data.repository.TrackRepositoryImpl
import org.koin.dsl.module
import services.TrackService

val appModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<TrackRepository> { TrackRepositoryImpl() }
    single { TrackService(get()) }
    single<FirebaseUserRepository> { FirebaseUserRepositoryImpl() }

} 