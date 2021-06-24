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
import io.github.fatimazza.moviecatalogue.databinding.ActivityMainBinding
import io.github.fatimazza.moviecatalogue.fragment.ListFavoriteFragment
import io.github.fatimazza.moviecatalogue.fragment.ListMovieFragment
import io.github.fatimazza.moviecatalogue.fragment.ListTelevisionFragment
import io.github.fatimazza.moviecatalogue.receiver.DailyReminderAlarmReceiver

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val bottomNavigation: BottomNavigationView
        get() = binding.navViewMain

    private lateinit var localeChangedReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createLocaleChangedReceiver()
        setupBottomNavigation(savedInstanceState)
    }

    private fun createLocaleChangedReceiver() {
        localeChangedReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                recreate()
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
                when (item.itemId) {
                    R.id.navigation_movie -> {
                        loadFragment(ListMovieFragment())
                        return true
                    }
                    R.id.navigation_favorite -> {
                        loadFragment(ListFavoriteFragment())
                        return true
                    }
                    R.id.navigation_tvshow -> {
                        loadFragment(ListTelevisionFragment())
                        return true
                    }
                }
                return false
            }
        }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, fragment, fragment::class.java.simpleName)
            .commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        DailyReminderAlarmReceiver.stackNotif.clear()
        DailyReminderAlarmReceiver.idReleaseNotification = 0
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val intentSettings = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intentSettings)
            }
            R.id.action_reminder -> {
                val intentReminder = Intent(this, ReminderActivity::class.java)
                startActivity(intentReminder)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(localeChangedReceiver)
    }
}
