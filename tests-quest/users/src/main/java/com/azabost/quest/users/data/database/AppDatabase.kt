package com.azabost.quest.users.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azabost.quest.users.data.dao.UserDao
import com.azabost.quest.users.data.model.UserJdo

@Database(
    entities = [UserJdo::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}
