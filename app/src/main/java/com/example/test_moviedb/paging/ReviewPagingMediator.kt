package com.example.test_moviedb.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.test_moviedb.api.ApiService
import com.example.test_moviedb.db.TestDb
import com.example.test_moviedb.db.entities.PagingKeyEnt
import com.example.test_moviedb.db.entities.ReviewEnt
import com.example.test_moviedb.utils.BASE_IMAGE_URL
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalPagingApi::class)
class ReviewPagingMediator(
    private val movieId: Int,
    val db: TestDb,
    val api: ApiService
) : RemoteMediator<Int, ReviewEnt>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReviewEnt>
    ): MediatorResult {
        return try {
            val pagingKey =
                db.pagingKey().get("review-$movieId") ?: PagingKeyEnt(name = "review-$movieId")

            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.APPEND -> pagingKey.key + 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            }

            val resp = api.fetchReview(movieId, page)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) db.review().clearByMovie(movieId)

                db.pagingKey().insert(pagingKey.apply { key = page })

                val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                val dateParse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                db.review().insert(resp.results.map {
                    ReviewEnt(
                        ids = it.id,
                        movieId = movieId,
                        author = it.author,
                        content = it.content,
                        createdAt = dateParse.parse(it.createdAt)?.let { it1 ->
                            dateFormat.format(it1)
                        } ?: it.createdAt,
                        photo = it.authorDetail.avatar?.let { BASE_IMAGE_URL + it } ?: ""
                    )
                })
            }

            MediatorResult.Success(endOfPaginationReached = page >= resp.totalPages)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}