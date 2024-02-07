package com.example.test_moviedb

import android.content.Context
import android.widget.Toast
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.withTransaction
import com.example.test_moviedb.api.ApiService
import com.example.test_moviedb.db.TestDb
import com.example.test_moviedb.db.entities.MovieEnt
import com.example.test_moviedb.paging.MoviePagingMediator
import com.example.test_moviedb.paging.ReviewPagingMediator
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MainData @Inject constructor(
    @ApplicationContext val context: Context,
    val db: TestDb,
    val api: ApiService
) {

    fun getGenreList() = db.genre().get()

    suspend fun fetchGenre() = try {
        with(api.fetchGenre()) {
            db.withTransaction {
                if (genres.isNotEmpty()) db.genre().clear()

                db.genre().insert(genres)
            }
        }
    } catch (e: Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }

    fun moviePager(genreId: Int) =
        Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MoviePagingMediator(genreId, db, api)
        ) {
            db.movie().get(genreId)
        }

    suspend fun getMovieDetail(movie: MovieEnt): MovieEnt {
        try {
            val resp = api.fetchDetail(movie.id)
            db.movie().insert(movie.apply {
                tagline = resp.tagline
            })
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }

        return movie
    }

    suspend fun getMovieVideo(movie: MovieEnt): MovieEnt {
        if (movie.youtubeTrailerId.isEmpty()) {
            try {
                val resp = api.fetchVideo(movie.id)
                db.movie().insert(movie.apply {
                    youtubeTrailerId =
                        resp
                            .results
                            .firstOrNull {
                                it.site.lowercase(Locale.getDefault()) == "youtube"
                                        && it.type.lowercase(Locale.getDefault()) == "trailer"
                            }?.key
                            ?: ""
                })
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        return movie
    }

    fun reviewPager(movieId: Int) =
        Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ReviewPagingMediator(movieId, db, api)
        ) {
            db.review().get(movieId)
        }
}