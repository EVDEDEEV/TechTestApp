package my.project.techtestapp.presentation.fragments.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import my.project.techtestapp.R
import my.project.techtestapp.databinding.FragmentAuthenticationBinding

class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {

    private val binding by viewBinding(FragmentAuthenticationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}