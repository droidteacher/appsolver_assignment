package hu.zsoltkiss.moviefacts.data.persistence.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.zsoltkiss.moviefacts.data.model.MovieListItem
import java.util.Date

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @NonNull
    val tmdbId: Int,
    val title: String,
    val favorite: Boolean,
    val releaseDate: String,
    val popularity: Float,
    val updatedAt: Long = Date().time

) {

    constructor(remoteData: MovieListItem): this(
        remoteData.id!!,
        remoteData.originalTitle!!,
        false,
        remoteData.releaseDate!!,
        remoteData.popularity!!,
        Date().time
    ) {

    }


}