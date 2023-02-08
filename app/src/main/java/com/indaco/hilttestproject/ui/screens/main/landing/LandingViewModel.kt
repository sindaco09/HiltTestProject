package com.indaco.hilttestproject.ui.screens.main.landing

import androidx.lifecycle.ViewModel
import com.indaco.mylibrary.data.repositories.UserRepository
import com.indaco.mylibrary.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    // if user exists, user is logged in
    fun isLoggedIn(): Pair<Boolean, User?> {
        return repository.loggedInUser.let { Pair(it != null, it) }
    }

    fun getTestValue() = repository.getTestValue()
}