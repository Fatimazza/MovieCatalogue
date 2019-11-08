package io.github.fatimazza.moviecatalogue.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import io.github.fatimazza.moviecatalogue.R

class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val widgetItems = ArrayList<Bitmap>()

    override fun onCreate() {

    }

    override fun hasStableIds(): Boolean = false

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(i: Int): Long = 0

    override fun getViewTypeCount(): Int = 1

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.item_favorite_widget)
        rv.setImageViewBitmap(R.id.ivImageWidget, widgetItems[position])

        val extras = bundleOf(FavoriteStackWidget.EXTRA_ITEM to position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        Log.d("Izza", "create intent ${extras.get(FavoriteStackWidget.EXTRA_ITEM)}")

        rv.setOnClickFillInIntent(R.id.ivImageWidget, fillInIntent)
        return rv
    }

    override fun getCount(): Int = widgetItems.size

    override fun onDataSetChanged() {
        widgetItems.add(
            BitmapFactory.decodeResource(context.resources, R.drawable.starwars_darth_vader)
        )
        widgetItems.add(
            BitmapFactory.decodeResource(context.resources, R.drawable.starwars_falcon)
        )
        widgetItems.add(
            BitmapFactory.decodeResource(context.resources, R.drawable.starwars_storm_trooper)
        )
    }

    override fun onDestroy() {

    }

}
