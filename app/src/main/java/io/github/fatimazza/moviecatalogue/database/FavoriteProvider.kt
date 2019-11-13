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
    val uriMatcher: UriMatcher

    companion object {
        const val MOVIE = 100
        const val MOVIE_ID = 102
        const val TV = 101
        const val TV_ID = 103
        val CONTENT_URI1 = Uri.parse("content://${BuildConfig.PROVIDER_NAME}/movie")
        val CONTENT_URI2 = Uri.parse("content://${BuildConfig.PROVIDER_NAME}/tv")
    }

    init {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH);

        // content://io.github.fatimazza.moviecatalogue/movie
        uriMatcher.addURI(BuildConfig.PROVIDER_NAME, "movie", MOVIE);
        // content://io.github.fatimazza.moviecatalogue/movie/id
        uriMatcher.addURI(BuildConfig.PROVIDER_NAME, "movie/#", MOVIE_ID);

        uriMatcher.addURI(BuildConfig.PROVIDER_NAME, "tv", TV);
        uriMatcher.addURI(BuildConfig.PROVIDER_NAME, "tv/#", TV_ID);
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
            MOVIE -> {
                return favoriteDao.getAllMoviesCursor()
            }
            TV -> {
                return favoriteDao.getAllTvShowsCursor()
            }
            MOVIE_ID -> {
                return favoriteDao.getFavMovieCursor(uri.lastPathSegment!!.toLong())
            }
            TV_ID -> {
                return favoriteDao.getFavTvShowCursor(uri.lastPathSegment!!.toLong())
            }
            else -> {
                throw SQLException("Failed insert rom into $uri")
            }
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var _uri: Uri? = null
        when (uriMatcher.match(uri)) {
            MOVIE_ID -> {
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

                    context?.contentResolver?.notifyChange(CONTENT_URI1, null)
                    _uri = ContentUris.withAppendedId(uri, id)
                }
            }
            TV_ID -> {
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

                    context?.contentResolver?.notifyChange(CONTENT_URI2, null)
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
            MOVIE_ID -> {
                favoriteDao.deleteFavMovie(uri.lastPathSegment?.toLong() ?: 0)
                return 0
            }
            TV_ID -> {
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

