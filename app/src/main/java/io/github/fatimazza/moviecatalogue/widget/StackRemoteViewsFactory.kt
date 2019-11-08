package io.github.fatimazza.moviecatalogue.widget

import android.widget.RemoteViews
import android.widget.RemoteViewsService

class StackRemoteViewsFactory : RemoteViewsService.RemoteViewsFactory {

    override fun onCreate() {

    }

    override fun hasStableIds(): Boolean = false

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(i: Int): Long = 0

    override fun getViewTypeCount(): Int = 1

    override fun getViewAt(position: Int): RemoteViews {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDataSetChanged() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {

    }

}
