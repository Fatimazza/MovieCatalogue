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

    @Query("DELETE FROM fav_movie_table WHERE movieId = :key")
    fun deleteFavMovie(key: Long)

    @Query("DELETE FROM fav_tv_table WHERE tvId = :key")
    fun deleteFavTvShow(key: Long)

    @Query("DELETE FROM fav_movie_table")
    fun clearAllFavMovie()

    @Query("DELETE FROM fav_tv_table")
    fun clearAllFavTvShow()

    @Query("SELECT * FROM fav_movie_table ORDER BY favMovieId DESC")
    fun getAllMovies(): LiveData<List<FavoriteMovie>>

    @Query("SELECT * FROM fav_tv_table ORDER BY favTvId DESC")
    fun getAllTvShows(): LiveData<List<FavoriteTv>>

    //get a specific movie based on its key
    @Query("SELECT * from fav_movie_table WHERE movieId = :key")
    fun getFavMovie(key: Long): LiveData<FavoriteMovie>

    @Query("SELECT * from fav_tv_table WHERE favTvId = :key")
    fun getFavTvShow(key: Long): FavoriteTv?
}

