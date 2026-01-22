
package com.example.el_clu8_del_sie7e.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen() {
    // El Box es el contenedor principal, que ocupa toda la pantalla.
    Box(
        modifier = Modifier
            .fillMaxSize()
            // El fondo es un gradiente vertical que va de un rojo oscuro a un rojo más oscuro.
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6D0000),
                        Color(0xFF4B0000)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // La Column apila los elementos verticalmente.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icono de estrella como placeholder.
            // Reemplaza esto con tu propio icono de corona cuando lo tengas.
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Corona",
                tint = Color.Yellow
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Texto del título.
            Text(
                text = "EL CLU8 DEL SIE7E",
                color = Color.Yellow,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Indicador de progreso circular.
            CircularProgressIndicator(color = Color.Yellow)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}
