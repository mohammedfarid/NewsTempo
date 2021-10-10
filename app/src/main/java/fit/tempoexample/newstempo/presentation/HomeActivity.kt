package fit.tempoexample.newstempo.presentation

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import fit.tempoexample.newstempo.R
import fit.tempoexample.newstempo.databinding.ActivityHomeBinding
import fit.tempoexample.newstempo.presentation.bases.BaseActivity

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private var binding: ActivityHomeBinding? = null
    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
        navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        binding?.toolbar?.setNavigationOnClickListener {
            navController?.popBackStack()
        }
    }

    fun hideBack() {
        binding?.toolbar?.navigationIcon = null
    }

    fun showBack() {
        binding?.toolbar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
    }

    fun onBackPress(no: Int? = 1){
        repeat(no ?: 1) {
            navController?.popBackStack()
        }
    }
}