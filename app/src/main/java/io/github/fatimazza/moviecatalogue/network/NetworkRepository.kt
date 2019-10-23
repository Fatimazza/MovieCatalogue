package io.github.fatimazza.moviecatalogue.network

import androidx.lifecycle.MutableLiveData
import io.github.fatimazza.moviecatalogue.BuildConfig
import io.github.fatimazza.moviecatalogue.model.BaseResponse
import io.github.fatimazza.moviecatalogue.model.MovieResponse
import io.github.fatimazza.moviecatalogue.model.TvShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepository {

    fun getMovies(locale: String): MutableLiveData<ArrayList<MovieResponse>> {
        val movieData = MutableLiveData<ArrayList<MovieResponse>>()
        val listMovie = ArrayList<MovieResponse>()

        NetworkConfig.api().fetchMovie(BuildConfig.API_KEY, locale)
            .enqueue(object : Callback<BaseResponse<MovieResponse>> {
                override fun onFailure(call: Call<BaseResponse<MovieResponse>>, t: Throwable) {
                    movieData.postValue(null)
                }

                override fun onResponse(
                    call: Call<BaseResponse<MovieResponse>>,
                    response: Response<BaseResponse<MovieResponse>>
                ) {
                    if (response.isSuccessful) {
                        val listSize = response.body()?.results?.size as Int
                        for (i in 0 until listSize) {
                            listMovie.add(response.body()?.results?.get(i) as MovieResponse)
                        }
                        movieData.postValue(listMovie)
                    }
                }
            })
        return movieData
    }

    fun getTvShow(locale: String): MutableLiveData<ArrayList<TvShowResponse>> {
        val tvShowData = MutableLiveData<ArrayList<TvShowResponse>>()
        val listTvShow = ArrayList<TvShowResponse>()

        NetworkConfig.api().fetchTvShow(BuildConfig.API_KEY, locale)
            .enqueue(object : Callback<BaseResponse<TvShowResponse>> {
                override fun onFailure(call: Call<BaseResponse<TvShowResponse>>, t: Throwable) {
                    tvShowData.postValue(null)
                }

                override fun onResponse(
                    call: Call<BaseResponse<TvShowResponse>>,
                    response: Response<BaseResponse<TvShowResponse>>
                ) {
                    if (response.isSuccessful) {
                        val listSize = response.body()?.results?.size as Int
                        for (i in 0 until listSize) {
                            listTvShow.add(response.body()?.results?.get(i) as TvShowResponse)
                        }
                        tvShowData.postValue(listTvShow)
                    }
                }
            })
        return tvShowData
    }
}
