package io.github.fatimazza.moviecatalogue.database

import androidx.lifecycle.LiveData

class DatabaseRepository(private val favDatabaseDao: FavoriteDatabaseDao) {

    val allFavMovies: LiveData<List<FavoriteMovie>> = favDatabaseDao.getAllMovies()

    suspend fun insertMovie(movie: FavoriteMovie) {
        favDatabaseDao.insertMovie(movie)
    }

    suspend fun deleteMovie(id: Long) {
        favDatabaseDao.deleteFavMovie(id)
    }

    val allFavTvShows: LiveData<List<FavoriteTv>> = favDatabaseDao.getAllTvShows()

    suspend fun insertTvShow(tv: FavoriteTv) {
        favDatabaseDao.insetTvShow(tv)
    }
}
