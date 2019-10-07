package io.github.fatimazza.moviecatalogue

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.fatimazza.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    private val ivMovieImage: ImageView
        get() = iv_movie_image

    private val tvMovieTitle: TextView
        get() = tv_movie_title

    private val tvMovieRelease: TextView
        get() = tv_movie_release

    private val tvMovieRuntime: TextView
        get() = tv_movie_time

    private val tvMovieDescription: TextView
        get() = tv_movie_desc

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        displayMovieDetail()
    }

    private fun displayMovieDetail() {
        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        tvMovieTitle.text = movie.title
        tvMovieRelease.text = movie.releaseDate
        tvMovieRuntime.text = movie.runtime
        tvMovieDescription.text = movie.description
    }
}
