package io.github.fatimazza.moviecatalogue

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import io.github.fatimazza.moviecatalogue.databinding.ActivityReminderBinding
import io.github.fatimazza.moviecatalogue.model.SPReminderModel
import io.github.fatimazza.moviecatalogue.receiver.DailyReminderAlarmReceiver
import io.github.fatimazza.moviecatalogue.utils.ReminderPreference

class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding

    private val releaseSwitch : Switch
        get() = binding.releaseSwitch

    private val dailySwitch : Switch
        get() = binding.dailySwitch

    private lateinit var reminderPreference: ReminderPreference
    private lateinit var reminderModel: SPReminderModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showExistingPreference()

        val alarmReceiver = DailyReminderAlarmReceiver()

        releaseSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.startReleaseAlarm(
                    this,
                    getString(R.string.reminder_daily_notif_title),
                    getString(R.string.reminder_daily_notif_desc)
                )
            } else {
                alarmReceiver.stopReleaseAlarm()
            }
            saveReminder(dailySwitch.isChecked, releaseSwitch.isChecked)
        }

        dailySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.startDailyAlarm(
                    this,
                    getString(R.string.reminder_daily_notif_title),
                    getString(R.string.reminder_daily_notif_desc)
                )
            } else {
                alarmReceiver.stopDailyAlarm()
            }
            saveReminder(dailySwitch.isChecked, releaseSwitch.isChecked)
        }
    }

    private fun showExistingPreference() {
        reminderPreference = ReminderPreference(this)
        reminderModel = reminderPreference.getReminder()
        dailySwitch.isChecked = reminderModel.isdailyReminderActive
        releaseSwitch.isChecked = reminderModel.isreleaseReminderActive
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
