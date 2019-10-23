package io.github.fatimazza.moviecatalogue.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.fatimazza.moviecatalogue.model.TvShowResponse
import io.github.fatimazza.moviecatalogue.network.NetworkRepository

class TvShowViewModel : ViewModel() {

    fun getTvShowData(locale: String): MutableLiveData<ArrayList<TvShowResponse>> {
        return NetworkRepository().getTvShow(locale)
    }
}
