package io.github.fatimazza.moviecatalogue.database

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.net.Uri
import io.github.fatimazza.moviecatalogue.BuildConfig

class FavoriteProvider : ContentProvider() {

    lateinit var favoriteDao: FavoriteDatabaseDao

    companion object {

        private const val AUTHORITY = BuildConfig.PROVIDER_NAME
        private const val DATABASE = "favorite_movie_database"
        private const val CONTENT_TYPE_MOVIE = 1
        private const val CONTENT_TYPE_TV = 2

        private const val FAVORITE_MOVIE = "fav_movie_table"
        private const val FAVORITE_TELEVISION = "fav_tv_table"

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, "$DATABASE/$FAVORITE_MOVIE", CONTENT_TYPE_MOVIE)
            uriMatcher.addURI(AUTHORITY, "$DATABASE/$FAVORITE_TELEVISION", CONTENT_TYPE_TV)
        }
    }

    override fun onCreate(): Boolean {
        favoriteDao = FavoriteDatabase.getInstance(context!!).favoriteDatabaseDao
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        when (uriMatcher.match(uri)) {
            CONTENT_TYPE_MOVIE -> {
                return favoriteDao.getAllMoviesCursor()
            }
            CONTENT_TYPE_TV -> {
                return favoriteDao.getAllTvShowsCursor()
            }
            else -> {
                throw SQLException("Failed insert rom into $uri")
            }
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var _uri: Uri? = null
        when (uriMatcher.match(uri)) {
            CONTENT_TYPE_MOVIE -> {
                var id = 0L
                values?.apply {
                    val data = FavoriteMovie(
                        0,
                        getAsString("movieId"),
                        getAsString("movieTitle"),
                        getAsString("movieOverview"),
                        getAsString("movieVoteAverage"),
                        getAsString("movieReleaseDate"),
                        getAsString("moviePosterPath"),
                        getAsString("movieLang")
                    )
                    id = favoriteDao.insertMovieCursor(data)

                    //context?.contentResolver?.notifyChange(CONTENT_TYPE_MOVIE, null)
                    _uri = ContentUris.withAppendedId(uri, id)
                }
            }
            CONTENT_TYPE_TV -> {
                var id = 0L
                values?.apply {
                    val data = FavoriteTv(
                        0,
                        getAsString("tvId"),
                        getAsString("tvTitle"),
                        getAsString("tvOverview"),
                        getAsString("tvVoteAverage"),
                        getAsString("tvReleaseDate"),
                        getAsString("tvPosterPath"),
                        getAsString("tvLang")
                    )

                    id = favoriteDao.insertTvShowCursor(data)

                    //context?.contentResolver?.notifyChange(CONTENT_URI2, null)
                    _uri = ContentUris.withAppendedId(uri, id)
                }
            }
            else -> {
                throw SQLException("Failed insert rom into $uri")
            }
        }
        return _uri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        //TODO return
        when (uriMatcher.match(uri)) {
            CONTENT_TYPE_MOVIE -> {
                favoriteDao.deleteFavMovie(uri.lastPathSegment?.toLong() ?: 0)
                return 0
            }
            CONTENT_TYPE_TV -> {
                favoriteDao.deleteFavTvShow(uri.lastPathSegment?.toLong() ?: 0)
                return 0
            }
        }
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}

