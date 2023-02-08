package com.indaco.hilttestproject.ui.screens.onboarding.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.indaco.mylibrary.data.model.User
import com.indaco.hilttestproject.databinding.ActivitySignUpParentBinding
import com.indaco.hilttestproject.ui.screens.onboarding.signup.email.EmailFragment
import com.indaco.hilttestproject.ui.screens.onboarding.signup.password.PasswordFragment
import com.indaco.hilttestproject.ui.screens.onboarding.welcome.WelcomeActivity
import com.indaco.mylibrary.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpParentActivity : BaseActivity<ActivitySignUpParentBinding>() {

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingContentView(ActivitySignUpParentBinding.inflate(layoutInflater))

        init()
    }

    private fun init() {
        observeSignUpState()

        viewModel.registerResult.observe(this) {
            if (it != null)
                goToMainScreen(it)
            else
                showError()
        }
    }

    private fun observeSignUpState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when(it) {
                        SignUpViewModel.State.SHOW_EMAIL -> displayEmailFragment()
                        SignUpViewModel.State.SHOW_PASSWORD -> displayPasswordFragment()
                        SignUpViewModel.State.SHOW_REGISTER_BUTTON -> displayProceedButton()
                    }
                }
            }
        }
    }

    private fun displayEmailFragment() {
        supportFragmentManager.commit {
            add(binding.emailContainer.id, EmailFragment::class.java, bundleOf())
        }
    }

    private fun displayPasswordFragment() {
        supportFragmentManager.commit {
            add(binding.passwordContainer.id, PasswordFragment::class.java, bundleOf())
        }
    }

    private fun displayProceedButton() {
        with(binding.register) {
            alpha = 1F
            setOnClickListener {
                viewModel.register()
            }
        }
    }

    private fun goToMainScreen(user: User) =
        startActivity(
            Intent(this, WelcomeActivity::class.java)
                .putExtra(User.KEY, user))

    private fun showError() = Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

}