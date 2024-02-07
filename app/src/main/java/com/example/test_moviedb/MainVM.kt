package com.example.test_moviedb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.test_moviedb.db.entities.GenreEnt
import com.example.test_moviedb.db.entities.MovieEnt
import com.example.test_moviedb.db.entities.ReviewEnt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainVM @Inject constructor(
    private val data: MainData,
) : ViewModel() {

    val genres get() = data.getGenreList()

    private val _genre = MutableStateFlow(GenreEnt())
    val genre: Flow<GenreEnt> get() = _genre
    fun updateGenre(genreEnt: GenreEnt) = _genre.update { genreEnt }

    val movies: Flow<PagingData<MovieEnt>> = _genre.filter { it.id > 0 }.flatMapLatest { genre ->
        data.moviePager(genre.id).flow.cachedIn(viewModelScope)
    }

    private val _movie = MutableStateFlow(MovieEnt())
    val movie: Flow<MovieEnt> = _movie
    fun updateMovie(movieEnt: MovieEnt) {
        _movie.update { movieEnt }

        viewModelScope.launch {
            _movie.update { data.getMovieDetail(movieEnt) }
        }
    }

    suspend fun getYoutubeId(movie: MovieEnt) = data.getMovieVideo(movie)

    val reviews: Flow<PagingData<ReviewEnt>> = _movie.filter { it.id > 0 }.flatMapLatest { movie ->
        data.reviewPager(movie.id).flow.cachedIn(viewModelScope)
    }

    init {
        viewModelScope.launch {
            data.fetchGenre()
        }
    }
}