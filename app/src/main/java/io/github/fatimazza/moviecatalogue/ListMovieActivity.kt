package io.github.fatimazza.moviecatalogue

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import io.github.fatimazza.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.activity_list_movie.*

class ListMovieActivity : AppCompatActivity(), ListMovieAdapter.OnItemClickCallback {

    private val listMovie: ListView
    get() = lv_movie

    private var list: ArrayList<Movie> = arrayListOf()

    private lateinit var listMovieAdapter: ListMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_movie)

        setupListMovieAdapter(this)
        setItemClickListener()
    }

    private fun setupListMovieAdapter(context: Context) {
        list.addAll(getMoviesData())

        listMovieAdapter = ListMovieAdapter(context, list)
        listMovie.adapter = listMovieAdapter
    }

    private fun getMoviesData(): ArrayList<Movie> {
        val movieTitle = resources.getStringArray(R.array.movie_title)
        val moviePoster = resources.getIntArray(R.array.movie_poster)
        val movieDesc = resources.getStringArray(R.array.movie_desc)
        val movieRelease = resources.getStringArray(R.array.movie_release)
        val movieRuntime = resources.getStringArray(R.array.movie_runtime)

        val listMovie = ArrayList<Movie>()
        for (position in movieTitle.indices) {
            val movie = Movie(
                movieTitle[position],
                moviePoster[position],
                movieDesc[position],
                movieRelease[position],
                movieRuntime[position]
            )
            listMovie.add(movie)
        }
        return listMovie
    }

    private fun setItemClickListener() {
        listMovieAdapter.setOnItemClickCallback(this)
    }

    override fun onItemClicked(data: Movie) {
        val intentMovie = Intent(this, DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.EXTRA_MOVIE, data)
        }
        startActivity(intentMovie)
    }
}
