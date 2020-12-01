package com.example.chartstest.networking

import com.example.chartstest.ChartApplication.Companion.monthlyDataJson
import com.example.chartstest.ChartApplication.Companion.weeklyDataJson
import com.example.chartstest.utils.Constants.Companion.BASE_URL
import com.example.chartstest.utils.Constants.Companion.monthEndpoint
import com.example.chartstest.utils.Constants.Companion.weekEndpoint
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Class intended to send mocked data as response
 */
class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        val responseString = when {
            uri.endsWith(weekEndpoint) -> {
                weeklyDataJson ?: ""
            }
            uri.endsWith(monthEndpoint) -> {
                monthlyDataJson ?: ""
            }
            else -> ""
        }

        return chain.proceed(chain.request().newBuilder().url(BASE_URL).build())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                responseString.toByteArray().toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()
    }

}