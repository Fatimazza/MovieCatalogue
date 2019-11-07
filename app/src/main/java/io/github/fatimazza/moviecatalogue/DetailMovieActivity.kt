package io.github.fatimazza.moviecatalogue

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.github.fatimazza.moviecatalogue.database.FavoriteMovie
import io.github.fatimazza.moviecatalogue.database.FavoriteTv
import io.github.fatimazza.moviecatalogue.utils.getFormattedLanguage
import io.github.fatimazza.moviecatalogue.viewmodel.DetailViewModel
import io.github.fatimazza.moviecatalogue.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    private val ivMovieImage: ImageView
        get() = iv_movie_image

    private val tvMovieTitle: TextView
        get() = tv_movie_title

    private val tvMovieReleaseTitle: TextView
        get() = tv_movie_release_title

    private val tvMovieRelease: TextView
        get() = tv_movie_release

    private val tvMovieRateTitle: TextView
        get() = tv_movie_time_title

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
        private const val STATE_DETAIL_TITLE = "state_detail_title"
        private const val STATE_DETAIL_OVERVIEW = "state_detail_overview"
        private const val STATE_DETAIL_RELEASE_DATE = "state_detail_release_date"
        private const val STATE_DETAIL_VOTE_AVERAGE = "state_detail_vote_average"
        private const val STATE_DETAIL_POSTER_PATH = "state_detail_poster_path"
        private const val STATE_LOCALE = "state_locale"
        private const val STATE_FAVORITE = "state_favorite"
    }

    private lateinit var localeChangedReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        getIntentExtra()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        createLocaleChangedReceiver()

        setLanguage()
        initViewModel()
        setClickListener()

        initiateFetchDetail(savedInstanceState)
    }

    private fun setFavorite() {
        if (isMovie) {
            favoriteViewModel.getMovie(detailId.toLong()).observe(this, Observer { movie ->
                Log.d("Izza", "getMovie $movie")
                isFavorited = movie != null
                setFavoriteIcon()
            })
        } else {
            favoriteViewModel.getTvShow(detailId.toLong()).observe(this, Observer { tvShow ->
                Log.d("Izza", "getTv $tvShow")
                isFavorited = tvShow != null
                setFavoriteIcon()
            })
        }
    }

    private fun initiateFetchDetail(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            fetchDetails()
        } else {
            val stateLocale = savedInstanceState.getString(STATE_LOCALE)
            if (stateLocale != locale) {
                fetchDetails()
            } else {
                val stateTitle = savedInstanceState.getString(STATE_DETAIL_TITLE)
                val stateOverview = savedInstanceState.getString(STATE_DETAIL_OVERVIEW)
                val stateReleaseDate = savedInstanceState.getString(STATE_DETAIL_RELEASE_DATE)
                val stateVoteAverage = savedInstanceState.getString(STATE_DETAIL_VOTE_AVERAGE)
                val statePosterPath = savedInstanceState.getString(STATE_DETAIL_POSTER_PATH)
                val stateFavorite = savedInstanceState.getBoolean(STATE_FAVORITE, false)

                if (stateTitle != null) {
                    if (stateTitle.isNotEmpty()) {
                        populateDetails(
                            stateTitle, stateOverview ?: "",
                            stateReleaseDate ?: "", stateVoteAverage ?: "", statePosterPath ?: ""
                        )
                        showDetailMovie(true)
                        isFavorited = stateFavorite
                    } else {
                        showDetailMovie(false)
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_DETAIL_TITLE, detailTitle)
        outState.putString(STATE_DETAIL_OVERVIEW, detailOverview)
        outState.putString(STATE_DETAIL_RELEASE_DATE, detailReleaseDate)
        outState.putString(STATE_DETAIL_VOTE_AVERAGE, detailVoteAverage)
        outState.putString(STATE_DETAIL_POSTER_PATH, detailPosterPath)
        outState.putString(STATE_LOCALE, locale)
        outState.putBoolean(STATE_FAVORITE, isFavorited)
    }

    private fun createLocaleChangedReceiver() {
        localeChangedReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                recreate()
            }
        }
        val localeChangedIntentFilter = IntentFilter(Intent.ACTION_LOCALE_CHANGED)
        registerReceiver(localeChangedReceiver, localeChangedIntentFilter)
    }

    private fun initViewModel() {
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)
    }

    private fun setClickListener() {
        btnRetryDetail.setOnClickListener {
            fetchDetails()
        }
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

    private fun showLoading(state: Boolean) {
        pbLoadingDetail.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showDetailMovie(state: Boolean) {
        val viewState = if (state) View.VISIBLE else View.GONE
        ivMovieImage.visibility = viewState
        tvMovieTitle.visibility = viewState
        tvMovieReleaseTitle.visibility = viewState
        tvMovieRelease.visibility = viewState
        tvMovieRateTitle.visibility = viewState
        tvMovieRate.visibility = viewState
        tvMovieDescription.visibility = viewState
        showFailedLoad(!state)
    }

    private fun showFailedLoad(state: Boolean) {
        llDetailFailed.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun fetchDetails() {
        showFailedLoad(false)
        showLoading(true)
        if (isMovie) {
            detailViewModel.getMovieDetail(
                Integer.valueOf(detailId), locale.getFormattedLanguage()
            )
                .observe(this, Observer { movieDetail ->
                    showLoading(false)
                    if (movieDetail != null) {
                        populateDetails(
                            movieDetail.title ?: "",
                            movieDetail.overview ?: "",
                            movieDetail.release_date ?: "",
                            movieDetail.vote_average.toString() ?: "",
                            movieDetail.poster_path ?: ""
                        )
                        showDetailMovie(true)
                        setFavorite()
                    } else {
                        showDetailMovie(false)
                    }
                })
        } else {
            detailViewModel.getTelevisionDetail(
                Integer.valueOf(detailId), locale.getFormattedLanguage()
            )
                .observe(this, Observer { tvDetail ->
                    showLoading(false)
                    if (tvDetail != null) {
                        populateDetails(
                            tvDetail.name ?: "",
                            tvDetail.overview ?: "",
                            tvDetail.first_air_date ?: "",
                            tvDetail.vote_average.toString() ?: "",
                            tvDetail.poster_path ?: ""
                        )
                        showDetailMovie(true)
                        setFavorite()
                    } else {
                        showDetailMovie(false)
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
                if (pbLoadingDetail.isVisible || llDetailFailed.isVisible) {
                    Toast.makeText(this, getString(R.string.favorite_error), Toast.LENGTH_SHORT)
                        .show()
                    return false
                }
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
                        Log.d(
                            "Izza",
                            "" + listMovie[i].favMovieId + " " + listMovie[i].movieTitle + " " + listMovie[i].movieLang
                        )
                    }
                }
            })
        } else {
            favoriteViewModel.getAllFavoriteTvShows().observe(this, Observer { listTvShows ->
                if (listTvShows.isNotEmpty()) {
                    for (i in 0 until listTvShows.size) {
                        Log.d(
                            "Izza",
                            "" + listTvShows[i].favTvId + " " + listTvShows[i].tvTitle + " " + listTvShows[i].tvLang
                        )
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
                    detailPosterPath,
                    locale.getFormattedLanguage()
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
                    detailPosterPath,
                    locale.getFormattedLanguage()
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

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(localeChangedReceiver)
    }
}
