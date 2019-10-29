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
    val allFavMovies: LiveData<List<FavoriteMovie>>
    val allFavTvShows: LiveData<List<FavoriteTv>>

    init {
        val favDatabaseDao = FavoriteDatabase.getInstance(application).favoriteDatabaseDao
        repository = DatabaseRepository(favDatabaseDao)
        allFavMovies = repository.allFavMovies
        allFavTvShows = repository.allFavTvShows
    }

    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>> {
        return allFavMovies
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
            Log.d("Izza", "Movie ${movieId} - - DELETED")
        }
    }

    fun getAllFavoriteTvShows(): LiveData<List<FavoriteTv>> {
        return allFavTvShows
    }
}
