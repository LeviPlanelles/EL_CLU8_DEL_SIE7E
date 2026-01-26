package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

/**
 * =====================================================================================
 * AMOUNTCHIP.KT - COMPONENTE DE CHIP DE MONTO
 * =====================================================================================
 *
 * Este componente muestra un chip seleccionable para montos predefinidos.
 * Usado en la pantalla de depósito para seleccionar cantidades rápidas.
 *
 * DISEÑO:
 * -------
 * - Seleccionado: Fondo dorado con texto oscuro
 * - No seleccionado: Fondo transparente con borde dorado
 *
 * PARÁMETROS:
 * -----------
 * @param amount Monto a mostrar (ej: "+$50", "+$100")
 * @param selected Estado de selección del chip
 * @param onClick Acción al presionar el chip
 * @param modifier Modificador opcional
 *
 * =====================================================================================
 */
@Composable
fun AmountChip(
    amount: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .then(
                if (selected) {
                    Modifier.background(
                        color = AccentGold,
                        shape = RoundedCornerShape(20.dp)
                    )
                } else {
                    Modifier
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(
                            width = 1.5.dp,
                            color = Color(0xFF777150),
                            shape = RoundedCornerShape(20.dp)
                        )
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = amount,
            color = if (selected) Color.Black else AccentGold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun AmountChipPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Row(modifier = Modifier.padding(16.dp)) {
            AmountChip(
                amount = "+\$50",
                selected = false,
                onClick = {}
            )
            AmountChip(
                amount = "+\$500",
                selected = true,
                onClick = {},
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
