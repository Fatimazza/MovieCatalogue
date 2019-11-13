package io.github.fatimazza.moviecatalogue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.fatimazza.moviecatalogue.model.SPReminderModel
import io.github.fatimazza.moviecatalogue.receiver.DailyReminderAlarmReceiver
import io.github.fatimazza.moviecatalogue.utils.ReminderPreference
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : AppCompatActivity() {

    private lateinit var reminderPreference: ReminderPreference
    private lateinit var reminderModel: SPReminderModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        showExistingPreference()

        val alarmReceiver = DailyReminderAlarmReceiver()

        release_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.startReleaseAlarm(
                    this,
                    getString(R.string.reminder_daily_notif_title),
                    getString(R.string.reminder_daily_notif_desc)
                )
            } else {
                alarmReceiver.stopReleaseAlarm()
            }
            saveReminder(daily_switch.isChecked, release_switch.isChecked)
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
            saveReminder(daily_switch.isChecked, release_switch.isChecked)
        }
    }

    private fun showExistingPreference() {
        reminderPreference = ReminderPreference(this)
        reminderModel = reminderPreference.getReminder()
        daily_switch.isChecked = reminderModel.isdailyReminderActive
        release_switch.isChecked = reminderModel.isreleaseReminderActive
    }

    private fun saveReminder(
        hasDailyReminder: Boolean, hasReleaseReminder: Boolean
    ) {
        val reminderPreference = ReminderPreference(this)
        reminderModel.isdailyReminderActive = hasDailyReminder
        reminderModel.isreleaseReminderActive = hasReleaseReminder
        reminderPreference.setReminder(reminderModel)
    }
}
