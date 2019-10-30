package io.github.fatimazza.moviecatalogue.database

import androidx.lifecycle.LiveData

class DatabaseRepository(private val favDatabaseDao: FavoriteDatabaseDao) {

    val allFavMovies: LiveData<List<FavoriteMovie>> = favDatabaseDao.getAllMovies()

    fun getMovie(id: Long): LiveData<FavoriteMovie> = favDatabaseDao.getFavMovie(id)

    fun insertMovie(movie: FavoriteMovie) {
        favDatabaseDao.insertMovie(movie)
    }

    fun deleteMovie(id: Long) {
        favDatabaseDao.deleteFavMovie(id)
    }

    val allFavTvShows: LiveData<List<FavoriteTv>> = favDatabaseDao.getAllTvShows()

    fun getTvShow(id: Long): LiveData<FavoriteTv> = favDatabaseDao.getFavTvShow(id)

    fun insertTvShow(tv: FavoriteTv) {
        favDatabaseDao.insetTvShow(tv)
    }

    fun deleteTvShow(id: Long) {
        favDatabaseDao.deleteFavTvShow(id)
    }
}
