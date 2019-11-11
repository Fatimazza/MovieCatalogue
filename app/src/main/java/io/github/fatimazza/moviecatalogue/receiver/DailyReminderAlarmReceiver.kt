package io.github.fatimazza.moviecatalogue.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class DailyReminderAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_NOTIF_TITLE = "title"
        const val EXTRA_NOTIF_MESSAGE = "message"
        const val EXTRA_NOTIF_TYPE = "type"

        private const val ID_DAILY = 100
        private const val ID_RELEASE = 101
    }

    var dailyAlarm: AlarmManager? = null

    var dailyPendingIntent: PendingIntent? = null

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        val type = intent.getIntExtra(EXTRA_NOTIF_TYPE, 0)
        val title = intent.getStringExtra(EXTRA_NOTIF_TITLE)
        val message = intent.getStringExtra(EXTRA_NOTIF_MESSAGE)

        when (type) {
            ID_DAILY -> {
            }
            ID_RELEASE -> {
            }
        }
    }

    fun startDailyAlarm(context: Context, title: String, message: String) {
        dailyAlarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminderAlarmReceiver::class.java)
        intent.putExtra(EXTRA_NOTIF_TITLE, title)
        intent.putExtra(EXTRA_NOTIF_MESSAGE, message)
        intent.putExtra(EXTRA_NOTIF_TYPE, ID_DAILY)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        dailyPendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0)
        dailyAlarm?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            dailyPendingIntent
        )
    }

    fun stopDailyAlarm() {
        dailyAlarm?.cancel(dailyPendingIntent)
    }

}
