package com.indaco.login.ui.screens.login.native_login

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

class NativeLoginViewModel(): ViewModel() {

    private var _loginResult = MutableLiveData<User?>()
    val loginResult: LiveData<User?> get() = _loginResult

    fun login(email: String, password: String) {
        _loginResult.postValue(User(email))
    }
}