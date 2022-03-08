package com.michaelmccormick.usermanager.repositories.di

import com.michaelmccormick.usermanager.repositories.implementations.UserRepositoryImpl
import com.michaelmccormick.usermanager.repositories.interfaces.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository
}
