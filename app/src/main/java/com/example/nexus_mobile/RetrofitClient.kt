package com.example.nexus_mobile

import android.content.Context
import com.example.nexus_mobile.api.CursoApi
import com.example.nexus_mobile.api.LoginApi
import com.example.nexus_mobile.autenticador.AuthInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {

    private const val BASE_URL = "http://35.175.242.34/api/"

    private fun getRetrofit(context: Context): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getLoginApi(context: Context): LoginApi {
        return getRetrofit(context).create(LoginApi::class.java)
    }

    fun getCursoApi(context: Context): CursoApi {
        return getRetrofit(context).create(CursoApi::class.java)
    }
}