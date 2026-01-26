package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed

/**
 * =====================================================================================
 * FILTERBUTTON.KT - COMPONENTE DE BOTÓN DE FILTRO
 * =====================================================================================
 *
 * Este componente muestra un botón de filtro para categorías de juegos.
 * Usado en la pantalla de búsqueda de juegos para filtrar por categoría.
 *
 * DISEÑO:
 * -------
 * - Botón con icono (opcional) y texto
 * - Estado seleccionado: fondo rojo (PrimaryRed)
 * - Estado no seleccionado: fondo transparente con borde dorado
 * - Esquinas redondeadas
 *
 * PARÁMETROS:
 * -----------
 * @param text Texto del botón (ej: "Todos", "Slots", "Cartas")
 * @param icon ID del recurso drawable del icono (opcional)
 * @param selected Estado de selección del botón
 * @param onClick Acción al presionar el botón
 * @param modifier Modificador opcional
 *
 * =====================================================================================
 */
@Composable
fun FilterButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Int? = null
) {
    Box(
        modifier = modifier
            .background(
                color = if (selected) PrimaryRed else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = if (selected) PrimaryRed else AccentGold,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono opcional
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = text,
                    tint = if (selected) Color.White else AccentGold,
                    modifier = Modifier.size(16.dp)
                )
                // Espacio entre icono y texto
                Box(modifier = Modifier.padding(end = 6.dp))
            }

            // Texto del botón
            Text(
                text = text,
                color = if (selected) Color.White else AccentGold,
                fontSize = 13.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun FilterButtonPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterButton(
                text = "Todos",
                selected = true,
                onClick = {}
            )
            FilterButton(
                text = "Slots",
                icon = R.drawable.game_slots,
                selected = false,
                onClick = {}
            )
            FilterButton(
                text = "Cartas",
                icon = R.drawable.ic_cards,
                selected = false,
                onClick = {}
            )
            FilterButton(
                text = "Otros",
                selected = false,
                onClick = {}
            )
        }
    }
}
