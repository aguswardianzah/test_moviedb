package com.example.test_moviedb.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.test_moviedb.api.ApiService
import com.example.test_moviedb.db.TestDb
import com.example.test_moviedb.db.entities.MovieEnt
import com.example.test_moviedb.db.entities.PagingKeyEnt
import com.example.test_moviedb.utils.BASE_IMAGE_URL

@OptIn(ExperimentalPagingApi::class)
class MoviePagingMediator(
    val genreId: Int,
    val db: TestDb,
    val api: ApiService
) : RemoteMediator<Int, MovieEnt>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEnt>
    ): MediatorResult {
        return try {
            val pagingKey = db.pagingKey().get("movie") ?: PagingKeyEnt(name = "movie")

            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.APPEND -> pagingKey.key + 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            }

            val resp = api.fetchMovies(genreId, page)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) db.movie().clearByGenre(genreId)

                db.pagingKey().insert(pagingKey.apply { key = page })

                db.movie().insert(resp.results.onEach {
                    it.genreId = genreId
                    it.poster = BASE_IMAGE_URL + it.poster
                    it.releaseYear = it.release.substring(0..3)
                })
            }

            MediatorResult.Success(endOfPaginationReached = page >= resp.totalPages)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}