package com.example.nexus_mobile.api

import com.example.nexus_mobile.dto.CapaRespostaDto
import com.example.nexus_mobile.dto.CursoDto
import com.example.nexus_mobile.dto.CurtidaCriacaoDto
import com.example.nexus_mobile.dto.MatriculaRequest
import com.example.nexus_mobile.dto.MatriculaVerificacaoResponse
import com.example.nexus_mobile.dto.Modulo
import com.example.nexus_mobile.dto.ProgressoRequest
import com.example.nexus_mobile.dto.QuestionarioResponse
import com.example.nexus_mobile.dto.RespostaProgresso
import com.example.nexus_mobile.dto.Video
import retrofit2.Response
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
       // @Header("Authorization") token: String,
        @Query("idAssociado") usuarioId: Int
    ): Response<List<CursoDto>>

    @GET("cursos/{id}")
    suspend fun getCursoPorId(
        @Path("id") cursoId: Int,
        @Query("idAssociado") usuarioId: Int
    ): CursoDto

    @GET("cursos/associado/{idAssociado}")
    suspend fun getCursosMatriculados(
       // @Header("Authorization") token: String,
        @Path("idAssociado") idAssociado: Int
    ): Response<List<CursoDto>>

    @GET("cursos/associado/{idAssociado}/categoria/{categoria}")
    suspend fun getCursosPorCategoria(
        @Path("idAssociado") idAssociado: Int,
        @Path("categoria") categoria: String
    ): Response <List<CursoDto>>

    @POST("curtidas")
    suspend fun curtirCurso(
       // @Header("Authorization") token: String,
        @Body curtida: CurtidaCriacaoDto
    )

    @DELETE("curtidas/{idAssociado}/{idCurso}")
    suspend fun descurtirCurso(
       // @Header("Authorization") token: String,
        @Path("idAssociado") idAssociado: Int,
        @Path("idCurso") idCurso: Int
    )

    @GET("curtidas/{idAssociado}")
    suspend fun getFavoritosDoUsuario(
        @Path("idAssociado") idAssociado: Int
    ): Response<List<CursoDto>>

    @POST("matriculas")
    suspend fun matricular(@Body matriculaRequest: MatriculaRequest): Response<Unit>

    @GET("matriculas/{usuarioId}/{cursoId}")
    suspend fun verificarMatricula(
      //  @Header("Authorization") token: String,
        @Path("usuarioId") usuarioId: Int,
        @Path("cursoId") cursoId: Int
    ): Response<Int>


    @GET("modulos/curso/{idCurso}")
    suspend fun getModulosPorCurso(
        @Path("idCurso") cursoId: Int,
      //  @Header("Authorization") token: String
    ):  Response<List<Modulo>>

    @GET("videos/modulo/{moduloId}")
    suspend fun getVideosPorModulo(@Path("moduloId") moduloId: Int): Response <List<Video>>

    @GET("questionarios/modulo/{moduloId}")
    suspend fun getQuestionarioPorModulo(
        @Path("moduloId") moduloId: Int,
       // @Header("Authorization") authToken: String
    ): QuestionarioResponse


    @POST("progresso-questionarios")
    suspend fun enviarProgresso(
        @Body progresso: ProgressoRequest,
       // @Header("Authorization") authToken: String
    )


    @GET("progresso-questionarios/{matriculaId}/{questionarioId}")
    suspend fun buscarProgressoQuestionario(
        @Path("matriculaId") matriculaId: Int,
        @Path("questionarioId") questionarioId: Int,
       // @Header("Authorization") authToken: String
    ): RespostaProgresso

    // CAPA CURSO
    @GET("cursos/capa/{cursoId}")
    suspend fun buscarCapaCurso(
        @Path("cursoId") cursoId: Int
    ): Response<CapaRespostaDto>

}

