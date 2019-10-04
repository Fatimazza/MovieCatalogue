package io.github.fatimazza.moviecatalogue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import io.github.fatimazza.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.item_list_movie.view.*

class ListMovieAdapter(val context: Context, val listMovie: ArrayList<Movie>) : BaseAdapter() {

    override fun getItem(i: Int): Any {
        return listMovie[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return listMovie.size
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val viewLayout = LayoutInflater.from(context).inflate(R.layout.item_list_movie, viewGroup, false)

        val viewHolder = ViewHolder(viewLayout)
        val movie = getItem(i) as Movie
        viewHolder.bind(context, movie)
        return viewLayout
    }

    private inner class ViewHolder(private val view: View) {
        fun bind(context: Context, movie: Movie) {
            with(view) {
                tv_movie_title_item.text = movie.title
                tv_movie_desc_item.text = movie.description

                Glide.with(context)
                    .load(movie.poster)
                    .into(iv_movie_image_item)
            }
        }
    }
}
