package com.example.test_moviedb.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.test_moviedb.db.entities.MovieEnt
import com.example.test_moviedb.ui.component.BackButton
import com.example.test_moviedb.ui.component.Rating
import com.example.test_moviedb.ui.theme.Test_moviedbTheme
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    movie: MovieEnt,
    onClickReview: (MovieEnt) -> Unit,
    onClickTrailer: (MovieEnt) -> Unit
) {
    val state = rememberCollapsingToolbarScaffoldState(toolbarState = CollapsingToolbarState())

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            LargeTopAppBar(
                navigationIcon = {
                    navController.previousBackStackEntry?.let {
                        BackButton(navController::popBackStack)
                    }
                },
                title = {
                    Text(
                        "${movie.title} (${movie.releaseYear})",
                        modifier = Modifier.graphicsLayer {
                            alpha = 1 - state.toolbarState.progress
                        }
                    )
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { alpha = state.toolbarState.progress }
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current).data(movie.poster)
                        .crossfade(true)
                        .build(),
                    contentDescription = movie.title,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    "${movie.title} (${movie.releaseYear})",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Rating(
                    rate = String.format("%.1f", movie.vote),
                )
            }

            item {
                Button(onClick = { onClickTrailer(movie) }) {
                    Text("Watch Trailer")
                }
            }

            item {
                Button(onClick = { onClickReview(movie) }) {
                    Text("See Reviews")
                }
            }

            item {
                Text(movie.tagline, fontStyle = FontStyle.Italic)
            }

            item {
                Text(movie.overview)
            }
        }
    }
}

@Preview
@Composable
fun PrevDetailScreen() {
    Test_moviedbTheme {
        DetailScreen(
            navController = NavController(LocalContext.current),
            movie = MovieEnt(),
            onClickReview = {},
            onClickTrailer = {}
        )
    }
}