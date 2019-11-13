package io.github.fatimazza.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SPReminderModel(
    var isdailyReminderActive: Boolean = false,
    var isreleaseReminderActive: Boolean = false
) : Parcelable
