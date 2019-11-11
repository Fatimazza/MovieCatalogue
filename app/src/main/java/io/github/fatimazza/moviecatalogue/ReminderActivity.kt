package io.github.fatimazza.moviecatalogue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.fatimazza.moviecatalogue.receiver.DailyReminderAlarmReceiver
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        val alarmReceiver = DailyReminderAlarmReceiver()

        release_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

            } else {

            }
        }

        daily_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.startDailyAlarm(
                    this,
                    getString(R.string.reminder_daily_notif_title),
                    getString(R.string.reminder_daily_notif_desc)
                )
            } else {
                alarmReceiver.stopDailyAlarm()
            }
        }
    }
}
