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
    
}
