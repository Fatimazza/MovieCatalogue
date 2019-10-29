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
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.adapter.FavoriteMovieAdapter
import io.github.fatimazza.moviecatalogue.database.FavoriteMovie
import io.github.fatimazza.moviecatalogue.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorite_movie.*


class FavoriteMovieFragment : Fragment(), FavoriteMovieAdapter.OnItemClickCallback {

    private val listFavMovie: RecyclerView
        get() = rv_fav_movie

    private lateinit var listFavMovieAdapter: FavoriteMovieAdapter

    private lateinit var favMovieViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoriteMovieViewModel()
        setupListFavoriteMovieAdapter()
        setItemClickListener()
        fetchFavoriteMovieData()
    }

    private fun initFavoriteMovieViewModel() {
        favMovieViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }

    private fun setupListFavoriteMovieAdapter() {
        listFavMovie.layoutManager = LinearLayoutManager(requireContext())
        listFavMovieAdapter = FavoriteMovieAdapter()
        listFavMovieAdapter.notifyDataSetChanged()
        listFavMovie.adapter = listFavMovieAdapter
    }

    private fun fetchFavoriteMovieData() {
        favMovieViewModel.getAllFavoriteMovies().observe(this, Observer { listMovie ->
            if (listMovie.isNotEmpty()) {
                listFavMovieAdapter.setData(listMovie)
            }
        })
    }

    private fun setItemClickListener() {
        listFavMovieAdapter.setOnItemClickCallback(this)
    }

    override fun onItemClicked(data: FavoriteMovie) {
        val intentMovie = Intent(requireContext(), DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.EXTRA_MOVIE, data.movieId)
        }
        startActivity(intentMovie)
    }
}
