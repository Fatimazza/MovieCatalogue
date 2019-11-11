package io.github.fatimazza.moviecatalogue.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DailyReminderAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        private const val ID_DAILY = 100
        private const val ID_RELEASE = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        val type = intent.getIntExtra(EXTRA_TYPE, 0)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        when (type) {
            ID_DAILY -> {
            }
            ID_RELEASE -> {
            }
        }
    }

}
