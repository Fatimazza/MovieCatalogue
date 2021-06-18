package io.github.fatimazza.moviecatalogue.fragment

import android.content.Intent
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
import io.github.fatimazza.moviecatalogue.DetailMovieActivity
import io.github.fatimazza.moviecatalogue.adapter.FavoriteTelevisionAdapter
import io.github.fatimazza.moviecatalogue.database.FavoriteTv
import io.github.fatimazza.moviecatalogue.databinding.FragmentFavoriteTelevisionBinding
import io.github.fatimazza.moviecatalogue.viewmodel.FavoriteViewModel


class FavoriteTelevisionFragment : Fragment(), FavoriteTelevisionAdapter.OnItemClickCallback {

    private var _binding: FragmentFavoriteTelevisionBinding? = null
    private val binding get() = _binding!!

    private val listFavTelevision: RecyclerView
        get() = binding.rvFavTv

    private val tvFavTelevisionEmpty: TextView
        get() = binding.tvFavTelevisionEmpty

    private lateinit var listFavTelevisionAdapter: FavoriteTelevisionAdapter

    private lateinit var favTelevisionViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteTelevisionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoriteTelevisionViewModel()
        setupListFavoriteTelevisionAdapter()
        setItemClickListener()
        fetchFavoriteTelevisionData()
    }

    private fun initFavoriteTelevisionViewModel() {
        favTelevisionViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }

    private fun setupListFavoriteTelevisionAdapter() {
        listFavTelevision.layoutManager = LinearLayoutManager(requireContext())
        listFavTelevisionAdapter = FavoriteTelevisionAdapter()
        listFavTelevisionAdapter.notifyDataSetChanged()
        listFavTelevision.adapter = listFavTelevisionAdapter
    }

    private fun fetchFavoriteTelevisionData() {
        favTelevisionViewModel.getAllFavoriteTvShows().observe(this, Observer { listTvShow ->
            if (listTvShow != null) {
                listFavTelevisionAdapter.setData(listTvShow)
                tvFavTelevisionEmpty.visibility =
                    if (listTvShow.isEmpty()) View.VISIBLE else View.GONE
            } else {
                listFavTelevisionAdapter.setData(listFavTelevisionAdapter.getData())
                tvFavTelevisionEmpty.visibility = View.VISIBLE
            }
        })
    }

    private fun setItemClickListener() {
        listFavTelevisionAdapter.setOnItemClickCallback(this)
    }

    override fun onItemClicked(data: FavoriteTv) {
        val intentMovie = Intent(requireContext(), DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.EXTRA_TELEVISION, data.tvId)
        }
        startActivity(intentMovie)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
