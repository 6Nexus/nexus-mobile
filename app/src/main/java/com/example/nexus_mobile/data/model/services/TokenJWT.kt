package com.example.nexus_mobile.data.model.services

import android.content.Context
import android.content.SharedPreferences

// esse object serve para salvar o token JWT e dados do usuário nome e email
// e qualquer outra requisição pode pegar desse arquivo

object TokenJWT {

    private val prefs = "jwt_prefs"
    private const val NOME_KEY = "usuario_nome"
    private const val EMAIL_KEY = "usuario_email"

    // para obter o SharedPreferences
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(prefs, Context.MODE_PRIVATE)
    }

    fun salvarToken(context: Context, token: String) {
        val prefs = getPrefs(context)
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun recuperarToken(context: Context): String? {
        val prefs = getPrefs(context)
        return prefs.getString("jwt_token", null)
    }

    fun limparToken(context: Context) {
        val prefs = getPrefs(context)
        prefs.edit().remove("jwt_token").apply()

    }

    fun salvarDadosUsuario(context: Context, nome: String, email: String, idAssociado: Int) {
        val prefs = getPrefs(context)
        prefs.edit()
            .putString(NOME_KEY, nome)
            .putString(EMAIL_KEY, email)
            .putInt("idAssociado", idAssociado)
            .apply()
    }

    fun recuperarIdAssociado(context: Context): Int {
        val prefs = getPrefs(context)
        return prefs.getInt("idAssociado", 0)
    }

    fun recuperarNome(context: Context): String? {
        return getPrefs(context).getString(NOME_KEY, null)
    }

    fun recuperarEmail(context: Context): String? {
        return getPrefs(context).getString(EMAIL_KEY, null)
    }

//    fun usuarioLogado(context: Context): Boolean {
//        val prefs = getPrefs(context)
//        return prefs.contains("jwt_token")
//    }

    fun limparDadosUsuario(context: Context) {
        val prefs = getPrefs(context)
        prefs.edit()
            .remove(NOME_KEY)
            .remove(EMAIL_KEY)
            .apply()
    }

}