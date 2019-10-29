package io.github.fatimazza.moviecatalogue

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.github.fatimazza.moviecatalogue.database.FavoriteMovie
import io.github.fatimazza.moviecatalogue.database.FavoriteTv
import io.github.fatimazza.moviecatalogue.model.MovieResponse
import io.github.fatimazza.moviecatalogue.model.TvShowResponse
import io.github.fatimazza.moviecatalogue.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    private var movie = MovieResponse()

    private var television = TvShowResponse()

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

    private var menuItem: Menu? = null

    private var isFavorited: Boolean = false

    private var isMovie: Boolean = false

    private lateinit var favoriteViewModel: FavoriteViewModel

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TELEVISION = "extra_television"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        getIntentExtra()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViewModel()
    }

    private fun initViewModel() {
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }

    private fun getIntentExtra() {
        if (intent.hasExtra(EXTRA_MOVIE)) {
            isMovie = true
            movie = intent.getParcelableExtra(EXTRA_MOVIE)
            displayMovieDetail(movie)
        } else if (intent.hasExtra(EXTRA_TELEVISION)) {
            isMovie = false
            television = intent.getParcelableExtra(EXTRA_TELEVISION)
            displayTelevisionDetail(television)
        }
    }

    private fun displayMovieDetail(movie: MovieResponse) {
        tvMovieTitle.text = movie.title
        tvMovieRelease.text = movie.release_date
        tvMovieRate.text = movie.vote_average.toString()
        tvMovieDescription.text = if (movie.overview.isEmpty())
            getString(R.string.list_movie_description_empty) else movie.overview

        Glide.with(this)
            .load(BuildConfig.POSTER_BASE_URL + movie.poster_path)
            .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .placeholder(R.color.colorAccent)
            .into(ivMovieImage)
    }

    private fun displayTelevisionDetail(tvShow: TvShowResponse) {
        tvMovieTitle.text = tvShow.name
        tvMovieRelease.text = tvShow.first_air_date
        tvMovieRate.text = tvShow.vote_average.toString()
        tvMovieDescription.text = if (tvShow.overview.isEmpty())
            getString(R.string.list_movie_description_empty) else tvShow.overview

        Glide.with(this)
            .load(BuildConfig.POSTER_BASE_URL + tvShow.poster_path)
            .into(ivMovieImage)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavoriteIcon()
        return super.onCreateOptionsMenu(menu)
    }

    private fun setFavoriteIcon() {
        if (isFavorited)
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorited)
        else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_favorite -> {
                isFavorited = !isFavorited
                setFavoriteIcon()
                addOrRemoveFavorite()
                true
            }
            else -> true
        }
    }

    private fun addOrRemoveFavorite() {
        if (isFavorited) {
            addToFavorite()
        } else {
            removeFromFavorite()
        }

        if (isMovie) {
            favoriteViewModel.getAllFavoriteMovies().observe(this, Observer { listMovie ->
                if (listMovie.isNotEmpty()) {
                    for (i in 0 until listMovie.size) {
                        Log.d("Izza", "" + listMovie[i].favMovieId + " " + listMovie[i].movieTitle)
                    }
                }
            })
        } else {
            favoriteViewModel.getAllFavoriteTvShows().observe(this, Observer { listTvShows ->
                if (listTvShows.isNotEmpty()) {
                    for (i in 0 until listTvShows.size) {
                        Log.d("Izza", "" + listTvShows[i].favTvId + " " + listTvShows[i].tvTitle)
                    }
                }
            })
        }
    }

    private fun addToFavorite() {
        if (isMovie) {
            favoriteViewModel.insertMovie(
                FavoriteMovie(
                    0,
                    movie.id.toString(),
                    movie.title,
                    movie.overview,
                    movie.vote_average.toString(),
                    movie.poster_path
                )
            )
        } else {
            favoriteViewModel.insertTvShow(
                FavoriteTv(
                    0,
                    television.id.toString(),
                    television.name,
                    television.overview,
                    television.vote_average.toString(),
                    television.poster_path
                )
            )
        }
    }

    private fun removeFromFavorite() {
        if (isMovie) {
            favoriteViewModel.deleteMovie(
                movie.id.toLong()
            )
        } else {
            favoriteViewModel.deleteTvShow(
                television.id.toLong()
            )
        }
    }
}
