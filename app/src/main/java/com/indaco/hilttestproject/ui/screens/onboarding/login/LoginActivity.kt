package com.indaco.hilttestproject.ui.screens.onboarding.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.indaco.mylibrary.data.model.User
import com.indaco.hilttestproject.databinding.ActivityLoginBinding
import com.indaco.hilttestproject.ui.screens.onboarding.welcome.WelcomeActivity
import com.indaco.mylibrary.ui.base.BaseActivity
import com.indaco.mylibrary.util.BaseString
import dagger.hilt.android.AndroidEntryPoint

/*
 * Hilt Activity to test:
 * + read values from Cache
 * + capture intent when going to another activity
 */
@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    companion object {
        private const val MIN = 3
        private const val MAX = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingContentView(ActivityLoginBinding.inflate(layoutInflater))

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
            email.isNullOrBlank() -> getString(BaseString.error_email_blank)
            email.isEmailInvalid() -> getString(BaseString.error_email_not_email_pattern)
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
            password.isNullOrBlank() -> getString(BaseString.error_password_blank)
            password.length < MIN -> getString(BaseString.error_password_short)
            password.length > MAX -> getString(BaseString.error_password_long)
            password.any { !it.isLetterOrDigit() } -> getString(BaseString.error_password_not_alphanumeric)
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
            .setTitle(getString(BaseString.error_title))
            .setMessage(getString(BaseString.user_not_found))
            .setPositiveButton(getString(BaseString.ok), null)
            .show()
    }

    private fun goToMainScreen(user: User) =
        startActivity(Intent(this, WelcomeActivity::class.java).putExtra(User.KEY, user))
}