package io.github.fatimazza.moviecatalogue.utils

import android.content.Context
import io.github.fatimazza.moviecatalogue.model.SPReminderModel

internal class ReminderPreference(context: Context) {

    companion object {
        private const val PREFS_REMINDER = "reminder_prefs"
        private const val HAS_DAILY_REMINDER = "hasDailyReminder"
        private const val HAS_RELEASE_REMINDER = "hasReleaseReminder"
    }

    private val preferences = context.getSharedPreferences(PREFS_REMINDER, Context.MODE_PRIVATE)

    fun setReminder(value: SPReminderModel) {
        val editor = preferences.edit()
        editor.putBoolean(HAS_DAILY_REMINDER, value.isdailyReminderActive)
        editor.putBoolean(HAS_RELEASE_REMINDER, value.isreleaseReminderActive)
        editor.apply()
    }

    fun getReminder(): SPReminderModel {
        val model = SPReminderModel()
        model.isdailyReminderActive = preferences.getBoolean(HAS_DAILY_REMINDER, false)
        model.isreleaseReminderActive = preferences.getBoolean(HAS_RELEASE_REMINDER, false)
        return model
    }

}
