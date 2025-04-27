package com.example.nexus_mobile.api

import com.example.nexus_mobile.dto.CadastroRequest
import com.example.nexus_mobile.dto.CadastroResponse
import com.example.nexus_mobile.dto.LoginRequest
import com.example.nexus_mobile.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LoginApi {
    @POST("associados/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("associados")
    suspend fun cadastrar(
        @Body cadastroRequest: CadastroRequest
    ): CadastroResponse

    @PUT("associados/{id}")
    suspend fun atualizarUsuario(
        @Body cadastroRequest: CadastroRequest,
        @Path("id") id: Int
    ): Response<CadastroResponse>


}