package com.example.nexus_mobile.data.model.services

import com.example.nexus_mobile.data.model.cadastro.CadastroRequest
import com.example.nexus_mobile.data.model.cadastro.CadastroResponse
import com.example.nexus_mobile.data.model.login.LoginRequest
import com.example.nexus_mobile.data.model.login.LoginResponse
import com.example.nexus_mobile.data.model.matricula.MatriculaCriacaoDto
import com.example.nexus_mobile.data.model.matricula.MatriculaResponse
import com.example.nexus_mobile.data.model.perfil.PerfilRequest
import com.example.nexus_mobile.data.model.perfil.PerfilResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface AssociadoService {

    // Login
    @POST("associados/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    // Cadastro
    @POST("associados")
    suspend fun cadastrar(@Body cadastroRequest: CadastroRequest): CadastroResponse

    // Matrícula
    @POST("matriculas")
    suspend fun matricular(@Body matriculaRequest: MatriculaCriacaoDto): Int

    // Buscar associado por curso
    @GET("matriculas/{idAssociado}/{idCurso}")
    suspend fun buscarAssociadoPorCurso(
        @Path("idAssociado") idAssociado: Int,
        @Path("idCurso") idCurso: Int
    ): Int

    // Atualizar Perfil
    @PUT("associados/{id}")
    suspend fun atualizarPerfil(
        @Path("id") id: Int,
        @Body perfilRequest: PerfilRequest
    ): PerfilResponse

}
