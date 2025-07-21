package com.miempresa.midiariodigital.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EntradaDiarioDao {
    @Query("SELECT * FROM entradas ORDER BY fecha DESC")
    suspend fun obtenerEntradas(): List<EntradaDiarioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEntrada(entrada: EntradaDiarioEntity)

    @Delete
    suspend fun eliminarEntrada(entrada: EntradaDiarioEntity) // ✅ función para borrar una entrada
}
