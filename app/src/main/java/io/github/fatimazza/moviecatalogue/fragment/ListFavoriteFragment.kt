package io.github.fatimazza.moviecatalogue.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.adapter.FavoritesPagerAdapter
import kotlinx.android.synthetic.main.fragment_list_favorite.*


class ListFavoriteFragment : Fragment() {

    private val favTabLayout: TabLayout
        get() = tab_favorite

    private val favViewPager: ViewPager
        get() = vp_favorite

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_favorite, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPageAdapter()
    }

    private fun initPageAdapter() {
        favViewPager.adapter = FavoritesPagerAdapter(childFragmentManager, requireContext())
        favTabLayout.setupWithViewPager(favViewPager)
    }
}
