package io.github.fatimazza.moviecatalogue

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
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

    private lateinit var localeChangedReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createLocaleChangedReceiver()
        setupBottomNavigation(savedInstanceState)
    }

    private fun createLocaleChangedReceiver() {
        localeChangedReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

            }
        }
        val localeChangedIntentFilter = IntentFilter(Intent.ACTION_LOCALE_CHANGED)
        registerReceiver(localeChangedReceiver, localeChangedIntentFilter)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            val intentSettings = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intentSettings)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(localeChangedReceiver)
    }
}
