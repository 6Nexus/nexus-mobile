package com.example.nexus_mobile.data.model.services

import com.example.nexus_mobile.data.model.perfil.PerfilRequest
import com.example.nexus_mobile.data.model.perfil.PerfilResponse
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface AssociadoApiService {


    @PUT("associados/{id}")
    suspend fun atualizarPerfil(
        @Path("id") id: Int,
        @Body perfilRequest: PerfilRequest
    ): PerfilResponse


}
