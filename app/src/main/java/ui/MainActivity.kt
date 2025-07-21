package com.miempresa.midiariodigital

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

