package io.github.fatimazza.myfavoritemovies.database

import android.content.Context
import android.net.Uri
import io.github.fatimazza.myfavoritemovies.entity.FavoriteMovie
import io.github.fatimazza.myfavoritemovies.entity.FavoriteTv

class FavoriteDao(private val context: Context) {

    fun getFavoriteMovieList(): List<FavoriteMovie>? {
        val cursor = context.contentResolver.query(
            Uri.parse(DatabaseContract.MOVIE_CONTENT_URI.toString()),
            DatabaseContract.MOVIE_PROJECTION_MAP,
            null,
            null,
            null
        )
        cursor?.let {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                val movies = mutableListOf<FavoriteMovie>()
                do {
                    movies.add(
                        FavoriteMovie(
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MOVIE_PROJECTION_MAP[0])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MOVIE_PROJECTION_MAP[1])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MOVIE_PROJECTION_MAP[2])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MOVIE_PROJECTION_MAP[3])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MOVIE_PROJECTION_MAP[4])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MOVIE_PROJECTION_MAP[5])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MOVIE_PROJECTION_MAP[6]))
                        )
                    )
                } while (cursor.moveToNext())
                return movies
            }
            cursor.close()
        }
        return null
    }

    fun getFavoriteTvList(): List<FavoriteTv>? {
        val cursor = context.contentResolver.query(
            Uri.parse(DatabaseContract.TV_CONTENT_URI.toString()),
            DatabaseContract.TV_PROJECTION_MAP,
            null,
            null,
            null
        )
        cursor?.let {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                val tvs = mutableListOf<FavoriteTv>()
                do {
                    tvs.add(
                        FavoriteTv(
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TV_PROJECTION_MAP[0])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TV_PROJECTION_MAP[1])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TV_PROJECTION_MAP[2])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TV_PROJECTION_MAP[3])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TV_PROJECTION_MAP[4])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TV_PROJECTION_MAP[5])),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TV_PROJECTION_MAP[6]))
                        )
                    )
                } while (cursor.moveToNext())
                return tvs
            }
            cursor.close()
        }
        return null
    }
}
