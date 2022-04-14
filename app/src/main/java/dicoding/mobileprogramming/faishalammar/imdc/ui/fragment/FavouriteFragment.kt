package dicoding.mobileprogramming.faishalammar.imdc.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dicoding.mobileprogramming.faishalammar.imdc.R
import dicoding.mobileprogramming.faishalammar.imdc.databinding.FragmentFavouriteBinding
import dicoding.mobileprogramming.faishalammar.imdc.ui.adapter.SectionsPagerAdapter


class FavouriteFragment : Fragment() {

    private lateinit var fragmentFavouriteBinding: FragmentFavouriteBinding
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movies_page, R.string.tv_series_page)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentFavouriteBinding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)

        val activityContext : Context = activity?.applicationContext!!

        /*
        ----- Tab Layout ---
        */

        val viewPager: ViewPager2 = fragmentFavouriteBinding.viewPager2
        val tabLayout: TabLayout = fragmentFavouriteBinding.tabs

        viewPager.adapter = SectionsPagerAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabText = resources.getString(TAB_TITLES[position])
            tab.text = tabText
        }.attach()


        // Inflate the layout for this fragment
        return fragmentFavouriteBinding.root
    }


}