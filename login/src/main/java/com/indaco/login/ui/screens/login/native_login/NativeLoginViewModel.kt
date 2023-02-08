package com.indaco.login.ui.screens.login.native_login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.indaco.mylibrary.data.model.User

class NativeLoginViewModel(): ViewModel() {

    private var _loginResult = MutableLiveData<User?>()
    val loginResult: LiveData<User?> get() = _loginResult

    fun login(email: String, password: String) {
        _loginResult.postValue(User(email))
    }
}