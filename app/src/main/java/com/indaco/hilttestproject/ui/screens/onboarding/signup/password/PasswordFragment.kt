package com.indaco.hilttestproject.ui.screens.onboarding.signup.password

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import com.indaco.hilttestproject.R
import com.indaco.hilttestproject.databinding.FragmentPasswordBinding
import com.indaco.mylibrary.ui.base.BaseFragment
import com.indaco.hilttestproject.ui.screens.onboarding.signup.SignUpViewModel
import com.indaco.hilttestproject.util.Validator
import com.indaco.hilttestproject.util.Validator.inputIsValid
import com.indaco.hilttestproject.util.Validator.matches
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment: com.indaco.mylibrary.ui.base.BaseFragment<FragmentPasswordBinding>(R.layout.fragment_password) {

    private val viewModel: SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPasswordBinding.bind(view)

        init()
    }

    private fun init() {
        with(binding) {
            password.setOnEditorActionListener { _, _, _ ->
                !passwordLayout.inputIsValid(Validator.Type.PASSWORD)
            }
            confirmPassword.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    submitPasswords()
                    true
                } else
                    false
            }
        }
    }

    private fun submitPasswords() {
        hideKeyboard()
        if (fieldsAreValid()) {
            viewModel.addPassword(binding.password.text.toString(), binding.confirmPassword.text.toString())
            viewModel.changeState(SignUpViewModel.State.SHOW_REGISTER_BUTTON)
        }
    }

    private fun fieldsAreValid(): Boolean {
        val isPasswordValid = binding.passwordLayout.inputIsValid(Validator.Type.PASSWORD)
        val isConfirmPasswordValid = binding.confirmPasswordLayout.inputIsValid(Validator.Type.PASSWORD)
        return if (isPasswordValid && isConfirmPasswordValid)
            binding.confirmPasswordLayout.matches(
                Validator.Type.PASSWORD,
                binding.password.text.toString()
            )
        else
            false
    }
}