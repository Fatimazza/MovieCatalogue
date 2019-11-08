package io.github.fatimazza.moviecatalogue.widget

import android.content.Intent
import android.widget.RemoteViewsService

class FavoriteStackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}
