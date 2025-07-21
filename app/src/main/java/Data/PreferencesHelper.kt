package com.miempresa.midiariodigital.data

import android.content.Context

object PreferencesHelper {
    private const val PREFS_NAME = "config"
    private const val KEY_MODO_OSCURO = "modo_oscuro"

    fun guardarModoOscuro(context: Context, activado: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_MODO_OSCURO, activado).apply()
    }

    fun obtenerModoOscuro(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_MODO_OSCURO, false)
    }
}
