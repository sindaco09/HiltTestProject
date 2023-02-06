package com.indaco.hilttestproject.ui.base

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B: ViewBinding>(resId: Int): Fragment(resId) {

    var _binding: B? = null
    val binding: B get() = _binding!!
    val TAG = this.javaClass.simpleName

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun hideKeyboard() {
        val imm: InputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        requireActivity().currentFocus?.let { imm.hideSoftInputFromWindow(it.windowToken, 0) }
    }
}