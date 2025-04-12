package com.example.nexus_mobile.data.model.services

import com.example.nexus_mobile.data.model.login.LoginRequest
import com.example.nexus_mobile.data.model.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface AssociadoService {

    @POST("associados/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}
