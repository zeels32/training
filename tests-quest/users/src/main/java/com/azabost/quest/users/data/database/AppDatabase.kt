package com.azabost.quest.users.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.azabost.quest.users.data.dao.UserDao
import com.azabost.quest.users.data.model.UserJdo

@Database(
    entities = [UserJdo::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}
