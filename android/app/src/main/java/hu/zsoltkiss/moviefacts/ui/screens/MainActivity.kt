package hu.zsoltkiss.moviefacts.ui.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hu.zsoltkiss.moviefacts.R
import hu.zsoltkiss.moviefacts.ui.theme.MovieFactsTheme
import hu.zsoltkiss.moviefacts.ui.theme.Purple80
import hu.zsoltkiss.moviefacts.ui.theme.genericBackground
import hu.zsoltkiss.moviefacts.ui.theme.noResults
import hu.zsoltkiss.moviefacts.ui.vm.MoviesViewModelImpl
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

const val KZS_FLUTTER_ENGINE_NAME = "kzs_flutter_engine"
const val MOVIE_FACTS_CHANNEL = "hu.zsoltkiss.moviefacts/channel"

class MainActivity : ComponentActivity() {
    private val moviesViewModel: MoviesViewModelImpl by viewModels()

    private var selectedMovieId: Int? = null
    private var isSelectedFavorite: Boolean? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MovieFactsTheme {
                if (moviesViewModel.offline.value && moviesViewModel.shouldShowDialog.value) {
                    NoNetworkDialog(onDismiss = { moviesViewModel.takeNoteNetworkWarning() })
                }
                
                Scaffold(
                    containerColor = genericBackground,
                    topBar = {
                        MFAppBar(stringResource(id = R.string.popular_movies))
                    },

                    content = {
                        val movies by moviesViewModel.moviesFetched
                        val processing by moviesViewModel.processing

                        Column(modifier = Modifier.padding(it)) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (processing) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(top = 50.dp), color = Purple80
                                    )
                                } else {
                                    if (movies.isEmpty()) {
                                        Text(
                                            text = "No results",
                                            style = noResults,
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .padding(top = 50.dp)
                                        )
                                    } else {
                                        LazyColumn(
                                            modifier = Modifier.align(Alignment.Center)
                                        ) {

                                            moviesList(
                                                movies = movies,
                                                onCardSelect = ::loadDetails
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        moviesViewModel.getPopularMovies()
    }

    private fun loadDetails(someMovieId: Int, locallyFavorite: Boolean) {
        if (moviesViewModel.offline.value) {
            Toast.makeText(this, "Device is offline", Toast.LENGTH_LONG).show()
        } else {
            selectedMovieId = someMovieId
            isSelectedFavorite = locallyFavorite

            configureMethodChanel()
            startActivity(FlutterActivity.withCachedEngine(KZS_FLUTTER_ENGINE_NAME).build(this))
        }

    }

    private fun configureMethodChanel() {
        val flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        val flutterEngineCache = FlutterEngineCache.getInstance()
        flutterEngineCache.put(KZS_FLUTTER_ENGINE_NAME, flutterEngine)

        val methodChanel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, MOVIE_FACTS_CHANNEL)

        methodChanel.setMethodCallHandler { call, result ->

            when(call.method) {
                "getSelectedMovieId" -> {
                    val payload = mapOf("movieId" to selectedMovieId, "favorite" to isSelectedFavorite)
                    result.success(payload)
                }

                "toggleFavorite" -> {
                    val args = call.arguments<Map<String, Any>>()
                    moviesViewModel.updateFavoriteAtMovie(args!!["movieId"] as Int, args["isFavorite"] as Boolean)
                }
            }
        }
    }
}

