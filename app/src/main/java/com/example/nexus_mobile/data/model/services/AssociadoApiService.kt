package com.example.nexus_mobile.data.model.services

import com.example.nexus_mobile.data.model.cadastro.CadastroRequest
import com.example.nexus_mobile.data.model.cadastro.CadastroResponse
import com.example.nexus_mobile.data.model.login.LoginRequest
import com.example.nexus_mobile.data.model.login.LoginResponse
import com.example.nexus_mobile.data.model.matricula.MatriculaCriacaoDto
import com.example.nexus_mobile.data.model.matricula.MatriculaResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface AssociadoService {

    // Logim
    @POST("associados/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    // Cadastro
    @POST("associados")
    suspend fun cadastrar(@Body cadastroRequest: CadastroRequest): CadastroResponse

    // Matricula
    @POST("matriculas")
    suspend fun matricular(@Body matriculaRequest: MatriculaCriacaoDto): Int

}
