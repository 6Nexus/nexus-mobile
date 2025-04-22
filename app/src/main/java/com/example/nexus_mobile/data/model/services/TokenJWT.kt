package com.example.nexus_mobile.data.model.services

import android.content.Context
import android.content.SharedPreferences

object TokenJWT {

    private val prefs = "jwt_prefs"
    private const val NOME_KEY = "usuario_nome"
    private const val EMAIL_KEY = "usuario_email"
    private const val ID_USUARIO_KEY = "usuario_id"

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

    fun salvarDadosUsuario(context: Context, nome: String, email: String) {
        val prefs = getPrefs(context)
        prefs.edit()
            .putString(NOME_KEY, nome)
            .putString(EMAIL_KEY, email)
            .apply()
    }

    fun salvarIdUsuario(context: Context, idUsuario: Int) {
        val prefs = getPrefs(context)
        prefs.edit().putInt(ID_USUARIO_KEY, idUsuario).apply()
    }

    fun recuperarIdUsuario(context: Context): Int {
        val prefs = getPrefs(context)
        return prefs.getInt(ID_USUARIO_KEY, -1) // Retorna -1 se não encontrar
    }

    fun recuperarNomeUsuario(context: Context): String? {
        val prefs = getPrefs(context)
        return prefs.getString(NOME_KEY, null)
    }

    fun recuperarEmailUsuario(context: Context): String? {
        val prefs = getPrefs(context)
        return prefs.getString(EMAIL_KEY, null)
    }

    fun limparDadosUsuario(context: Context) {
        val prefs = getPrefs(context)
        prefs.edit()
            .remove(NOME_KEY)
            .remove(EMAIL_KEY)
            .remove(ID_USUARIO_KEY)
            .apply()
    }
}
