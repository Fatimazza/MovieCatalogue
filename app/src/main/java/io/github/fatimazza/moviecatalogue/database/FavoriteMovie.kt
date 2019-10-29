package io.github.fatimazza.moviecatalogue.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_movie_table")
data class FavoriteMovie(

    @PrimaryKey(autoGenerate = true)
    var favMovieId: Long = 0L,

    @ColumnInfo(name = "movieId")
    var movieId: String = "",

    @ColumnInfo(name = "movieTitle")
    var movieTitle: String = "",

    @ColumnInfo(name = "movieOverview")
    var movieOverview: String = "",

    @ColumnInfo(name = "movieVoteAverage")
    var movieVoteAverage: String = "",

    @ColumnInfo(name = "moviePosterPath")
    var moviePosterPath: String = ""
)
