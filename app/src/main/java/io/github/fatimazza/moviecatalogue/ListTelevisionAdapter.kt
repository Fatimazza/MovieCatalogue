package io.github.fatimazza.moviecatalogue


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import io.github.fatimazza.moviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.item_list_movie.view.*

class ListTelevisionAdapter(val context: Context, val listTelevision: ArrayList<TvShow>) :
    BaseAdapter() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItem(i: Int): Any {
        return listTelevision[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return listTelevision.size
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val viewLayout =
            LayoutInflater.from(context).inflate(R.layout.item_list_movie, viewGroup, false)

        val viewHolder = ViewHolder(viewLayout)
        val television = getItem(i) as TvShow
        viewHolder.bind(context, television, i)
        return viewLayout
    }

    private inner class ViewHolder(private val view: View) {
        fun bind(context: Context, television: TvShow, position: Int) {
            with(view) {
                tv_movie_title_item.text = television.title
                tv_movie_desc_item.text = television.description

                Glide.with(context)
                    .load(television.poster)
                    .into(iv_movie_image_item)
            }
            view.setOnClickListener { onItemClickCallback.onItemClicked(listTelevision[position]) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }
}
