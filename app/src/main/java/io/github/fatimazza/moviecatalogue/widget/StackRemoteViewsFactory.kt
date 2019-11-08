package io.github.fatimazza.moviecatalogue.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int = widgetItems.size

    override fun onDataSetChanged() {
        widgetItems.add(
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_movie)
        )
        widgetItems.add(
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_favorites)
        )
        widgetItems.add(
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_television)
        )
    }

    override fun onDestroy() {

    }

}
