package com.wahyuhw.authify.data.util

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class HeaderInterceptor(
    private val headers: HashMap<String, String>
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return try {
            chain.proceed(request)
        } catch (e: Throwable) {
            Response.Builder()
                .message(e.message.orEmpty())
                .code(999)
                .protocol(Protocol.HTTP_2)
                .body(
                    Gson().toJson(
                        BaseResponse<Any>(
                            999,
                            null,
                            e.message,
                            400,
                        )
                    ).toResponseBody()
                )
                .request(chain.request())
                .build()
        }
    }
}