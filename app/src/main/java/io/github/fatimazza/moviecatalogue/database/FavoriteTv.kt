package io.github.fatimazza.moviecatalogue.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_tv_table")
data class FavoriteTv(

    @PrimaryKey(autoGenerate = true)
    var favTvId: Long = 0L,

    @ColumnInfo(name = "tvId")
    val tvId: String = "",

    @ColumnInfo(name = "tvTitle")
    val tvTitle: String = "",

    @ColumnInfo(name = "tvOverview")
    val tvOverview: String = "",

    @ColumnInfo(name = "tvVoteAverage")
    val tvVoteAverage: String = ""
)
