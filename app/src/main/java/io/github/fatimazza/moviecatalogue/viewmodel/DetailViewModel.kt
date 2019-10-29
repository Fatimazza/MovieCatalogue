package io.github.fatimazza.moviecatalogue.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.fatimazza.moviecatalogue.model.MovieDetailResponse
import io.github.fatimazza.moviecatalogue.model.TvDetailResponse
import io.github.fatimazza.moviecatalogue.network.NetworkRepository

class DetailViewModel : ViewModel() {

    fun getMovieDetail(id: Int, locale: String): MutableLiveData<MovieDetailResponse> {
        return NetworkRepository().getMovieDetail(id, locale)
    }

    fun getTelevisionDetail(id: Int, locale: String): MutableLiveData<TvDetailResponse> {
        return NetworkRepository().getTvShowDetail(id, locale)
    }
}
