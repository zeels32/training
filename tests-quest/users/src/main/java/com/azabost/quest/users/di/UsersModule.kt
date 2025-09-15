package com.azabost.quest.users.di

import com.azabost.quest.users.repository.UsersRepository
import com.azabost.quest.users.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UsersModule {

    @Binds
    fun bindUsersRepository(impl: UsersRepositoryImpl): UsersRepository

}