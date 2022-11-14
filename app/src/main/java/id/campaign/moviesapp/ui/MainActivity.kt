package id.campaign.moviesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import id.campaign.moviesapp.R
import id.campaign.moviesapp.databinding.ActivityMainBinding


// Mengatur tata letak, fungsionalitas dari main activity
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding UI
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // add default fragment
        selectedFragment = HomeFragment()
        changeFragment(selectedFragment, "HOME")

        // add event listener
        binding.bottomNavigation.setOnItemSelectedListener {
            var target = ""
            when(it.itemId){
                R.id.homeMenu -> {selectedFragment = HomeFragment()
                target = "HOME"}
                R.id.searchMenu -> {
                    selectedFragment = SearchFragment()
                    target = "SEARCH"
                }
                R.id.favoriteMenu -> {
                    selectedFragment = FavoriteFragment()
                    target = "FAVORITE"
                }
                R.id.profileMenu -> {
                    selectedFragment = ProfileFragment()
                    target = "PROFILE"
                }
            }
            changeFragment(selectedFragment, target)
            true
        }
    }

    // change fragment function
    private fun changeFragment(selectedFragment: Fragment, TAG : String) {
        supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, selectedFragment, TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragment = supportFragmentManager.findFragmentByTag("HOME")
        if(fragment!!.isRemoving){
            finish()
        }
    }
}