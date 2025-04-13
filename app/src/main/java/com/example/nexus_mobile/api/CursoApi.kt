package com.example.nexus_mobile.api

import com.example.nexus_mobile.dto.CursoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CursoApi {
    @GET("usuarios/{usuarioId}/cursos")
    suspend fun getCursos(
        @Path("usuarioId") usuarioId: Int
    ): List<CursoDto>
}
