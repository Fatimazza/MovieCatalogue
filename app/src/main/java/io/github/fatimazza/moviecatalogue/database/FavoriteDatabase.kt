package io.github.fatimazza.moviecatalogue.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [FavoriteMovie::class, FavoriteTv::class], version = 2, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract val favoriteDatabaseDao: FavoriteDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE : FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDatabase::class.java,
                        "favorite_movie_database"
                    ).addMigrations(MIGRATION_1_2).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE fav_movie_table "
                            + " ADD COLUMN movieLang TEXT"
                            + " NOT NULL DEFAULT 'en'"
                )
                database.execSQL(
                    "ALTER TABLE fav_tv_table "
                            + " ADD COLUMN tvLang TEXT"
                            + " NOT NULL DEFAULT 'en'"
                )
            }
        }
    }
}
