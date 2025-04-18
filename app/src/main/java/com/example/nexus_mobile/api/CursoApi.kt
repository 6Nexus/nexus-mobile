package com.example.nexus_mobile.api

import com.example.nexus_mobile.dto.CursoDto
import com.example.nexus_mobile.dto.CurtidaCriacaoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CursoApi {

    @GET("cursos")
    suspend fun getCursos(
        @Header("Authorization") token: String,
        @Query("idAssociado") usuarioId: Int
    ): List<CursoDto>

    @GET("cursos/{id}")
    suspend fun getCursoPorId(
        @Path("id") cursoId: Int,
        @Query("idAssociado") usuarioId: Int
    ): CursoDto

    @GET("categoria/{categoria}")
    suspend fun getCursosPorCategoria(
        @Path("categoria") categoria: String
    ): List<CursoDto>

    @POST("curtidas")
    suspend fun curtirCurso(
        @Header("Authorization") token: String,
        @Body curtida: CurtidaCriacaoDto
    )

    @DELETE("curtidas/{idAssociado}/{idCurso}")
    suspend fun descurtirCurso(
        @Header("Authorization") token: String,
        @Path("idAssociado") idAssociado: Int,
        @Path("idCurso") idCurso: Int
    )

    @GET("curtidas/{idAssociado}")
    suspend fun getFavoritosDoUsuario(@Path("idAssociado") idAssociado: Int): List<CursoDto>
}

