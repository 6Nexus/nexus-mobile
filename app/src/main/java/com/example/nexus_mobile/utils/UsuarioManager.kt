package com.example.nexus_mobile.utils

import android.content.Context

object UsuarioManager {
    private const val PREF_NAME = "user_prefs"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"

    fun salvarUsuario(context: Context, userId: Int, nome: String, email: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putInt(KEY_USER_ID, userId)  // Usando userId
            .putString(KEY_USER_NAME, nome)
            .putString(KEY_USER_EMAIL, email)
            .apply()
    }

    fun getUserId(context: Context): Int {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_USER_ID, -1)
    }

    fun getUserName(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_NAME, "") ?: ""
    }

    fun getUserEmail(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_EMAIL, "") ?: ""
    }

    fun limparDados(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}