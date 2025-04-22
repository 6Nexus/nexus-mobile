package com.example.nexus_mobile

import android.content.Context
import com.example.nexus_mobile.api.LoginApi
import com.example.nexus_mobile.autenticador.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitLogin {

    fun create(context: Context): LoginApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl("http://192.168.18.88:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(LoginApi::class.java)
    }
}
