package com.indaco.mylibrary.ui.base

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B: ViewBinding> : AppCompatActivity() {

    var _binding: B? = null
    val binding: B get() = _binding!!
    val TAG = this.javaClass.simpleName

    fun setBindingContentView(inflate: B) {
        _binding = inflate.also { setContentView(it.root) }
    }

    fun hideKeyboard() {
        val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { imm.hideSoftInputFromWindow(it.windowToken, 0) }
    }
}
