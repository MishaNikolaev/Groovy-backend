package com.nmichail.groovy.data.di

import com.nmichail.groovy.domain.repository.UserRepository
import com.nmichail.groovy.data.repository.UserRepositoryImpl
import com.nmichail.groovy.services.AuthService
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    
    single { AuthService(get()) }
}