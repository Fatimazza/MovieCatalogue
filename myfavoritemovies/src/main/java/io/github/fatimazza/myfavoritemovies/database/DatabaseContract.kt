package io.github.fatimazza.myfavoritemovies.database

import android.net.Uri
import io.github.fatimazza.myfavoritemovies.BuildConfig

object DatabaseContract {

    private const val AUTHORITY = BuildConfig.PROVIDER_NAME
    private const val DATABASE = "favorite_movie_database"

    private const val FAV_MOVIE_TABLE = "fav_movie_table"
    private const val FAV_TELEVISION_TABLE = "fav_tv_table"

    val MOVIE_PROJECTION_MAP =
        arrayOf(
            "movieId",
            "movieTitle",
            "movieOverview",
            "movieVoteAverage",
            "movieReleaseDate",
            "moviePosterPath",
            "movieLang"
        )
    val TV_PROJECTION_MAP =
        arrayOf(
            "tvId",
            "tvTitle",
            "tvOverview",
            "tvVoteAverage",
            "tvReleaseDate",
            "tvPosterPath",
            "tvLang"
        )

    val MOVIE_CONTENT_URI: Uri =
        Uri.parse("content://$AUTHORITY/$DATABASE/$FAV_MOVIE_TABLE")
    val TV_CONTENT_URI: Uri =
        Uri.parse("content://$AUTHORITY/$DATABASE/$FAV_TELEVISION_TABLE")
}
