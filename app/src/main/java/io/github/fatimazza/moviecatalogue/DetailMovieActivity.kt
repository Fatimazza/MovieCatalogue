package io.github.fatimazza.moviecatalogue

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import io.github.fatimazza.moviecatalogue.model.MovieResponse
import io.github.fatimazza.moviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    private var movie = MovieResponse()

    private var television = TvShow()

    private val ivMovieImage: ImageView
        get() = iv_movie_image

    private val tvMovieTitle: TextView
        get() = tv_movie_title

    private val tvMovieRelease: TextView
        get() = tv_movie_release

    private val tvMovieRate: TextView
        get() = tv_movie_rate

    private val tvMovieDescription: TextView
        get() = tv_movie_desc

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TELEVISION = "extra_television"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        getIntentExtra()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getIntentExtra() {
        if (intent.hasExtra(EXTRA_MOVIE)) {
            movie = intent.getParcelableExtra(EXTRA_MOVIE)
            displayMovieDetail(movie)
        } else if (intent.hasExtra(EXTRA_TELEVISION)) {
            television = intent.getParcelableExtra(EXTRA_TELEVISION)
            displayTelevisionDetail(television)
        }
    }

    private fun displayMovieDetail(movie: MovieResponse) {
        tvMovieTitle.text = movie.title
        tvMovieRelease.text = movie.release_date
        tvMovieRate.text = movie.vote_average.toString()
        tvMovieDescription.text = movie.overview

        Glide.with(this)
            .load(BuildConfig.POSTER_BASE_URL + movie.poster_path)
            .into(ivMovieImage)
    }

    private fun displayTelevisionDetail(tvShow: TvShow) {
        tvMovieTitle.text = tvShow.title
        tvMovieRelease.text = tvShow.releaseDate
        tvMovieRate.text = tvShow.runtime
        tvMovieDescription.text = tvShow.description

        Glide.with(this)
            .load(tvShow.poster)
            .into(ivMovieImage)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> true
        }
    }
}
