package io.github.fatimazza.myfavoritemovies.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.github.fatimazza.myfavoritemovies.BuildConfig
import io.github.fatimazza.myfavoritemovies.R
import io.github.fatimazza.myfavoritemovies.databinding.ItemListMovieBinding
import io.github.fatimazza.myfavoritemovies.entity.FavoriteMovie

class FavoriteMovieAdapter :
    RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>() {

    private var movieData = emptyList<FavoriteMovie>()

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
        return movieData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieData[position], position)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemListMovieBinding.bind(view)
        fun bind(movie: FavoriteMovie, position: Int) {
            with(view) {
                binding.tvMovieTitleItem.text = movie.movieTitle
                binding.tvMovieDescItem.text = if (movie.movieOverview.isEmpty())
                    context.getString(R.string.list_movie_description_empty) else movie.movieOverview

                Glide.with(view.context)
                    .load(BuildConfig.POSTER_BASE_URL + movie.moviePosterPath)
                    .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .placeholder(R.color.colorAccent)
                    .into(binding.ivMovieImageItem)
            }
            view.setOnClickListener { onItemClickCallback.onItemClicked(movieData[position]) }
        }
    }

    fun setData(movieItems: List<FavoriteMovie>) {
        val listFavMovie = ArrayList<FavoriteMovie>()
        for (i in 0 until movieItems.size) {
            listFavMovie.add(movieItems[i])
        }
        movieData = listFavMovie
        notifyDataSetChanged()
    }

    fun getData(): List<FavoriteMovie> = movieData

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteMovie)
    }
}
