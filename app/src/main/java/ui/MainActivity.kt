package com.miempresa.midiariodigital

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.miempresa.midiariodigital.preferences.ThemePreferences
import com.miempresa.midiariodigital.ui.MainScreen
import com.miempresa.midiariodigital.ui.theme.MiDiarioDigitalTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiDiarioDigitalTheme {
                MainScreen()
            }
        }

        }
        }

