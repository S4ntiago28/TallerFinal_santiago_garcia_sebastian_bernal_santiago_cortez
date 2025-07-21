package com.miempresa.midiariodigital.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntradaDiarioEntity::class], version = 1, exportSchema = false)
abstract class DiarioDatabase : RoomDatabase() {
    abstract fun entradaDao(): EntradaDiarioDao

    companion object {
        @Volatile private var instancia: DiarioDatabase? = null

        fun obtenerInstancia(context: Context): DiarioDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    DiarioDatabase::class.java,
                    "diario_db"
                ).build().also { instancia = it }
            }
    }
}
