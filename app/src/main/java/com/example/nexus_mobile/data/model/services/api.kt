package com.example.nexus_mobile.data.model.services

import android.content.Context
import android.util.Log
import com.example.nexus_mobile.data.model.login.LoginRequest
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofit Client para comunicação com a API
object api {

   // private val token = TokenJWT

   // private const val BASE_URL = "http://10.0.2.2:8080/" // - emulador
   private const val BASE_URL = "http://192.168.18.88:8080/" // IP do computador

    fun criarApi(context: Context): AssociadoService {
        val okHttpClient = OkHttpClient.Builder() // cria um cliente http
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                TokenJWT.recuperarToken(context)?.let { token ->
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                chain.proceed(requestBuilder.build())
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AssociadoService::class.java)

    }
}

