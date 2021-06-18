package io.github.fatimazza.myfavoritemovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import io.github.fatimazza.myfavoritemovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val favTabLayout: TabLayout
        get() = binding.tabFavorite

    private val favViewPager: ViewPager
        get() = binding.vpFavorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPageAdapter()
    }

    private fun initPageAdapter() {
        favViewPager.adapter = FavoritesPagerAdapter(supportFragmentManager, this)
        favTabLayout.setupWithViewPager(favViewPager)
    }
}
