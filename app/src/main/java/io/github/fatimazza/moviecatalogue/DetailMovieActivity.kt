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
import io.github.fatimazza.moviecatalogue.viewmodel.DetailViewModel
import io.github.fatimazza.moviecatalogue.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

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

    private var detailId: String = ""

    private lateinit var locale: String

    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var detailViewModel: DetailViewModel

    private var detailTitle: String = ""
    private var detailOverview: String = ""
    private var detailReleaseDate: String = ""
    private var detailVoteAverage: String = ""
    private var detailPosterPath: String = ""

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TELEVISION = "extra_television"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        getIntentExtra()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setLanguage()
        initViewModel()
        fetchDetails()
    }

    private fun initViewModel() {
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)
    }

    private fun getIntentExtra() {
        if (intent.hasExtra(EXTRA_MOVIE)) {
            isMovie = true
            detailId = intent.getStringExtra(EXTRA_MOVIE) ?: ""
        } else if (intent.hasExtra(EXTRA_TELEVISION)) {
            isMovie = false
            detailId = intent.getStringExtra(EXTRA_TELEVISION) ?: ""
        }
    }

    private fun setLanguage() {
        locale = resources.configuration.locale.toLanguageTag()
    }

    private fun fetchDetails() {
        if (isMovie) {
            detailViewModel.getMovieDetail(Integer.valueOf(detailId), locale)
                .observe(this, Observer { movieDetail ->
                    if (movieDetail != null) {
                        populateDetails(
                            movieDetail.title,
                            movieDetail.overview,
                            movieDetail.release_date,
                            movieDetail.vote_average.toString(),
                            movieDetail.poster_path
                        )
                    } else {

                    }
                })
        } else {
            detailViewModel.getTelevisionDetail(Integer.valueOf(detailId), locale).observe(
                this, Observer { tvDetail ->
                    if (tvDetail != null) {
                        populateDetails(
                            tvDetail.name,
                            tvDetail.overview,
                            tvDetail.first_air_date,
                            tvDetail.vote_average.toString(),
                            tvDetail.poster_path
                        )
                    } else {

                    }
                }
            )
        }
    }

    private fun populateDetails(
        title: String, overview: String, releaseDate: String,
        voteAverage: String, posterPath: String
    ) {
        detailTitle = title
        detailOverview = if (overview.isEmpty())
            getString(R.string.list_movie_description_empty) else overview
        detailReleaseDate = releaseDate
        detailVoteAverage = voteAverage
        detailPosterPath = posterPath

        tvMovieTitle.text = detailTitle
        tvMovieDescription.text = detailOverview
        tvMovieRelease.text = detailReleaseDate
        tvMovieRate.text = detailVoteAverage

        Glide.with(this)
            .load(BuildConfig.POSTER_BASE_URL + detailPosterPath)
            .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .placeholder(R.color.colorAccent)
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
                    detailId,
                    detailTitle,
                    detailOverview,
                    detailVoteAverage,
                    detailReleaseDate,
                    detailPosterPath
                )
            )
        } else {
            favoriteViewModel.insertTvShow(
                FavoriteTv(
                    0,
                    detailId,
                    detailTitle,
                    detailOverview,
                    detailVoteAverage,
                    detailReleaseDate,
                    detailPosterPath
                )
            )
        }
    }

    private fun removeFromFavorite() {
        if (isMovie) {
            favoriteViewModel.deleteMovie(
                detailId.toLong()
            )
        } else {
            favoriteViewModel.deleteTvShow(
                detailId.toLong()
            )
        }
    }
}
