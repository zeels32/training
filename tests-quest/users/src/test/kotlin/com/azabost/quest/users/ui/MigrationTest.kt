package com.azabost.quest.users.ui

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.azabost.quest.users.data.database.AppDatabase
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MigrationTest {


    private val migrationDb = "migration-test"


    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java
    )

    @Test
    fun migrate1To2() {
        helper.createDatabase(
            name = migrationDb,
            version = 1
        ).use {
            val values = ContentValues().apply {
                put("id", 1)
                put("firstName", "John")
                put("lastName", "Doe")
            }
            it.insert("users", SQLiteDatabase.CONFLICT_FAIL, values)
        }


        helper.runMigrationsAndValidate(
            name = migrationDb,
            version = 2,
            validateDroppedTables = true
        ).use {
            it.query("SELECT * FROM users").use { cursor ->
                if (cursor.moveToFirst()) {
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val isFavorite = cursor.getInt(cursor.getColumnIndex("isFavorite"))
                    val firstName = cursor.getString(cursor.getColumnIndex("firstName"))
                    val lastName = cursor.getString(cursor.getColumnIndex("lastName"))

                    assertEquals(4, cursor.columnCount)

                    Assertions.assertEquals(1, id)
                    Assertions.assertEquals(0, isFavorite)
                    Assertions.assertEquals("John", firstName)
                    Assertions.assertEquals("Doe", lastName)

                } else {
                    assert(false) { "No data found in 'users' table after migration" }
                }
            }
        }


    }

}