package com.indaco.login.ui.screens.login.hilt_login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indaco.hilttestproject.core.hilt.IODispatcher
import com.indaco.hilttestproject.data.model.User
import com.indaco.hilttestproject.data.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

open class HiltLoginViewModel @Inject constructor(
    private val repository: UserRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
): ViewModel() {

    internal var _loginResult = MutableLiveData<User?>()
    val loginResult: LiveData<User?> get() = _loginResult

    open fun login(email: String, password: String) {
        Log.d("TAG","login")
        viewModelScope.launch(dispatcher) {
            repository.login(email, password)
                .collect { _loginResult.postValue(it) }
        }
    }
}