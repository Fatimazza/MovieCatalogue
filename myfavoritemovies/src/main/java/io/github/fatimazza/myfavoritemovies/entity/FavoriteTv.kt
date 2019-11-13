package io.github.fatimazza.myfavoritemovies.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteTv(

    var tvId: String = "",

    var tvTitle: String = "",

    var tvOverview: String = "",

    var tvVoteAverage: String = "",

    var tvReleaseDate: String = "",

    var tvPosterPath: String = "",

    var tvLang: String = ""

) : Parcelable
