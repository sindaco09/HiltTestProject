package com.indaco.hilttestproject.ui.screens.onboarding.landing

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.indaco.hilttestproject.R
import com.indaco.hilttestproject.databinding.ActivityLandingBinding
import com.indaco.hilttestproject.ui.base.BaseActivity
import com.indaco.hilttestproject.data.model.User
import com.indaco.hilttestproject.ui.screens.onboarding.login.LoginActivity
import com.indaco.hilttestproject.ui.screens.onboarding.signup.SignUpParentActivity
import com.indaco.hilttestproject.ui.screens.onboarding.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint

/*
 * Hilt Activity to test:
 * + read values from Cache
 * + capture intent when going to another activity
 */
@AndroidEntryPoint
class LandingActivity : BaseActivity<ActivityLandingBinding>() {

    private val viewModel: LandingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingContentView(ActivityLandingBinding.inflate(layoutInflater))

        init()
    }

    private fun init() {
        initView()

        checkIfLoggedIn()

        checkTestValue()
    }

    private fun checkTestValue() {
        if (viewModel.getTestValue())
            binding.testValue.text = getString(R.string.test_value_true)
        else
            binding.testValue.text = getString(R.string.test_value_false)
    }

    private fun checkIfLoggedIn() =
        with(viewModel.isLoggedIn()) {
            if (first) {
                binding.loginStatus.text = getString(R.string.login_status_success)
                goToWelcomeScreen(second!!)
            } else {
                binding.loginStatus.text = getString(R.string.login_status_false)
            }
        }

    private fun goToWelcomeScreen(user: User) =
        startActivity(Intent(this, WelcomeActivity::class.java)
            .putExtra(User.KEY, user))

    private fun initView() =
        with(binding) {
            login.setOnClickListener { goToLoginScreen() }
            signup.setOnClickListener { goToSignUpScreen() }
            hiltLogin.setOnClickListener { goToHiltLoginActivity() }
            nativeLogin.setOnClickListener { goToNativeLoginActivity() }
        }

    private fun goToSignUpScreen() =
        startActivity(Intent(this, SignUpParentActivity::class.java))

    private fun goToLoginScreen() =
        startActivity(Intent(this,LoginActivity::class.java))

    private fun goToHiltLoginActivity() =
        startActivity(Intent().setClassName(packageName,"com.indaco.login.ui.screens.login.hilt_login.HiltLoginActivity"))

    private fun goToNativeLoginActivity() =
        startActivity(Intent().setClassName(packageName,"com.indaco.login.ui.screens.login.native_login.NativeLoginActivity"))
}