package io.github.fatimazza.moviecatalogue.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import io.github.fatimazza.moviecatalogue.DetailMovieActivity
import io.github.fatimazza.moviecatalogue.ListMovieAdapter
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.fragment_list_movie.*

class ListMovieFragment : Fragment(), ListMovieAdapter.OnItemClickCallback {

    private val listMovie: ListView
        get() = lv_movie

    private var list: ArrayList<Movie> = arrayListOf()

    private lateinit var listMovieAdapter: ListMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListMovieAdapter()
        setItemClickListener()
    }

    private fun setupListMovieAdapter() {
        list.addAll(getMoviesData())

        listMovieAdapter = ListMovieAdapter(requireContext(), list)
        listMovie.adapter = listMovieAdapter
    }

    private fun getMoviesData(): ArrayList<Movie> {
        val movieTitle = resources.getStringArray(R.array.movie_title)
        val moviePoster = resources.obtainTypedArray(R.array.movie_poster)
        val movieDesc = resources.getStringArray(R.array.movie_desc)
        val movieRelease = resources.getStringArray(R.array.movie_release)
        val movieRuntime = resources.getStringArray(R.array.movie_runtime)

        val listMovie = ArrayList<Movie>()
        for (position in movieTitle.indices) {
            val movie = Movie(
                movieTitle[position],
                moviePoster.getResourceId(position, -1),
                movieDesc[position],
                movieRelease[position],
                movieRuntime[position]
            )
            listMovie.add(movie)
        }
        moviePoster.recycle()
        return listMovie
    }

    private fun setItemClickListener() {
        listMovieAdapter.setOnItemClickCallback(this)
    }

    override fun onItemClicked(data: Movie) {
        val intentMovie = Intent(requireContext(), DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.EXTRA_MOVIE, data)
        }
        startActivity(intentMovie)
    }

}
