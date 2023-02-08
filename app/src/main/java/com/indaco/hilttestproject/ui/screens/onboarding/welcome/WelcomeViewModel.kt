package com.indaco.hilttestproject.ui.screens.onboarding.welcome

import androidx.lifecycle.ViewModel
import com.indaco.mylibrary.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    // set current user to null
    fun logout() = userRepository.logout()
}