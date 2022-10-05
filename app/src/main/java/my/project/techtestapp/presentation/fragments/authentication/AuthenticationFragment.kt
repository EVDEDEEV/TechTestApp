package my.project.techtestapp.presentation.fragments.authentication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import my.project.techtestapp.R
import my.project.techtestapp.databinding.FragmentAuthenticationBinding
import my.project.techtestapp.utils.makeToast
import my.project.techtestapp.utils.safeNavigate

@AndroidEntryPoint
class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {

    private val binding by viewBinding(FragmentAuthenticationBinding::bind)
    private val authenticationViewModel: AuthenticationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClearPhoneFieldButton()
        login()
        setupDataObserver()
    }

    private fun setupDataObserver() {
        authenticationViewModel.authResponse.observe(viewLifecycleOwner) { success ->
            success?.let {
                if (it) {
                    navigateToMainFragment()
                } else {
                    makeToast(getString(R.string.error_answer))
                }
           }
        }
    }

    private fun login() {
        binding.apply {
            enterAccountButton.setOnClickListener {
                val phone = telephoneInputText.text.toString()
                val password = passwordInputText.editText?.text.toString()
                loginFromApi(phone, password)
            }
        }
    }

    private fun navigateToMainFragment() {
        val action =
            AuthenticationFragmentDirections
                .actionAuthenticationFragmentToDevExam()
        view?.findNavController()?.safeNavigate(action)
//        view?.findNavController()?.popBackStack(R.id.authenticationFragment, true)

    }

    private fun loginFromApi(phone: String, password: String) {
        authenticationViewModel.loginFromApi(phone, password)
    }

    private fun initClearPhoneFieldButton() {
        binding.clearTextIcon.setOnClickListener {
            binding.telephoneInputText.text.clear()
        }
    }
}

