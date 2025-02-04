package hu.zsoltkiss.moviefacts.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hu.zsoltkiss.moviefacts.data.persistence.entity.Movie
import hu.zsoltkiss.moviefacts.ui.theme.Purple80
import hu.zsoltkiss.moviefacts.ui.theme.cardTitleStyle
import hu.zsoltkiss.moviefacts.ui.theme.popularityIconTintColor


fun LazyListScope.moviesList(
    movies: List<Movie>,
    onCardSelect: (Int, Boolean) -> Unit
) {
    items(movies) { movie ->
        MovieCard(
            movieId = movie.tmdbId,
            title = movie.title,
            releaseDate = movie.releaseDate,
            popularity = movie.popularity,
            isFavorite = movie.favorite,
            onSelect = onCardSelect
        )
    }
}

@Composable
fun MovieCard(
    movieId: Int,
    title: String,
    releaseDate: String?,
    popularity: Float?,
    isFavorite: Boolean,
    onSelect: (Int, Boolean) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Purple80
        ),
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                onSelect(movieId, isFavorite)
            }
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = cardTitleStyle,
                    modifier = Modifier.weight(0.7f)
                )

                Row(
                    modifier = Modifier.weight(0.3f)
                ) {
                    Icon(Icons.Filled.Star , contentDescription = "Star icon", tint = popularityIconTintColor)
                    Text(text = popularity?.toString() ?: "")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = releaseDate ?: "", modifier = Modifier.weight(0.3f))
                Spacer(modifier = Modifier.weight(0.4f))
                if (isFavorite) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favorite icon", tint = Color.Red )
                }

            }


        }
    }
}




