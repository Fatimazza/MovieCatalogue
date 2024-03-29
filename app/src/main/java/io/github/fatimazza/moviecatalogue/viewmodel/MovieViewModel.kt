package io.github.fatimazza.moviecatalogue.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.fatimazza.moviecatalogue.model.MovieResponse
import io.github.fatimazza.moviecatalogue.network.NetworkRepository

class MovieViewModel : ViewModel() {

    fun getMovieData(locale: String): MutableLiveData<ArrayList<MovieResponse>> {
        return NetworkRepository().getMovies(locale)
    }

    fun searchMovie(locale: String, query: String): MutableLiveData<ArrayList<MovieResponse>> {
        return NetworkRepository().searchMovies(locale, query)
    }
}
