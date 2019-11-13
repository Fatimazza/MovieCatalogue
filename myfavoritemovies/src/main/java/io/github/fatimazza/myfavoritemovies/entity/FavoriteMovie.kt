package io.github.fatimazza.myfavoritemovies.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteMovie(

    var favMovieId: Long = 0L,

    var movieId: String = "",

    var movieTitle: String = "",

    var movieOverview: String = "",

    var movieVoteAverage: String = "",

    var movieReleaseDate: String = "",

    var moviePosterPath: String = "",

    var movieLang: String = ""

) : Parcelable
