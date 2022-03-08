package com.michaelmccormick.usermanager

import com.michaelmccormick.usermanager.repositories.di.DataModule
import com.michaelmccormick.usermanager.repositories.interfaces.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [DataModule::class],
)
abstract class FakeDataModule {
    @Binds
    internal abstract fun bindUserRepository(
        fakeUserRepositoryImpl: FakeUserRepositoryImpl,
    ): UserRepository
}
