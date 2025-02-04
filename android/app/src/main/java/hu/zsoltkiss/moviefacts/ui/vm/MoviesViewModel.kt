package hu.zsoltkiss.moviefacts.ui.vm


import androidx.compose.runtime.MutableState
import hu.zsoltkiss.moviefacts.data.persistence.entity.Movie

interface MoviesViewModel {
    val processing: MutableState<Boolean>
    val offline: MutableState<Boolean>
    val shouldShowDialog: MutableState<Boolean>
    val moviesFetched: MutableState<List<Movie>>

    fun getPopularMovies()
    fun updateFavoriteAtMovie(movieId: Int, favorite: Boolean)
    fun takeNoteNetworkWarning()

}