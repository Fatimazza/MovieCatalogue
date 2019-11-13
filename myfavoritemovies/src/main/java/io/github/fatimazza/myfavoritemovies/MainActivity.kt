package io.github.fatimazza.myfavoritemovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val favTabLayout: TabLayout
        get() = tab_favorite

    private val favViewPager: ViewPager
        get() = vp_favorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPageAdapter()
    }

    private fun initPageAdapter() {
        favViewPager.adapter = FavoritesPagerAdapter(supportFragmentManager, this)
        favTabLayout.setupWithViewPager(favViewPager)
    }
}
