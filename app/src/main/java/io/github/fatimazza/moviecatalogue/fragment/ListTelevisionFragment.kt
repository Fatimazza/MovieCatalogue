package io.github.fatimazza.moviecatalogue.fragment


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.fatimazza.moviecatalogue.DetailMovieActivity
import io.github.fatimazza.moviecatalogue.ListTelevisionAdapter
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.model.TvShowResponse
import io.github.fatimazza.moviecatalogue.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_list_television.*

class ListTelevisionFragment : Fragment(), ListTelevisionAdapter.OnItemClickCallback {

    private val listTelevision: RecyclerView
        get() = rv_tvshow

    private lateinit var listTelevisionAdapter: ListTelevisionAdapter

    private lateinit var tvShowViewModel: TvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_television, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTvViewModel()
        setupListTelevisionAdapter()
        setItemClickListener()
        setClickListener()
        fetchTelevisionData()
    }

    private fun initTvViewModel() {
        tvShowViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(TvShowViewModel::class.java)
    }

    private fun setupListTelevisionAdapter() {
        listTelevision.layoutManager = LinearLayoutManager(requireContext())
        listTelevisionAdapter = ListTelevisionAdapter()
        listTelevisionAdapter.notifyDataSetChanged()
        listTelevision.adapter = listTelevisionAdapter
    }

    private fun fetchTelevisionData() {
        showLoading(true)
        tvShowViewModel.getTvShowData().observe(this, Observer { tvShow ->
            if (tvShow != null) {
                listTelevisionAdapter.setData(tvShow)
                showLoading(false)
            } else {
                showLoading(false)
                showFailedLoad(true)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbLoadingTelevision.visibility = View.VISIBLE
        } else {
            pbLoadingTelevision.visibility = View.GONE
        }
        showFailedLoad(false)
    }

    private fun showFailedLoad(state: Boolean) {
        if (state) {
            llTelevisionFailed.visibility = View.VISIBLE
            listTelevision.visibility = View.GONE
        } else {
            llTelevisionFailed.visibility = View.GONE
            listTelevision.visibility = View.VISIBLE
        }
    }

    private fun setClickListener() {
        btnRetryTelevision.setOnClickListener {
            fetchTelevisionData()
        }
    }

    private fun setItemClickListener() {
        listTelevisionAdapter.setOnItemClickCallback(this)
    }

    override fun onItemClicked(data: TvShowResponse) {
        val intentTelevision = Intent(requireContext(), DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.EXTRA_TELEVISION, data)
        }
        startActivity(intentTelevision)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("Izza", "- - config TV Changed")
    }
}
