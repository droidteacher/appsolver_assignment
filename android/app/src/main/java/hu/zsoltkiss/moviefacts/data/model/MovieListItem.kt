package hu.zsoltkiss.moviefacts.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieListItem(
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null,

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Float? = null,

    @SerializedName("TITLE")
    @Expose
    var title: String? = null,

    @SerializedName("popularity")
    @Expose
    var popularity: Float? = null,


    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null,


    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null,
)