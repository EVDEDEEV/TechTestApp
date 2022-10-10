package my.project.techtestapp.presentation.fragments.authentication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import my.project.techtestapp.R
import my.project.techtestapp.databinding.FragmentAuthenticationBinding
import my.project.techtestapp.utils.LoginState
import my.project.techtestapp.utils.changeXtoNumber
import my.project.techtestapp.utils.makeToast
import my.project.techtestapp.utils.safeNavigate


@AndroidEntryPoint
class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {

    private val binding by viewBinding(FragmentAuthenticationBinding::bind)
    private val authenticationViewModel: AuthenticationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMask()
        initClearPhoneFieldButton()
        setupLoginSuccessStatusObserver()
        setupEnterButtonListener()
        setupMaskObserver()
    }

    private fun isAirplaneModeOn(): Boolean {
        return authenticationViewModel.isAirplaneModeOn()
    }

    private fun isHasInternet(): Boolean {
        return authenticationViewModel.isHasInternetConnection()
    }

    private fun loadMask() {
        authenticationViewModel.loadMask()
    }

    private fun setupMaskObserver() {
        authenticationViewModel.phoneMask.observe(viewLifecycleOwner) { mask ->
            val editText = binding.telephoneEditText
            val result = mask?.changeXtoNumber()
            editText.mask = result
            editText.hint = mask
        }
    }

    private fun setupEnterButtonListener() {

        binding.apply {
            enterAccountButton.setOnClickListener {
                if (isHasInternet() && !isAirplaneModeOn()) {
                    val phone = telephoneEditText.text.toString().filter { it.isDigit() }
                    val password = passwordInputText.text.toString()
                    loadLoginSuccessStatusFromServer(phone, password)
                } else {
                    makeToast(getString(R.string.check_internet))
                }
            }
        }
    }

    private fun setupLoginSuccessStatusObserver() {
        lifecycleScope.launchWhenCreated {
            authenticationViewModel.loginState.collect {
                when (it) {
                    is LoginState.Success -> {
                        Snackbar.make(binding.root, "Успешно", Snackbar.LENGTH_LONG).show()
                        navigateToMainFragment()
                    }
                    is LoginState.Error -> {
                        Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun loadLoginSuccessStatusFromServer(phone: String, password: String) {
        authenticationViewModel.login(phone, password)
    }

    private fun navigateToMainFragment() {
        val action =
            AuthenticationFragmentDirections
                .actionAuthenticationFragmentToDevExam()
        view?.findNavController()?.safeNavigate(action)
    }

    private fun initClearPhoneFieldButton() {
        binding.clearTextIcon.setOnClickListener {
            binding.telephoneEditText.text?.clear()
        }
    }
}