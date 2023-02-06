package com.indaco.hilttestproject.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.indaco.hilttestproject.data.storage.dao.UserDao
import com.indaco.hilttestproject.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
