package hu.zsoltkiss.moviefacts.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hu.zsoltkiss.moviefacts.data.persistence.entity.Movie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun persistAll(items: List<Movie>)

    @Query("select * from movies")
    suspend fun fetchAll(): List<Movie>

    @Query("update movies set favorite = :value where tmdbId = :movieId")
    suspend fun updateFavorite(movieId: Int, value: Int)

    @Update
    fun update(movie: Movie)
}