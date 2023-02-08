package com.indaco.mylibrary.di.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.indaco.mylibrary.data.model.User
import com.indaco.mylibrary.data.storage.dao.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
