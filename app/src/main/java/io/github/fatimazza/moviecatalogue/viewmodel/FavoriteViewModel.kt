package io.github.fatimazza.moviecatalogue.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.github.fatimazza.moviecatalogue.database.DatabaseRepository
import io.github.fatimazza.moviecatalogue.database.FavoriteDatabase
import io.github.fatimazza.moviecatalogue.database.FavoriteMovie
import io.github.fatimazza.moviecatalogue.database.FavoriteTv
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DatabaseRepository
    private val allFavMovies: LiveData<List<FavoriteMovie>>
    private val allFavTvShows: LiveData<List<FavoriteTv>>

    init {
        val favDatabaseDao = FavoriteDatabase.getInstance(application).favoriteDatabaseDao
        repository = DatabaseRepository(favDatabaseDao)
        allFavMovies = repository.allFavMovies
        allFavTvShows = repository.allFavTvShows
    }

    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>> {
        return allFavMovies
    }

    fun getMovie(id: Long): LiveData<FavoriteMovie> {
        return repository.getMovie(id)
    }

    fun insertMovie(movie: FavoriteMovie) {
        GlobalScope.launch {
            repository.insertMovie(movie)
            Log.d("Izza", "Movie ${movie.movieTitle} ${movie.movieId} - - INSERTED")
        }
    }

    fun deleteMovie(movieId: Long) {
        GlobalScope.launch {
            repository.deleteMovie(movieId)
            Log.d("Izza", "Movie $movieId - - DELETED")
        }
    }

    fun getAllFavoriteTvShows(): LiveData<List<FavoriteTv>> {
        return allFavTvShows
    }

    fun getTvShow(id: Long): LiveData<FavoriteTv> {
        return repository.getTvShow(id)
    }

    fun insertTvShow(tv: FavoriteTv) {
        GlobalScope.launch {
            repository.insertTvShow(tv)
            Log.d("Izza", "Tv ${tv.tvTitle} ${tv.tvId} - - INSERTED")
        }
    }

    fun deleteTvShow(tvId: Long) {
        GlobalScope.launch {
            repository.deleteTvShow(tvId)
            Log.d("Izza", "Tv $tvId - - DELETED")
        }
    }
}
