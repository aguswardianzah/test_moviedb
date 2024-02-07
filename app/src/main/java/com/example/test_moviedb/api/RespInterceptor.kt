package com.example.test_moviedb.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject

class RespInterceptor @Inject constructor(moshi: Moshi) : Interceptor {

    private val errorAdapter by lazy {
        moshi.adapter<Map<String, Any>>(
            Types.newParameterizedType(
                Map::class.java,
                String::class.java,
                Any::class.java
            )
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val responseCode = response.code
        val responseBody = response.body

        return try {
            val content =
                responseBody?.source()?.also { it.request(Long.MAX_VALUE) }?.buffer?.clone()
                    ?.readString(Charset.forName("UTF-8"))
                    ?: "Request Failed"

            if ((responseCode / 100) != 2) {
                val contentMap: Map<String, Any> = try {
                    errorAdapter.fromJson(content) ?: emptyMap()
                } catch (e: Exception) {
                    emptyMap()
                }

                val msg = contentMap["status_message"] as? String ?: "Request Failed."

                responseBody?.close()
                throw IOException(msg)
            }

            response
        } catch (e: Exception) {
            responseBody?.close()
            Timber.e(e)
            throw e
        }
    }
}