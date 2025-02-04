package hu.zsoltkiss.moviefacts.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.zsoltkiss.moviefacts.data.persistence.dao.MoviesDao
import hu.zsoltkiss.moviefacts.data.persistence.entity.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesDb : RoomDatabase() {
    abstract val dao: MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDao? = null

        fun getDaoInstance(context: Context): MoviesDao {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = buildDatabase(context).dao
                    INSTANCE = instance
                }
                return instance
            }
        }

        private fun buildDatabase(context: Context): MoviesDb = Room.databaseBuilder(
            context.applicationContext,
            MoviesDb::class.java,
            "movies_database"
        ).build()
    }
}