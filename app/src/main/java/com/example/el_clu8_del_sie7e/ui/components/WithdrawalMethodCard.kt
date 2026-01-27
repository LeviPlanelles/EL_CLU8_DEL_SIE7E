package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed

/**
 * =====================================================================================
 * WITHDRAWALMETHODCARD.KT - COMPONENTE DE MÉTODO DE RETIRO
 * =====================================================================================
 *
 * Este componente muestra un método de retiro disponible:
 * - Icono del método dentro de círculo con borde dorado
 * - Nombre del método (ej: "Transferencia Bancaria")
 * - Tiempo estimado (ej: "1- 3 días hábiles")
 * - Seleccionable con efecto visual
 *
 * EJEMPLO DE USO:
 * ```kotlin
 * WithdrawalMethodCard(
 *     icon = Icons.Default.AccountBalance,
 *     methodName = "Transferencia Bancaria",
 *     timeEstimate = "1- 3 días hábiles",
 *     isSelected = true,
 *     onClick = { /* seleccionar método */ }
 * )
 * ```
 *
 * =====================================================================================
 */

@Composable
fun WithdrawalMethodCard(
    icon: ImageVector,
    methodName: String,
    timeEstimate: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) PrimaryRed.copy(alpha = 0.3f) else Color(0xFF2A2A2A),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) PrimaryRed else Color.Gray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // ------------------------------------------------------------------
            // ICONO DEL MÉTODO
            // ------------------------------------------------------------------
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
                    .border(
                        width = 1.5.dp,
                        color = AccentGold,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = methodName,
                    tint = AccentGold,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // ------------------------------------------------------------------
            // TEXTOS (NOMBRE Y TIEMPO)
            // ------------------------------------------------------------------
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = methodName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )

                Text(
                    text = timeEstimate,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun WithdrawalMethodCardPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WithdrawalMethodCard(
                icon = Icons.Default.AccountBalance,
                methodName = "Transferencia Bancaria",
                timeEstimate = "1- 3 días hábiles",
                isSelected = true,
                onClick = {}
            )

            WithdrawalMethodCard(
                icon = Icons.Default.AccountBalance,
                methodName = "Visa / Mastercard",
                timeEstimate = "Instantáneo",
                isSelected = false,
                onClick = {}
            )
        }
    }
}
