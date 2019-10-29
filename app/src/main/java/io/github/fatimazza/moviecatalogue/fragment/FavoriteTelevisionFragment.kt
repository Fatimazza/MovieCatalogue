package io.github.fatimazza.moviecatalogue.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.adapter.FavoriteTelevisionAdapter
import kotlinx.android.synthetic.main.fragment_favorite_television.*


class FavoriteTelevisionFragment : Fragment() {

    private val listFavTelevision: RecyclerView
        get() = rv_fav_tv

    private lateinit var listFavTelevisionAdapter: FavoriteTelevisionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_television, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListFavoriteTelevisionAdapter()
    }

    private fun setupListFavoriteTelevisionAdapter() {
        listFavTelevision.layoutManager = LinearLayoutManager(requireContext())
        listFavTelevisionAdapter = FavoriteTelevisionAdapter()
        listFavTelevisionAdapter.notifyDataSetChanged()
        listFavTelevision.adapter = listFavTelevisionAdapter
    }
}
