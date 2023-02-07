package com.indaco.login.ui.screens.login.native_login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.indaco.hilttestproject.data.model.User
import com.indaco.hilttestproject.ui.base.BaseActivity
import com.indaco.hilttestproject.ui.screens.onboarding.welcome.WelcomeActivity
import com.indaco.login.databinding.ActivityNativeLoginBinding

class NativeLoginActivity : BaseActivity<ActivityNativeLoginBinding>() {

    private val viewModel by viewModels<NativeLoginViewModel>()

    companion object {
        private const val MIN = 3
        private const val MAX = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingContentView(ActivityNativeLoginBinding.inflate(layoutInflater))

        init()
    }

    private fun init() {
        initViews()

        observeData()
    }

    private fun initViews() {
        binding.submit.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (fieldsAreValid(email, password))
                login(email, password)
        }
    }

    private fun fieldsAreValid(email: String?, password: String?): Boolean {
        val isEmailValid = isEmailEntryValid(email)
        val isPasswordValid = isPasswordValid(password)
        return isEmailValid && isPasswordValid
    }

    private fun isEmailEntryValid(email: String?): Boolean {
        val error =  when {
            email.isNullOrBlank() -> getString(com.indaco.hilttestproject.R.string.error_email_blank)
            email.isEmailInvalid() -> getString(com.indaco.hilttestproject.R.string.error_email_not_email_pattern)
            else ->  null
        }
        binding.emailLayout.error = error
        return error == null
    }

    private fun String.isEmailInvalid(): Boolean {
        return TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun isPasswordValid(password: String?): Boolean {
        val error =  when {
            password.isNullOrBlank() -> getString(com.indaco.hilttestproject.R.string.error_password_blank)
            password.length < MIN -> getString(com.indaco.hilttestproject.R.string.error_password_short)
            password.length > MAX -> getString(com.indaco.hilttestproject.R.string.error_password_long)
            password.any { !it.isLetterOrDigit() } -> getString(com.indaco.hilttestproject.R.string.error_password_not_alphanumeric)
            else ->  null
        }
        binding.passwordLayout.error = error
        return error == null
    }

    private fun login(email: String, password: String) {
        viewModel.login(email, password)
    }

    private fun observeData() {
        viewModel.loginResult.observe(this) {
            if (it != null)
                goToMainScreen(it)
            else
                showError()
        }
    }

    private fun showError() {
        AlertDialog.Builder(this)
            .setTitle(getString(com.indaco.hilttestproject.R.string.error_title))
            .setMessage(getString(com.indaco.hilttestproject.R.string.user_not_found))
            .setPositiveButton(getString(com.indaco.hilttestproject.R.string.ok), null)
            .show()
    }

    private fun goToMainScreen(user: User) =
        startActivity(Intent(this, WelcomeActivity::class.java).putExtra(User.KEY, user))
}