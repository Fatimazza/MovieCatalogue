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
import io.github.fatimazza.moviecatalogue.BuildConfig
import io.github.fatimazza.moviecatalogue.MainActivity
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.model.BaseResponse
import io.github.fatimazza.moviecatalogue.model.MovieResponse
import io.github.fatimazza.moviecatalogue.model.NotificationItem
import io.github.fatimazza.moviecatalogue.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DailyReminderAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_NOTIF_TITLE = "title"
        const val EXTRA_NOTIF_MESSAGE = "message"
        const val EXTRA_NOTIF_TYPE = "type"

        private const val ID_DAILY = 100
        private const val ID_RELEASE = 101

        private const val MAX_RELEASE_NOTIFICATION = 2
        var idReleaseNotification = 0
        val stackNotif = ArrayList<NotificationItem>()
    }

    var dailyAlarm: AlarmManager? = null
    var releaseAlarm: AlarmManager? = null

    var dailyPendingIntent: PendingIntent? = null
    var releasePendingIntent: PendingIntent? = null

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
                getMovieReleasedToday(context)
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

    fun startReleaseAlarm(context: Context, title: String, message: String) {
        releaseAlarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminderAlarmReceiver::class.java)
        intent.putExtra(EXTRA_NOTIF_TITLE, title)
        intent.putExtra(EXTRA_NOTIF_MESSAGE, message)
        intent.putExtra(EXTRA_NOTIF_TYPE, ID_RELEASE)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.SECOND, 0)

        releasePendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0)
        releaseAlarm?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            releasePendingIntent
        )
    }

    fun stopReleaseAlarm() {
        releaseAlarm?.cancel(releasePendingIntent)
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
        } else {
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
        }
    }

    private fun showReleaseNotification(context: Context) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "Movie Channel"

        val GROUP_KEY_RELEASES = "group_key_releases"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
        )

        val title = stackNotif[idReleaseNotification].releasedMovieTitle
        val message = stackNotif[idReleaseNotification].releasedMovieDesc

        if (idReleaseNotification < MAX_RELEASE_NOTIFICATION) {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    val notificationChannel = NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    notificationChannel.lightColor =
                        ContextCompat.getColor(context, R.color.colorPrimary)

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
                            .setGroup(GROUP_KEY_RELEASES)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setVibrate(longArrayOf(100, 0, 100, 100, 100, 100, 0, 100, 0, 100))
                            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                            .build()
                    )
                }
                else -> {
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
                            .setGroup(GROUP_KEY_RELEASES)
                            .setOngoing(false)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setVibrate(longArrayOf(100, 0, 100, 100, 100, 100, 0, 100, 0, 100))
                            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                            .build()
                    )

                }
            }
        } else {
            val inboxTitle = "$idReleaseNotification new releases"
            val inboxMessage = "Movie Catalogue"

            val inboxStyle = NotificationCompat.InboxStyle()
                .addLine("New Movie Release: " + stackNotif[idReleaseNotification].releasedMovieTitle)
                .addLine("New Movie Release: " + stackNotif[idReleaseNotification - 1].releasedMovieTitle)
                .setBigContentTitle("$idReleaseNotification new releases")
                .setSummaryText(inboxMessage)

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    val notificationChannel = NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    notificationChannel.lightColor =
                        ContextCompat.getColor(context, R.color.colorPrimary)

                    notificationManager.createNotificationChannel(notificationChannel)
                    notificationManager.notify(
                        System.currentTimeMillis().toInt(),
                        NotificationCompat.Builder(context, CHANNEL_ID)
                            .setChannelId(CHANNEL_ID)
                            .setContentTitle(inboxTitle)
                            .setContentText(inboxMessage)
                            .setSmallIcon(R.drawable.ic_movie_notification)
                            .setLargeIcon(
                                BitmapFactory.decodeResource(
                                    context.resources,
                                    R.drawable.ic_movie_notification
                                )
                            )
                            .setContentIntent(pendingIntent)
                            .setGroup(GROUP_KEY_RELEASES)
                            .setGroupSummary(true)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setVibrate(longArrayOf(100, 0, 100, 100, 100, 100, 0, 100, 0, 100))
                            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                            .setStyle(inboxStyle)
                            .build()
                    )
                }
                else -> {
                    notificationManager.notify(
                        System.currentTimeMillis().toInt(),
                        NotificationCompat.Builder(context, "1")
                            .setContentTitle(inboxTitle)
                            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                            .setContentText(inboxMessage)
                            .setContentIntent(pendingIntent)
                            .setGroup(GROUP_KEY_RELEASES)
                            .setGroupSummary(true)
                            .setOngoing(false)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVibrate(longArrayOf(100, 0, 100, 100, 100, 100, 0, 100, 0, 100))
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setStyle(inboxStyle)
                            .build()
                    )
                }
            }
        }
    }

    private fun getMovieReleasedToday(context: Context) {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val now = df.format(Date())
        NetworkConfig.api().checkMovieReleasedToday(BuildConfig.API_KEY, now, now)
            .enqueue(object : Callback<BaseResponse<MovieResponse>> {
                override fun onFailure(call: Call<BaseResponse<MovieResponse>>, t: Throwable) {
                    showAlarmNotification(
                        context,
                        context.getString(R.string.reminder_release_nodata),
                        t.message ?: ""
                    )
                }

                override fun onResponse(
                    call: Call<BaseResponse<MovieResponse>>,
                    response: Response<BaseResponse<MovieResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.results?.let {
                            if (it.isNotEmpty()) {
                                for (i in 0 until it.size) {
                                    idReleaseNotification = i
                                    stackNotif.add(
                                        NotificationItem(
                                            idReleaseNotification,
                                            it[i].title,
                                            it[i].overview
                                        )
                                    )
                                }
                                showReleaseNotification(context)
                            } else {
                                showAlarmNotification(
                                    context,
                                    context.getString(R.string.reminder_release_nodata),
                                    context.getString(R.string.reminder_release_nodata_desc)
                                )
                            }
                        } ?: run {
                            showAlarmNotification(
                                context,
                                context.getString(R.string.reminder_release_nodata),
                                context.getString(R.string.reminder_release_nodata_desc)
                            )
                        }
                    }
                }
            })
    }
}
