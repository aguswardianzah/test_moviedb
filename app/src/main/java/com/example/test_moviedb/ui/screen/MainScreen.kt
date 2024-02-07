package com.example.test_moviedb.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.test_moviedb.MainVM
import com.example.test_moviedb.db.entities.GenreEnt
import com.example.test_moviedb.db.entities.MovieEnt

private const val genreIdArg = "genreId"
private const val movieIdArg = "movieId"

@Composable
fun MainScreen(onClickTrailer: (MovieEnt) -> Unit, vm: MainVM = hiltViewModel()) {
    val navController = rememberNavController()

    val genres = vm.genres.collectAsState(initial = emptyList())
    val genre = vm.genre.collectAsState(initial = GenreEnt())
    val movies = vm.movies.collectAsLazyPagingItems()
    val movie = vm.movie.collectAsState(initial = MovieEnt())
    val reviews = vm.reviews.collectAsLazyPagingItems()

    NavHost(navController = navController, startDestination = "genre") {
        composable("genre") {
            GenreScreen(genres.value) {
                vm.updateGenre(it)
                navController.navigate("movie")
            }
        }

        composable("movie") {
            MovieScreen(
                navController = navController,
                genre = genre.value,
                movies = movies
            ) {
                vm.updateMovie(it)
                navController.navigate("detail")
            }
        }

        composable("detail") {
            DetailScreen(
                navController = navController,
                movie = movie.value,
                onClickReview = { navController.navigate("review") },
                onClickTrailer = onClickTrailer
            )
        }

        composable("review") {
            ReviewScreen(navController, reviews)
        }
    }
}