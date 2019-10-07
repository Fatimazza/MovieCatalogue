package io.github.fatimazza.moviecatalogue

import android.content.Context
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.fatimazza.moviecatalogue.model.Movie
import io.github.fatimazza.moviecatalogue.model.MoviesData
import kotlinx.android.synthetic.main.activity_list_movie.*

class ListMovieActivity : AppCompatActivity(), ListMovieAdapter.OnItemClickCallback {

    private val listMovie: ListView
    get() = lv_movie

    private var list: ArrayList<Movie> = arrayListOf()

    private lateinit var listMovieAdapter: ListMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_movie)

        setupListMovieAdapter(this)
        setItemClickListener()
    }

    private fun setupListMovieAdapter(context: Context) {
        list.addAll(MoviesData.listData)

        listMovieAdapter = ListMovieAdapter(context, list)
        listMovie.adapter = listMovieAdapter
    }

    private fun setItemClickListener() {
        listMovieAdapter.setOnItemClickCallback(this)
    }

    override fun onItemClicked(data: Movie) {
        Toast.makeText(this, "You choose " + data.title, Toast.LENGTH_SHORT).show()
    }
}
