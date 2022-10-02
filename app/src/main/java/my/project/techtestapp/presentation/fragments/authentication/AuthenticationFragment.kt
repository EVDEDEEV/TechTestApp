package my.project.techtestapp.presentation.fragments.authentication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import my.project.techtestapp.R
import my.project.techtestapp.data.models.remote.AuthResponse
import my.project.techtestapp.databinding.FragmentAuthenticationBinding
import my.project.techtestapp.presentation.MainViewModel

@AndroidEntryPoint
class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {

    private val binding by viewBinding(FragmentAuthenticationBinding::bind)
    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val phone = binding.telephoneInputText.text.toString()
        val password = binding.passwordInputText.text.toString()

        binding.enterAccountButton.setOnClickListener {

            binding.enterAccountHeader.text = getRight(phone, password).toString()
        }
    }

    private fun getRight(phone: String, password: String) {
        mainViewModel.getAuth(phone, password)
    }
}