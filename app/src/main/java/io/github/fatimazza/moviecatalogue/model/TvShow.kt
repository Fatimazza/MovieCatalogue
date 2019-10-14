package io.github.fatimazza.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow(
    var title: String = "",
    var poster: Int = 0,
    var description: String = "",
    var releaseDate: String = "",
    var runtime: String = ""
): Parcelable
