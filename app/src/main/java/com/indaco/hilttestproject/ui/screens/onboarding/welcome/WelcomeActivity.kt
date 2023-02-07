package com.indaco.hilttestproject.ui.screens.onboarding.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.indaco.hilttestproject.R
import com.indaco.hilttestproject.data.model.User
import com.indaco.hilttestproject.databinding.ActivityWelcomeBinding
import com.indaco.hilttestproject.ui.screens.onboarding.landing.LandingActivity
import com.indaco.mylibrary.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/*
 * Testing
 * + data passed via intent
 * + UI
 */
@AndroidEntryPoint
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {

    private val viewModel: WelcomeViewModel by viewModels()

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingContentView(ActivityWelcomeBinding.inflate(layoutInflater))

        init()
    }

    private fun init() {
        setIntentData()

        binding.logout.setOnClickListener { logout() }

        binding.proceed.setOnClickListener { proceed() }
    }

    private fun proceed() {
//        startActivity(Intent(this, BartActivity::class.java))
    }

    private fun setIntentData() {
        user = intent.getParcelableExtra(User.KEY)!!

        val welcomeMessage = getString(R.string.welcome_message, user.email)

        binding.welcomeTitle.text = welcomeMessage
    }

    private fun logout() {
        viewModel.logout()

        startActivity(Intent(this, LandingActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }
}