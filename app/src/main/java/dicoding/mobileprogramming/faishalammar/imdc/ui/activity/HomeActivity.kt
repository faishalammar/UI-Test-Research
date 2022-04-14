package dicoding.mobileprogramming.faishalammar.imdc.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dicoding.mobileprogramming.faishalammar.imdc.R
import dicoding.mobileprogramming.faishalammar.imdc.databinding.ActivityHomeBinding
import dicoding.mobileprogramming.faishalammar.imdc.ui.adapter.SectionsPagerAdapter
import dicoding.mobileprogramming.faishalammar.imdc.ui.fragment.FavouriteFragment
import dicoding.mobileprogramming.faishalammar.imdc.ui.fragment.MovieListFragment
import dicoding.mobileprogramming.faishalammar.imdc.ui.fragment.TvSeriesListFragment
import androidx.test.espresso.idling.CountingIdlingResource




class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private var fragmentManager : FragmentManager = supportFragmentManager
    var idlingResource = CountingIdlingResource("Loader")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        _binding = ActivityHomeBinding.inflate(layoutInflater)

        // removing toolbar elevation
        supportActionBar?.elevation = 0F;


        val bottomNavigationView = binding.bottomNavigation

        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, MovieListFragment(), MovieListFragment::class.java.simpleName)
            .addToBackStack("fav users")
            .commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, MovieListFragment(), MovieListFragment::class.java.simpleName)
                            .addToBackStack("list movie")
                            .commit()
                    // Respond to navigation item 1 click
                    true
                }
                R.id.page_2 -> {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, TvSeriesListFragment(), TvSeriesListFragment::class.java.simpleName)
                            .addToBackStack("list series")
                            .commit()
                    true
                }
                R.id.page_3 -> {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, FavouriteFragment(), FavouriteFragment::class.java.simpleName)
                            .addToBackStack("list favourite")
                            .commit()
                    true
                }
                else -> false
            }
        }


        setContentView(binding.root)

    }

}