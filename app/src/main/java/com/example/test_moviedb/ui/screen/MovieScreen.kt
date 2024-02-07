package com.example.test_moviedb.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.test_moviedb.db.entities.GenreEnt
import com.example.test_moviedb.db.entities.MovieEnt
import com.example.test_moviedb.ui.component.BackButton
import com.example.test_moviedb.ui.component.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    navController: NavController,
    genre: GenreEnt,
    movies: LazyPagingItems<MovieEnt>,
    onClickMovie: (MovieEnt) -> Unit
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    navController.previousBackStackEntry?.let {
                        BackButton(navController::popBackStack)
                    }
                },
                title = { Text("${genre.name} Movies") },
            )
        },
    ) { padding ->
        LazyVerticalGrid(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(
                count = movies.itemCount,
                key = movies.itemKey { it.id },
                contentType = movies.itemContentType { "movieItem" }
            ) {
                movies[it]?.let { movie ->
                    MovieCard(movie = movie, onClick = { onClickMovie(movie) })
                }
            }

            // initial/refresh loading
            when (val refresh = movies.loadState.refresh) {
                is LoadState.Error -> {
                    item(span = { GridItemSpan(2) }) {
                        Surface(
                            color = MaterialTheme.colorScheme.error,
                            shadowElevation = 2.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                "Failed to load - ${refresh.error}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                is LoadState.Loading -> {
                    item(span = { GridItemSpan(2) }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                "Refresh Loading",
                                modifier = Modifier.padding(8.dp),
                            )

                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                is LoadState.NotLoading -> {
                    // show message when movies empty
                    if (movies.itemCount == 0) {
                        item(span = { GridItemSpan(2) }) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    "Not Found",
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }

            // next page loading indicator
            if (movies.loadState.append == LoadState.Loading) {
                item(span = { GridItemSpan(2) }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            "Loading Next",
                            modifier = Modifier.padding(8.dp),
                        )

                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}