package io.github.fatimazza.moviecatalogue.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDatabaseDao {

    @Insert
    fun insertMovie(movie: FavoriteMovie)

    @Insert
    fun insetTvShow(tv: FavoriteTv)

    @Update
    fun updateMovie(movie: FavoriteMovie)

    @Update
    fun updateTvShow(tv: FavoriteTv)

    @Query("DELETE FROM fav_movie_table")
    fun clearFavMovie()

    @Query("DELETE FROM fav_tv_table")
    fun clearFavTvShow()

    @Query("SELECT * FROM fav_movie_table ORDER BY favMovieId DESC")
    fun getAllMovies(): LiveData<ArrayList<FavoriteMovie>>

    @Query("SELECT * FROM fav_tv_table ORDER BY favTvId DESC")
    fun getAllTvShows(): LiveData<ArrayList<FavoriteTv>>

    //get a specific movie based on its key
    @Query("SELECT * from fav_movie_table WHERE favMovieId = :key")
    fun getFavMovie(key: Long): FavoriteMovie?

    @Query("SELECT * from fav_tv_table WHERE favTvId = :key")
    fun getFavTvShow(key: Long): FavoriteTv?
}

