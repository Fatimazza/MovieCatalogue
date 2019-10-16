package io.github.fatimazza.moviecatalogue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.fatimazza.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.item_list_movie.view.*

class ListMovieAdapter(val listMovie: ArrayList<Movie>) :
    RecyclerView.Adapter<ListMovieAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMovie[position], position)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie, position: Int) {
            with(view) {
                tv_movie_title_item.text = movie.title
                tv_movie_desc_item.text = movie.description

                Glide.with(view.context)
                    .load(movie.poster)
                    .into(iv_movie_image_item)
            }
            view.setOnClickListener { onItemClickCallback.onItemClicked(listMovie[position]) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }
}
