package io.github.fatimazza.moviecatalogue


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.github.fatimazza.moviecatalogue.model.TvShowResponse
import kotlinx.android.synthetic.main.item_list_movie.view.*

class ListTelevisionAdapter :
    RecyclerView.Adapter<ListTelevisionAdapter.ViewHolder>() {

    private val tvShowData = ArrayList<TvShowResponse>()

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
        fun bind(television: TvShowResponse, position: Int) {
            with(view) {
                tv_movie_title_item.text = television.name
                tv_movie_desc_item.text = if (television.overview.isEmpty())
                    context.getString(R.string.list_movie_description_empty) else television.overview

                Glide.with(view.context)
                    .load(BuildConfig.POSTER_BASE_URL + television.poster_path)
                    .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .placeholder(R.color.colorAccent)
                    .into(iv_movie_image_item)
            }
            view.setOnClickListener { onItemClickCallback.onItemClicked(tvShowData[position]) }
        }
    }

    fun setData(tvShowItems: ArrayList<TvShowResponse>) {
        tvShowData.clear()
        tvShowData.addAll(tvShowItems)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShowResponse)
    }
}
