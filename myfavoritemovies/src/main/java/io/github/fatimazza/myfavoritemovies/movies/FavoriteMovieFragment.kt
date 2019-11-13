package io.github.fatimazza.myfavoritemovies.movies


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.fatimazza.myfavoritemovies.FavoriteViewModel
import io.github.fatimazza.myfavoritemovies.R
import io.github.fatimazza.myfavoritemovies.entity.FavoriteMovie
import kotlinx.android.synthetic.main.fragment_favorite_movie.*


class FavoriteMovieFragment : Fragment(), FavoriteMovieAdapter.OnItemClickCallback {

    private val listFavMovie: RecyclerView
        get() = rv_fav_movie

    private val tvFavMovieEmpty: TextView
        get() = tv_fav_movie_empty

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

    override fun onResume() {
        super.onResume()
        favMovieViewModel.getFavouriteMovies()
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
        favMovieViewModel.favouriteMovies.observe(this, Observer { listMovie ->
            if (listMovie != null) {
                listFavMovieAdapter.setData(listMovie)
                tvFavMovieEmpty.visibility = if (listMovie.isEmpty()) View.VISIBLE else View.GONE
            } else {
                listFavMovieAdapter.setData(listFavMovieAdapter.getData())
                tvFavMovieEmpty.visibility = View.VISIBLE
            }
        })
    }

    private fun setItemClickListener() {
        listFavMovieAdapter.setOnItemClickCallback(this)
    }

    override fun onItemClicked(data: FavoriteMovie) {
        /*val intentMovie = Intent(requireContext(), DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.EXTRA_MOVIE, data.movieId)
        }
        startActivity(intentMovie)*/
    }
}