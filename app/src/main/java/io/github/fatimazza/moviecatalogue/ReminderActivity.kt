package io.github.fatimazza.moviecatalogue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        release_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

            } else {

            }
        }

        daily_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

            } else {

            }
        }
    }
}
