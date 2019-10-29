package io.github.fatimazza.moviecatalogue.database

import androidx.lifecycle.LiveData

class DatabaseRepository(private val favMovieDao: FavoriteDatabaseDao) {

    val allFavMovies: LiveData<List<FavoriteMovie>> = favMovieDao.getAllMovies()

    suspend fun insertMovie(movie: FavoriteMovie) {
        favMovieDao.insertMovie(movie)
    }

    suspend fun deleteMovie(id: Long) {
        favMovieDao.deleteFavMovie(id)
    }
}
