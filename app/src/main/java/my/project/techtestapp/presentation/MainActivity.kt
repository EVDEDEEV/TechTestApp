package my.project.techtestapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import my.project.techtestapp.R
import my.project.techtestapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomAppBarInAuthenticationFragment()
        navController = findNavController(R.id.nav_host_fragment)
//        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun hideBottomAppBarInAuthenticationFragment() {
        binding.bottomNavigationView.isGone = true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}

