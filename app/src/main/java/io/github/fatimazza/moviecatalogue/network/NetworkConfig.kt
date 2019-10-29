package io.github.fatimazza.moviecatalogue.network

import io.github.fatimazza.moviecatalogue.BuildConfig
import io.github.fatimazza.moviecatalogue.model.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class NetworkConfig {

    companion object {

        @Volatile
        private var retrofit: Retrofit? = null

        private fun getRetrofit(): Retrofit {
            return retrofit ?: synchronized(this) {
                retrofit ?: buildRetrofit().also {
                    retrofit = it
                }
            }
        }

        private fun buildRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun getInterceptor(): OkHttpClient {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        }

        fun api(): MovieAPIService {
            return getRetrofit().create(MovieAPIService::class.java)
        }
    }

}

interface MovieAPIService {

    @GET("discover/tv")
    fun fetchTvShow(@Query("api_key") apiKey: String, @Query("language") language: String)
            : Call<BaseResponse<TvShowResponse>>

    @GET("discover/movie")
    fun fetchMovie(@Query("api_key") apiKey: String, @Query("language") language: String)
            : Call<BaseResponse<MovieResponse>>

    @GET("tv/{id}")
    fun fetchDetailTvShow(@Path("id") id: Int, @Query("api_key") apiKey: String, @Query("language") language: String)
            : Call<TvDetailResponse>

    @GET("movie/{id}")
    fun fetchDetailMovie(@Path("id") id: Int, @Query("api_key") apiKey: String, @Query("language") language: String)
            : Call<MovieDetailResponse>

}
