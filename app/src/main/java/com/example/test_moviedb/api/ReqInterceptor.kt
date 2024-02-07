package com.example.test_moviedb.api

import com.example.test_moviedb.utils.API_TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ReqInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain
            .request()
            .newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer $API_TOKEN")
            .build()
    )
}