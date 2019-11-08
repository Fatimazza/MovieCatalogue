package io.github.fatimazza.moviecatalogue.widget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import io.github.fatimazza.moviecatalogue.BuildConfig
import io.github.fatimazza.moviecatalogue.R
import io.github.fatimazza.moviecatalogue.database.FavoriteDatabase
import io.github.fatimazza.moviecatalogue.database.FavoriteMovie
import io.github.fatimazza.moviecatalogue.widget.FavoriteStackWidget.Companion.EXTRA_ITEM
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val widgetItems = ArrayList<FavoriteMovie>()

    override fun onCreate() {
        GlobalScope.launch {
            context.let {
                val allFavMovies =
                    FavoriteDatabase.getInstance(it).favoriteDatabaseDao.getAllMoviesForWidget()
                Log.d("Izza", "onCreate " + allFavMovies.size)
                widgetItems.addAll(allFavMovies)
            }
        }
    }

    override fun hasStableIds(): Boolean = false

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(i: Int): Long = 0

    override fun getViewTypeCount(): Int = 1

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.item_favorite_widget)

        val bitmap = Glide
            .with(context)
            .asBitmap()
            .load(BuildConfig.POSTER_BASE_URL + widgetItems[position].moviePosterPath)
            .centerCrop()
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.ivImageWidget, bitmap)
        rv.setTextViewText(R.id.tvTitleWidget, widgetItems[position].movieTitle)

        val extras = Bundle()
        extras.putInt(EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        Log.d("Izza", "create intent ${extras.get(FavoriteStackWidget.EXTRA_ITEM)}")

        rv.setOnClickFillInIntent(R.id.ivImageWidget, fillInIntent)
        return rv
    }

    override fun getCount(): Int = widgetItems.size

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        GlobalScope.launch {
            context.let {
                widgetItems.clear()
                val allFavMovies =
                    FavoriteDatabase.getInstance(it).favoriteDatabaseDao.getAllMoviesForWidget()
                Log.d("Izza", "onDataSetChanged " + allFavMovies.size)
                widgetItems.addAll(allFavMovies)
            }
        }
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {

    }

}
