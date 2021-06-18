package io.github.fatimazza.myfavoritemovies.television

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
import io.github.fatimazza.myfavoritemovies.entity.FavoriteTv


class FavoriteTelevisionAdapter :
    RecyclerView.Adapter<FavoriteTelevisionAdapter.ViewHolder>() {

    private var tvShowData = emptyList<FavoriteTv>()

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
        return tvShowData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tvShowData[position], position)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemListMovieBinding.bind(view)
        fun bind(television: FavoriteTv, position: Int) {
            with(view) {
                binding.tvMovieTitleItem.text = television.tvTitle
                binding.tvMovieDescItem.text = if (television.tvOverview.isEmpty())
                    context.getString(R.string.list_movie_description_empty) else television.tvOverview

                Glide.with(view.context)
                    .load(BuildConfig.POSTER_BASE_URL + television.tvPosterPath)
                    .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .placeholder(R.color.colorAccent)
                    .into(binding.ivMovieImageItem)
            }
            view.setOnClickListener { onItemClickCallback.onItemClicked(tvShowData[position]) }
        }
    }

    fun setData(tvShowItems: List<FavoriteTv>) {
        val listFavTvShow = ArrayList<FavoriteTv>()
        for (i in 0 until tvShowItems.size) {
            listFavTvShow.add(tvShowItems[i])
        }
        tvShowData = listFavTvShow
        notifyDataSetChanged()
    }

    fun getData(): List<FavoriteTv> = tvShowData

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteTv)
    }
}
