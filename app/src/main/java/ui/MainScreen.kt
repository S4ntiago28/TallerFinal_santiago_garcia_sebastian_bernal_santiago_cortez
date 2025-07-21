package com.miempresa.midiariodigital.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.miempresa.midiariodigital.api.RetrofitClient
import com.miempresa.midiariodigital.data.DiarioDatabase
import com.miempresa.midiariodigital.data.EntradaDiarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var isDarkMode by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var titulo by remember { mutableStateOf(TextFieldValue("")) }
    var contenido by remember { mutableStateOf(TextFieldValue("")) }
    var fecha by remember { mutableStateOf(TextFieldValue("")) }

    var ciudad by remember { mutableStateOf(TextFieldValue("Madrid")) } // editable
    var clima by remember { mutableStateOf<String?>(null) }
    val apiKey = "abc91b6acc669ccc86b23954a57fa6e5" // tu clave de OpenWeather

    val db = DiarioDatabase.obtenerInstancia(context)
    val entradas = remember { mutableStateListOf<EntradaDiarioEntity>() }

    val fondoColor = if (isDarkMode) Color(0xFF121212) else Color.White
    val textoColor = if (isDarkMode) Color.White else Color.Black
    val campoColor = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFF0F0F0)

    fun consultarClima(nombreCiudad: String) {
        scope.launch {
            try {
                val response = RetrofitClient.api.getWeatherByCity(nombreCiudad, apiKey)
                clima = "${response.name}: ${response.main.temp}°C, ${response.weather[0].description}"
            } catch (e: Exception) {
                clima = "No se pudo obtener el clima"
            }
        }
    }

    LaunchedEffect(Unit) {
        val lista = db.entradaDao().obtenerEntradas()
        entradas.clear()
        entradas.addAll(lista)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoColor)
            .padding(16.dp)
    ) {
        Column {
            // 🔘 Modo oscuro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text("Modo oscuro", color = textoColor)
                Switch(checked = isDarkMode, onCheckedChange = { isDarkMode = it })
            }

            // 🏙 Ciudad
            TextField(
                value = ciudad,
                onValueChange = { ciudad = it },
                label = { Text("Ciudad") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = textoColor,
                    unfocusedTextColor = textoColor,
                    containerColor = campoColor,
                    focusedIndicatorColor = textoColor,
                    unfocusedIndicatorColor = textoColor,
                    focusedLabelColor = textoColor,
                    unfocusedLabelColor = textoColor
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { consultarClima(ciudad.text) }) {
                Text("Consultar clima")
            }

            Spacer(modifier = Modifier.height(8.dp))

            clima?.let {
                Text("Clima de hoy: $it", color = textoColor, style = MaterialTheme.typography.titleSmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Nueva entrada", style = MaterialTheme.typography.headlineSmall, color = textoColor)
            Spacer(modifier = Modifier.height(8.dp))

            // 📝 Entrada: Título
            TextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = textoColor,
                    unfocusedTextColor = textoColor,
                    containerColor = campoColor,
                    focusedIndicatorColor = textoColor,
                    unfocusedIndicatorColor = textoColor,
                    focusedLabelColor = textoColor,
                    unfocusedLabelColor = textoColor
                )
            )

            // 📝 Entrada: Contenido
            TextField(
                value = contenido,
                onValueChange = { contenido = it },
                label = { Text("Contenido") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = textoColor,
                    unfocusedTextColor = textoColor,
                    containerColor = campoColor,
                    focusedIndicatorColor = textoColor,
                    unfocusedIndicatorColor = textoColor,
                    focusedLabelColor = textoColor,
                    unfocusedLabelColor = textoColor
                )
            )

            // 📝 Entrada: Fecha
            TextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = textoColor,
                    unfocusedTextColor = textoColor,
                    containerColor = campoColor,
                    focusedIndicatorColor = textoColor,
                    unfocusedIndicatorColor = textoColor,
                    focusedLabelColor = textoColor,
                    unfocusedLabelColor = textoColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val nuevaEntrada = EntradaDiarioEntity(
                    titulo = titulo.text,
                    contenido = contenido.text,
                    fecha = fecha.text
                )
                scope.launch(Dispatchers.IO) {
                    db.entradaDao().insertarEntrada(nuevaEntrada)
                    val actualizadas = db.entradaDao().obtenerEntradas()
                    entradas.clear()
                    entradas.addAll(actualizadas)
                }
                Toast.makeText(context, "Entrada guardada", Toast.LENGTH_SHORT).show()
            }) {
                Text("Guardar")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = textoColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Entradas guardadas:", style = MaterialTheme.typography.titleMedium, color = textoColor)

            LazyColumn {
                items(entradas) { entrada ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFF9F9F9))
                            .padding(8.dp)
                    ) {
                        Text(text = entrada.titulo, style = MaterialTheme.typography.titleLarge, color = textoColor)
                        Text(text = entrada.fecha, style = MaterialTheme.typography.labelSmall, color = textoColor)
                        Text(text = entrada.contenido, style = MaterialTheme.typography.bodyMedium, color = textoColor)

                        Spacer(modifier = Modifier.height(4.dp))

                        // 🗑 Botón Eliminar
                        Button(
                            onClick = {
                                scope.launch(Dispatchers.IO) {
                                    db.entradaDao().eliminarEntrada(entrada)
                                    val actualizadas = db.entradaDao().obtenerEntradas()
                                    entradas.clear()
                                    entradas.addAll(actualizadas)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Eliminar", color = Color.White)
                        }

                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = textoColor)
                    }
                }
            }

        }
            }
        }

