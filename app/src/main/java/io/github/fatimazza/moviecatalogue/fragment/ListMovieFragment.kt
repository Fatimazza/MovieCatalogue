package io.github.fatimazza.moviecatalogue.fragment


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.fatimazza.moviecatalogue.DetailMovieActivity
import io.github.fatimazza.moviecatalogue.ListMovieAdapter
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.model.MovieResponse
import io.github.fatimazza.moviecatalogue.utils.getFormattedLanguage
import io.github.fatimazza.moviecatalogue.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_list_movie.*

class ListMovieFragment : Fragment(), ListMovieAdapter.OnItemClickCallback {

    private val listMovie: RecyclerView
        get() = rv_movie

    private lateinit var listMovieAdapter: ListMovieAdapter

    private lateinit var movieViewModel: MovieViewModel

    private lateinit var locale: String

    companion object {
        private const val STATE_LIST_MOVIE = "state_list_movie"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLanguage()
        initMovieViewModel()
        setupListMovieAdapter()
        setItemClickListener()
        setClickListener()

        if (savedInstanceState == null) {
            fetchMovieData()
        } else {
            val stateList =
                savedInstanceState.getParcelableArrayList<MovieResponse>(STATE_LIST_MOVIE)
            if (stateList != null) {
                if (stateList.isNotEmpty()) {
                    listMovieAdapter.setData(stateList)
                } else {
                    showFailedLoad(true)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE_LIST_MOVIE, listMovieAdapter.getData())
    }

    private fun initMovieViewModel() {
        movieViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MovieViewModel::class.java)
    }

    private fun setupListMovieAdapter() {
        listMovie.layoutManager = LinearLayoutManager(requireContext())
        listMovieAdapter = ListMovieAdapter()
        listMovieAdapter.notifyDataSetChanged()
        listMovie.adapter = listMovieAdapter
    }

    private fun fetchMovieData() {
        showLoading(true)
        movieViewModel.getMovieData(locale.getFormattedLanguage()).observe(this, Observer { movie ->
            if (movie != null) {
                listMovieAdapter.setData(movie)
                showLoading(false)
            } else {
                showLoading(false)
                showFailedLoad(true)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbLoadingMovie.visibility = View.VISIBLE
        } else {
            pbLoadingMovie.visibility = View.GONE
        }
        showFailedLoad(false)
    }

    private fun showFailedLoad(state: Boolean) {
        if (state) {
            llMovieFailed.visibility = View.VISIBLE
            listMovie.visibility = View.GONE
        } else {
            llMovieFailed.visibility = View.GONE
            listMovie.visibility = View.VISIBLE
        }
    }

    private fun setClickListener() {
        btnRetryMovie.setOnClickListener {
            fetchMovieData()
        }
    }

    private fun setItemClickListener() {
        listMovieAdapter.setOnItemClickCallback(this)
    }

    override fun onItemClicked(data: MovieResponse) {
        val intentMovie = Intent(requireContext(), DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.EXTRA_MOVIE, data)
        }
        startActivity(intentMovie)
    }

    private fun setLanguage() {
        locale = resources.configuration.locale.toLanguageTag()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (!locale.equals(newConfig.locale.toLanguageTag(), true)) {
            setLanguage()
            activity?.recreate()
            fetchMovieData()
        }
    }
}
