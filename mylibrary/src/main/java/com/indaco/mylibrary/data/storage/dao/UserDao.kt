package com.indaco.mylibrary.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.indaco.mylibrary.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun addUser(user: User)

    @Query("SELECT * FROM users")
    fun getUsers(): List<User?>?

    @Query("SELECT * FROM users WHERE email == :email LIMIT 1")
    fun getUser(email: String): User?

    @Delete
    fun removeUser(user: User)
}