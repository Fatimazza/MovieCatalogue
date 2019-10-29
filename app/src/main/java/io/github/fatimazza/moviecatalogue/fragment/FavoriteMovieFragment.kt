package io.github.fatimazza.moviecatalogue.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.adapter.FavoriteMovieAdapter
import kotlinx.android.synthetic.main.fragment_favorite_movie.*


class FavoriteMovieFragment : Fragment() {

    private val listFavMovie: RecyclerView
        get() = rv_fav_movie

    private lateinit var listFavMovieAdapter: FavoriteMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListFavoriteMovieAdapter()
    }

    private fun setupListFavoriteMovieAdapter() {
        listFavMovie.layoutManager = LinearLayoutManager(requireContext())
        listFavMovieAdapter = FavoriteMovieAdapter()
        listFavMovieAdapter.notifyDataSetChanged()
        listFavMovie.adapter = listFavMovieAdapter
    }
}
