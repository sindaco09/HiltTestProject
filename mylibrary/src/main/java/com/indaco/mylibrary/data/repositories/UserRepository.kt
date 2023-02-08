package com.indaco.mylibrary.data.repositories

import com.indaco.mylibrary.data.storage.cache.UserCache
import com.indaco.mylibrary.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userCache: UserCache) {

    val loggedInUser: User? get() = userCache.currentUser

    fun login(email: String, password: String): Flow<User?> {
        return flow {
            val user = userCache.getUser(email)
            userCache.currentUser = user.also { emit(it) }
        }
    }

    // Short-hand way of writing " return flow { emit(...) } "
    fun isEmailInUse(email: String): Flow<Boolean> = flowOf(userCache.getUser(email) != null)

    fun register(user: User): Flow<User?> {
        return flow {
            // API call
            userCache.addUser(user)
            userCache.currentUser = user.also { emit(it) }
        }
    }

    fun logout() {
        userCache.currentUser = null
    }

    fun getTestValue() = userCache.testValue



}