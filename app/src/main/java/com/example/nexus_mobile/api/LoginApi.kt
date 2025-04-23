package com.example.nexus_mobile.api

import com.example.nexus_mobile.dto.LoginRequest
import com.example.nexus_mobile.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("associados/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}

