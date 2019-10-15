package io.github.fatimazza.moviecatalogue

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.fatimazza.moviecatalogue.fragment.ListMovieFragment
import io.github.fatimazza.moviecatalogue.fragment.ListTelevisionFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val bottomNavigation: BottomNavigationView
        get() = nav_view_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation(savedInstanceState)
    }

    private fun setupBottomNavigation(savedInstanceState: Bundle?) {
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.navigation_movie
        }
    }

    private val onNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val fragment: Fragment
                when (item.itemId) {
                    R.id.navigation_movie -> {
                        fragment = ListMovieFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, fragment, fragment.javaClass.simpleName)
                            .commit()
                        return true
                    }
                    R.id.navigation_tvshow -> {
                        fragment = ListTelevisionFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, fragment, fragment.javaClass.simpleName)
                            .commit()
                        return true
                    }
                }
                return false
            }
        }
}
