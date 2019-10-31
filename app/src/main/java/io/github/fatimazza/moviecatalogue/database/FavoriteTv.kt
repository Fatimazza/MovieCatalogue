package io.github.fatimazza.moviecatalogue.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_tv_table")
data class FavoriteTv(

    @PrimaryKey(autoGenerate = true)
    var favTvId: Long = 0L,

    @ColumnInfo(name = "tvId")
    var tvId: String = "",

    @ColumnInfo(name = "tvTitle")
    var tvTitle: String = "",

    @ColumnInfo(name = "tvOverview")
    var tvOverview: String = "",

    @ColumnInfo(name = "tvVoteAverage")
    var tvVoteAverage: String = "",

    @ColumnInfo(name = "tvReleaseDate")
    var tvReleaseDate: String = "",

    @ColumnInfo(name = "tvPosterPath")
    var tvPosterPath: String = "",

    @ColumnInfo(name = "tvLang")
    var tvLang: String = ""
)
