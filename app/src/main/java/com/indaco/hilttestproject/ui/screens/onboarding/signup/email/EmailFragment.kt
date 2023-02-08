package com.indaco.hilttestproject.ui.screens.onboarding.signup.email

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.indaco.hilttestproject.R
import com.indaco.hilttestproject.databinding.FragmentEmailBinding
import com.indaco.mylibrary.ui.base.BaseFragment
import com.indaco.hilttestproject.ui.screens.onboarding.signup.SignUpViewModel
import com.indaco.hilttestproject.util.Validator
import com.indaco.hilttestproject.util.Validator.inputIsValid
import com.indaco.mylibrary.util.BaseString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailFragment: com.indaco.mylibrary.ui.base.BaseFragment<FragmentEmailBinding>(R.layout.fragment_email) {

    private val viewModel: SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEmailBinding.bind(view)

        init()
    }

    private fun init() {
        viewModel.emailInUse.observe(viewLifecycleOwner) {
            if (!it.first)
                goToPasswordScreen()
            else
                showError(it.second!!)
        }

        binding.email.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT && emailIsValid()) {
                viewModel.checkIfEmailInUse(binding.email.text.toString(), resources)
                true
            } else
                false
        }
    }

    private fun emailIsValid(): Boolean {
        return binding.emailLayout.inputIsValid(Validator.Type.EMAIL)
    }

    private fun showError(error: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(BaseString.error_title)
            .setMessage(error)
            .show()
    }

    private fun goToPasswordScreen() {
        viewModel.changeState(SignUpViewModel.State.SHOW_PASSWORD)
    }
}