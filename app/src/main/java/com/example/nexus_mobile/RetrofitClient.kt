package com.example.nexus_mobile

import com.example.nexus_mobile.api.CursoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080") // ou IP da sua máquina
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


    val api: CursoApi by lazy {
        retrofit.create(CursoApi::class.java)
    }
}