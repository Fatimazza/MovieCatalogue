package io.github.fatimazza.moviecatalogue.fragment


import android.content.Intent
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
import io.github.fatimazza.moviecatalogue.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_list_movie.*

class ListMovieFragment : Fragment(), ListMovieAdapter.OnItemClickCallback {

    private val listMovie: RecyclerView
        get() = rv_movie

    private lateinit var listMovieAdapter: ListMovieAdapter

    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMovieViewModel()
        setupListMovieAdapter()
        setItemClickListener()
        fetchMovieData()
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
        movieViewModel.getMovieData().observe(this, Observer { movie ->
            if (movie != null) {
                listMovieAdapter.setData(movie)
                showLoading(false)
            } else {
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbLoadingMovie.visibility = View.VISIBLE
        } else {
            pbLoadingMovie.visibility = View.GONE
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

}
