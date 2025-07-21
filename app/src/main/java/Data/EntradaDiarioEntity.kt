package com.miempresa.midiariodigital.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entradas")
data class EntradaDiarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val contenido: String,
    val fecha: String
)

