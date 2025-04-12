package com.example.nexus_mobile.data.model.services

import android.content.Context
import android.content.SharedPreferences

// esse object serve para salvar o token JWT
// e qualquer outra requisição pode pegar desse arquivo

object TokenJWT {

    private val prefs = "jwt_prefs"

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


}