package com.indaco.hilttestproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonLaunchHiltActivity).setOnClickListener {
            goToHiltLoginActivity()
        }

        findViewById<Button>(R.id.buttonLaunchNativeActivity).setOnClickListener {
            goToNativeLoginActivity()
        }
    }

    private fun goToHiltLoginActivity() =
        startActivity(Intent().setClassName(packageName,"com.indaco.login.ui.screens.login.hilt_login.HiltLoginActivity"))

    private fun goToNativeLoginActivity() =
        startActivity(Intent().setClassName(packageName,"com.indaco.login.ui.screens.login.native_login.NativeLoginActivity"))
}