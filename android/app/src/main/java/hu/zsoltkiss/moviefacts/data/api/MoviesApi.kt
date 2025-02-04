package hu.zsoltkiss.moviefacts.data.api

import hu.zsoltkiss.moviefacts.data.model.MoviesListResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MoviesApi {

    @Headers("Accept: application/json")
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MoviesListResponse

}