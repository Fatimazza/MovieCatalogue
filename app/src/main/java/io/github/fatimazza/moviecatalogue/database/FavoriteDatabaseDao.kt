package io.github.fatimazza.moviecatalogue.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDatabaseDao {

    @Insert
    fun insertMovie(movie: FavoriteMovie)

    @Insert
    fun insertMovieCursor(movie: FavoriteMovie): Long

    @Insert
    fun insetTvShow(tv: FavoriteTv)

    @Insert
    fun insertTvShowCursor(tv: FavoriteTv): Long

    @Query("DELETE FROM fav_movie_table WHERE movieId = :key")
    fun deleteFavMovie(key: Long)

    @Query("DELETE FROM fav_tv_table WHERE tvId = :key")
    fun deleteFavTvShow(key: Long)

    @Query("SELECT * FROM fav_movie_table ORDER BY favMovieId DESC")
    fun getAllMovies(): LiveData<List<FavoriteMovie>>

    @Query("SELECT * FROM fav_movie_table ORDER BY favMovieId DESC")
    fun getAllMoviesForWidget(): List<FavoriteMovie>

    @Query("SELECT * FROM fav_movie_table ORDER BY favMovieId DESC")
    fun getAllMoviesCursor(): Cursor

    @Query("SELECT * FROM fav_tv_table ORDER BY favTvId DESC")
    fun getAllTvShows(): LiveData<List<FavoriteTv>>

    @Query("SELECT * FROM fav_tv_table ORDER BY favTvId DESC")
    fun getAllTvShowsCursor(): Cursor

    //get a specific movie based on its key
    @Query("SELECT * from fav_movie_table WHERE movieId = :key")
    fun getFavMovie(key: Long): LiveData<FavoriteMovie>

    @Query("SELECT * from fav_movie_table WHERE movieId = :key")
    fun getFavMovieCursor(key: Long): Cursor

    @Query("SELECT * from fav_tv_table WHERE tvId = :key")
    fun getFavTvShow(key: Long): LiveData<FavoriteTv>

    @Query("SELECT * from fav_tv_table WHERE tvId = :key")
    fun getFavTvShowCursor(key: Long): Cursor
}

