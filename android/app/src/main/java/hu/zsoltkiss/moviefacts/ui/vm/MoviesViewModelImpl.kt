package hu.zsoltkiss.moviefacts.ui.vm

import android.net.ConnectivityManager
import android.net.Network
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import hu.zsoltkiss.moviefacts.BuildConfig
import hu.zsoltkiss.moviefacts.app.TheApp
import hu.zsoltkiss.moviefacts.data.api.MoviesApi
import hu.zsoltkiss.moviefacts.data.persistence.MoviesDb
import hu.zsoltkiss.moviefacts.data.persistence.entity.Movie
import hu.zsoltkiss.moviefacts.data.service.ConnectivityManagerWrapper
import hu.zsoltkiss.moviefacts.util.debugPrint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesViewModelImpl : ViewModel(), MoviesViewModel {

    private var apiService: MoviesApi

    override val processing: MutableState<Boolean> = mutableStateOf(false)
    override val offline: MutableState<Boolean> = mutableStateOf(true)
    override val shouldShowDialog: MutableState<Boolean> = mutableStateOf(true)

    override val moviesFetched: MutableState<List<Movie>> = mutableStateOf(emptyList())

    private var moviesDao = MoviesDb.getDaoInstance(TheApp.getAppContext())

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.IO)


    init {
        val tmdbUrl = BuildConfig.TMDB_BASE_URL

        val retrofit: Retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
            tmdbUrl).build()
        apiService = retrofit.create(MoviesApi::class.java)

        ConnectivityManagerWrapper.getInstance().subscribe(object:
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                offline.value = false
                debugPrint("Connectivity status: ONLINE", "__VM__", 9999)
                if (moviesFetched.value.isEmpty()) {
                    getPopularMovies()
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                shouldShowDialog.value = true
                offline.value = true
                debugPrint("Connectivity status: OFFLINE", "__VM__", 9999)
            }
        })
    }

    override fun getPopularMovies() {
        processing.value = true
        scope.launch {
            updateMoviesFromRemote()

            val localMovies = moviesDao.fetchAll()
            localMovies.filter { someMovie -> someMovie.favorite }

            withContext(Dispatchers.Main) {
                localMovies.let {
                    moviesFetched.value = it
                }

                processing.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
        ConnectivityManagerWrapper.getInstance().unsubscribe()
    }

    private suspend fun updateMoviesFromRemote() {
        if (!offline.value) {
            val tmdbApiKey = BuildConfig.TMDB_API_KEY
            val response = apiService.getPopularMovies(apiKey = tmdbApiKey)
            val movieInstances = response.results?.map { anItem -> Movie(remoteData = anItem) }
            movieInstances?.let {
                moviesDao.persistAll(it)
            }
        }
    }


    override fun updateFavoriteAtMovie(movieId: Int, favorite: Boolean) {
        scope.launch {
            val dbValue = when {
                favorite -> 1
                    else -> 0
            }
            moviesDao.updateFavorite(movieId, dbValue)
        }
    }

    override fun takeNoteNetworkWarning() {
        shouldShowDialog.value = false
    }
}