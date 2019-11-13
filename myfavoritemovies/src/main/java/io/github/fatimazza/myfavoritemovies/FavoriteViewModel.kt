package io.github.fatimazza.myfavoritemovies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.fatimazza.myfavoritemovies.database.FavoriteDao
import io.github.fatimazza.myfavoritemovies.entity.FavoriteMovie
import io.github.fatimazza.myfavoritemovies.entity.FavoriteTv
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoriteDao = FavoriteDao(application)
//    private lateinit var allFavMovies: LiveData<List<FavoriteMovie>>
//    private lateinit var allFavTvShows: LiveData<List<FavoriteTv>>

    private val _favouriteMovies = MutableLiveData<List<FavoriteMovie>>()
    private val _favouriteTvs = MutableLiveData<List<FavoriteTv>>()
    val favouriteMovies: LiveData<List<FavoriteMovie>> = _favouriteMovies
    val favouriteTvs: LiveData<List<FavoriteTv>> = _favouriteTvs

    fun getFavouriteMovies() =
        GlobalScope.launch {
            _favouriteMovies.postValue(repository.getFavoriteMovieList())

        }

    fun getFavouriteTvs() =
        GlobalScope.launch {
            _favouriteTvs.postValue(repository.getFavoriteTvList())
        }


//    init {
//        repository = FavoriteDao(application)
////        allFavMovies = repository.getFavoriteTvList()
////        allFavTvShows = repository.getFavoriteTvList()
//    }

//    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>> {
//        return allFavMovies
//    }

//    fun getAllFavMovies() {
//        GlobalScope.launch {
//            repository.getFavoriteMovieList()
//        }
//    }

//    fun getAllFavTvs() {
//        GlobalScope.launch {
//            repository.getFavoriteTvList()
//        }
//    }

    /*fun getMovie(id: Long): LiveData<FavoriteMovie> {
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
    }*/

//    fun getAllFavoriteTvShows(): LiveData<List<FavoriteTv>> {
//        return allFavTvShows
//    }

    /*fun getTvShow(id: Long): LiveData<FavoriteTv> {
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
    }*/
}
