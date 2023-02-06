package com.indaco.hilttestproject.ui.screens.onboarding.signup

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indaco.hilttestproject.R
import com.indaco.hilttestproject.core.espresso.TestCountingIdlingResource
import com.indaco.hilttestproject.data.model.AuthForm
import com.indaco.hilttestproject.data.model.User
import com.indaco.hilttestproject.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    enum class State {SHOW_EMAIL, SHOW_PASSWORD, SHOW_REGISTER_BUTTON}

    private val _emailInUse = MutableLiveData<Pair<Boolean, String?>>()
    val emailInUse: LiveData<Pair<Boolean, String?>> get() = _emailInUse

    private val _registerResult = MutableLiveData<User?>()
    val registerResult: LiveData<User?> get() = _registerResult

    private val _state = MutableStateFlow(State.SHOW_EMAIL)
    val state: StateFlow<State> get() = _state

    private var authForm: AuthForm = AuthForm()

    fun register() {
        viewModelScope.launch(dispatcher) {
            userRepository.register(User(authForm.email)).collect {
                _registerResult.postValue(it)
            }
        }
    }

    fun checkIfEmailInUse(email: String, res: Resources) {
        val emailInUseError = res.getString(R.string.error_email_in_use)

        TestCountingIdlingResource.countingIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            userRepository.isEmailInUse(email).collect {
                if (!it)
                    authForm.email = email

                _emailInUse.postValue(Pair(it, if (it) emailInUseError else null))
                TestCountingIdlingResource.countingIdlingResource.decrement()
            }
        }
    }

    fun addPassword(password: String, confirmPassword: String) {
        authForm.password = password
        authForm.confirmPassword = confirmPassword
    }

    fun changeState(s: State) {
        viewModelScope.launch(dispatcher) {
            if (_state.value.ordinal < s.ordinal)
                _state.emit(s)
        }
    }
}