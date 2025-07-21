package com.miempresa.midiariodigital.model

data class ClimaResponse(
    val name: String,
    val main: Main
)

data class Main(
    val temp: Double
)