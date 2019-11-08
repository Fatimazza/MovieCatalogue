package io.github.fatimazza.moviecatalogue.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

import io.github.fatimazza.moviecatalogue.R

/**
 * Implementation of App Widget functionality.
 */
class FavoriteStackWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
    }

    companion object {

        private const val TOAST_ACTION = "io.github.fatimazza.moviecatalogue.widget.TOAST_ACTION"
        const val EXTRA_ITEM = "io.github.fatimazza.moviecatalogue.widget.EXTRA_ITEM"

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val widgetText = context.getString(R.string.widget_fav_title)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.favorite_stack_widget)
            views.setTextViewText(R.id.empty_view, widgetText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

