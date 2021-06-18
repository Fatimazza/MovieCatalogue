package io.github.fatimazza.moviecatalogue.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import io.github.fatimazza.moviecatalogue.adapter.FavoritesPagerAdapter
import io.github.fatimazza.moviecatalogue.databinding.FragmentListFavoriteBinding


class ListFavoriteFragment : Fragment() {

    private var _binding: FragmentListFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favTabLayout: TabLayout
        get() = binding.tabFavorite

    private val favViewPager: ViewPager
        get() = binding.vpFavorite

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPageAdapter()
    }

    private fun initPageAdapter() {
        favViewPager.adapter = FavoritesPagerAdapter(childFragmentManager, requireContext())
        favTabLayout.setupWithViewPager(favViewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
