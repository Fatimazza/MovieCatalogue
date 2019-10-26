package io.github.fatimazza.moviecatalogue.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_movie_table")
data class FavoriteMovie(

    @PrimaryKey(autoGenerate = true)
    var favMovieId: Long = 0L,

    @ColumnInfo(name = "movieId")
    val movieId: String = "",

    @ColumnInfo(name = "movieTitle")
    val movieTitle: String = "",

    @ColumnInfo(name = "movieOverview")
    val movieOverview: String = "",

    @ColumnInfo(name = "movieVoteAverage")
    val movieVoteAverage: String = ""
)
