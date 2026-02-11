package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.Tomorrow

/**
 * =====================================================================================
 * PROFILESTATSCARD.KT - COMPONENTE DE TARJETA DE ESTADISTICAS DEL PERFIL
 * =====================================================================================
 *
 * Este componente muestra una estadistica del perfil del usuario:
 * - Titulo (ej: "Saldo Actual")
 * - Valor (ej: "$5,000.00")
 *
 * Se usa para mostrar el saldo actual y los puntos VIP en la pantalla de perfil.
 *
 * EJEMPLO DE USO:
 * ```kotlin
 * ProfileStatsCard(
 *     title = "Saldo Actual",
 *     value = "$5,000.00"
 * )
 * ```
 *
 * =====================================================================================
 */

@Composable
fun ProfileStatsCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = Color.Gray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título (ej: "Saldo Actual")
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            fontSize = 14.sp
        )

        // Valor (ej: "$5,000.00") con fuente Tomorrow para números
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            color = AccentGold,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Tomorrow
        )
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun ProfileStatsCardPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        ProfileStatsCard(
            title = "Saldo Actual",
            value = "$5,000.00"
        )
    }
}
