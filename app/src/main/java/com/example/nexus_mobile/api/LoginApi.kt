package com.example.nexus_mobile.api

import com.example.nexus_mobile.dto.LoginRequest
import com.example.nexus_mobile.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    // Defina o endpoint de login, por exemplo
    @POST("/login") // O caminho do endpoint depende da sua API
    suspend fun login(
        @Body loginRequest: LoginRequest // Envia um objeto com email e senha
    ): LoginResponse // Espera um objeto de resposta contendo o token
}