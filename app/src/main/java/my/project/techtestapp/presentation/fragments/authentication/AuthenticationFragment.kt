package my.project.techtestapp.presentation.fragments.authentication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import my.project.techtestapp.R
import my.project.techtestapp.databinding.FragmentAuthenticationBinding
import my.project.techtestapp.presentation.MainActivity
import my.project.techtestapp.presentation.MainViewModel

@AndroidEntryPoint
class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {

    private val binding by viewBinding(FragmentAuthenticationBinding::bind)
    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClearPhoneFieldButton()
        login()
    }

    private fun login() {
        binding.apply {
            enterAccountButton.setOnClickListener {
                val phone = telephoneInputText.text.toString()
                val password = passwordInputText.editText?.text.toString()
                getRight(phone, password)
                mainViewModel.authResponse.observe(viewLifecycleOwner) { success ->
                    if (success == true) {
                        val action =
                            AuthenticationFragmentDirections.actionAuthenticationFragmentToDevExam()
                        view?.findNavController()?.navigate(action)
                    }
//                    } else {
//                        Toast.makeText(this@AuthenticationFragment.requireActivity(),
//                            "Неверное имя или пустые поля",
//                            Toast.LENGTH_SHORT).show()
//                    }
                }
            }
        }
    }

    private fun initClearPhoneFieldButton() {
        binding.clearTextIcon.setOnClickListener {
            binding.telephoneInputText.text.clear()
        }
    }

    private fun getRight(phone: String, password: String) {
        mainViewModel.getAuth(phone, password)
    }
}