package io.github.fatimazza.moviecatalogue.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import io.github.fatimazza.moviecatalogue.MainActivity
import io.github.fatimazza.moviecatalogue.R
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
                showAlarmNotification(context, title ?: "", message ?: "")
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
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 7)
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

    private fun showAlarmNotification(context: Context, title: String, message: String) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "Movie Channel"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.lightColor = ContextCompat.getColor(context, R.color.colorPrimary)

            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(
                System.currentTimeMillis().toInt(),
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setChannelId(CHANNEL_ID)
                    .setContentTitle(title)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_movie_notification)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.ic_movie_notification
                        )
                    )
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setVibrate(longArrayOf(100, 0, 100, 100, 100, 100, 0, 100, 0, 100))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .build()
            )

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationManager.notify(
                System.currentTimeMillis().toInt(),
                NotificationCompat.Builder(context, System.currentTimeMillis().toString())
                    .setContentTitle(title)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_movie_notification)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.ic_movie_notification
                        )
                    )
                    .setContentIntent(pendingIntent)
                    .setOngoing(false)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setVibrate(longArrayOf(100, 0, 100, 100, 100, 100, 0, 100, 0, 100))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .build()
            )
        } else {
            notificationManager.notify(
                System.currentTimeMillis().toInt(), NotificationCompat.Builder(context, "1")
                    .setContentTitle(title)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setOngoing(false)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVibrate(longArrayOf(100, 0, 100, 100, 100, 100, 0, 100, 0, 100))
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .build()
            )
        }
    }

}
