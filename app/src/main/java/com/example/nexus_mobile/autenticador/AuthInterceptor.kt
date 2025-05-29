package com.example.nexus_mobile.autenticador

import android.content.Context
import android.util.Log
import com.example.nexus_mobile.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (chain.request().url.toString().contains("api/associados/login")) {
            return chain.proceed(chain.request())
        }

        val token = TokenManager.getToken(context)

        val requestBuilder = originalRequest.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("User-Agent", "Android-App") // <- importante
            .addHeader("Connection", "keep-alive")

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val request = requestBuilder.build()

        // Log completo da requisição
        Log.d("AuthInterceptor", "Interceptando requisição:")
        Log.d("AuthInterceptor", "URL: ${request.url}")
        Log.d("AuthInterceptor", "Method: ${request.method}")
        Log.d("AuthInterceptor", "Headers: ${request.headers}")

        return chain.proceed(request)
    }


}