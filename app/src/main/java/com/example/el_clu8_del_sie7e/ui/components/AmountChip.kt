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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.Tomorrow

/**
 * =====================================================================================
 * AMOUNTCHIP.KT - COMPONENTE DE CHIP DE MONTO
 * =====================================================================================
 */
@Composable
fun AmountChip(
    amount: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedBrush: Brush? = null, // Permite personalizar el fondo cuando está seleccionado
    selectedTextColor: Color = Color.Black // Permite personalizar el color del texto cuando está seleccionado
) {
    Box(
        modifier = modifier
            .background(
                brush = if (selected) {
                    selectedBrush ?: SolidColor(AccentGold)
                } else {
                    SolidColor(Color.Transparent)
                },
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.5.dp,
                color = if (selected) AccentGold else Color(0xFF777150),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        // Monto con fuente Tomorrow para números
        Text(
            text = amount,
            color = if (selected) selectedTextColor else AccentGold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Tomorrow
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun AmountChipPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Row(modifier = Modifier.padding(16.dp)) {
            AmountChip(
                amount = "+$50",
                selected = false,
                onClick = {}
            )
            AmountChip(
                amount = "+$500",
                selected = true,
                onClick = {},
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
