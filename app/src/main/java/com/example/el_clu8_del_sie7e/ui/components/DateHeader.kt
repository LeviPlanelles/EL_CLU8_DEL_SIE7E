package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * =====================================================================================
 * DATEHEADER.KT - COMPONENTE DE ENCABEZADO DE FECHA
 * =====================================================================================
 *
 * Componente reutilizable para mostrar un encabezado de fecha en listas agrupadas.
 *
 * USOS:
 * -----
 * - Agrupar transacciones por fecha
 * - Separar secciones de contenido cronológico
 * - Headers de mensajes agrupados por día
 *
 * CARACTERÍSTICAS:
 * ----------------
 * - Texto en mayúsculas
 * - Espaciado de letras aumentado (letter spacing)
 * - Color blanco semitransparente (40%)
 * - Padding consistente con el diseño
 *
 * DISEÑO:
 * -------
 * - Color: Blanco con 40% opacidad
 * - Fuente: Negrita (Bold)
 * - Tamaño: 14sp
 * - Espaciado de letras: 1sp
 * - Texto: Siempre en MAYÚSCULAS
 *
 * EJEMPLO DE USO:
 * ---------------
 * ```kotlin
 * val groupedItems = items.groupBy { it.date }
 * groupedItems.forEach { (date, itemsOfDay) ->
 *     DateHeader(date = date)
 *     itemsOfDay.forEach { item ->
 *         ItemComponent(item)
 *     }
 * }
 * ```
 *
 * =====================================================================================
 */
@Composable
fun DateHeader(
    date: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 21.dp, end = 21.dp, top = 18.dp, bottom = 12.dp)
    ) {
        Text(
            text = date.uppercase(),
            color = Color.White.copy(alpha = 0.4f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}
