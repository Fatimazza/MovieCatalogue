package io.github.fatimazza.moviecatalogue.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.fatimazza.moviecatalogue.DetailMovieActivity
import io.github.fatimazza.moviecatalogue.ListTelevisionAdapter
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.fragment_list_television.*

class ListTelevisionFragment : Fragment(), ListTelevisionAdapter.OnItemClickCallback {

    private val listTelevision: RecyclerView
        get() = rv_tvshow

    private var list: ArrayList<TvShow> = arrayListOf()

    private lateinit var listTelevisionAdapter: ListTelevisionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_television, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListTelevisionAdapter()
        setItemClickListener()
    }

    private fun setupListTelevisionAdapter() {
        list.addAll(getTelevisionData())

        listTelevision.layoutManager = LinearLayoutManager(requireContext())
        listTelevisionAdapter = ListTelevisionAdapter(list)
        listTelevision.adapter = listTelevisionAdapter
    }

    private fun getTelevisionData(): ArrayList<TvShow> {
        val tvshowTitle = resources.getStringArray(R.array.tvshow_title)
        val tvshowPoster = resources.obtainTypedArray(R.array.tvshow_poster)
        val tvshowDesc = resources.getStringArray(R.array.tvshow_desc)
        val tvshowRelease = resources.getStringArray(R.array.tvshow_release)
        val tvshowRuntime = resources.getStringArray(R.array.tvshow_runtime)

        val listTelevision = ArrayList<TvShow>()
        for (position in tvshowTitle.indices) {
            val television = TvShow(
                tvshowTitle[position],
                tvshowPoster.getResourceId(position, -1),
                tvshowDesc[position],
                tvshowRelease[position],
                tvshowRuntime[position]
            )
            listTelevision.add(television)
        }
        tvshowPoster.recycle()
        return listTelevision
    }

    private fun setItemClickListener() {
        listTelevisionAdapter.setOnItemClickCallback(this)
    }

    override fun onItemClicked(data: TvShow) {
        val intentTelevision = Intent(requireContext(), DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.EXTRA_TELEVISION, data)
        }
        startActivity(intentTelevision)
    }
}
