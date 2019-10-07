package io.github.fatimazza.moviecatalogue

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import io.github.fatimazza.moviecatalogue.model.Movie
import io.github.fatimazza.moviecatalogue.model.MoviesData
import kotlinx.android.synthetic.main.activity_list_movie.*

class ListMovieActivity : AppCompatActivity() {

    private val listMovie: ListView
    get() = lv_movie

    private var list: ArrayList<Movie> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_movie)

        setupListMovieAdapter(this)
        setItemClickListener(listMovie)
    }

    private fun setupListMovieAdapter(context: Context) {
        list.addAll(MoviesData.listData)

        val adapter = ListMovieAdapter(context, list)
        listMovie.adapter = adapter
    }

    private fun setItemClickListener(listMovie: ListView) {
        listMovie.setOnItemClickListener(
            object : AdapterView.OnItemClickListener{
                override fun onItemClick(adapterView: AdapterView<*>?, view: View?, index: Int, l: Long) {
                    Toast.makeText(this@ListMovieActivity, list[index].title, Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}
