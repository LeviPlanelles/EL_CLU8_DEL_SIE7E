package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
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
 * PAYMENTMETHODCARD.KT - COMPONENTE DE TARJETA DE MÉTODO DE PAGO
 * =====================================================================================
 *
 * Este componente muestra una tarjeta para seleccionar un método de pago.
 * Usado en la pantalla de depósito para elegir entre Tarjeta, Transferencia, E-Wallet.
 *
 * DISEÑO:
 * -------
 * - Seleccionado: Fondo con gradiente rojo y check verde en la esquina
 * - No seleccionado: Fondo gris oscuro con borde sutil
 * - Icono del método de pago en la parte superior
 * - Nombre del método debajo del icono
 *
 * PARÁMETROS:
 * -----------
 * @param icon ID del recurso drawable del icono
 * @param label Nombre del método de pago (ej: "TARJETA", "TRANSFERENCIA")
 * @param selected Estado de selección del método
 * @param onClick Acción al presionar la tarjeta
 * @param modifier Modificador opcional
 *
 * =====================================================================================
 */
@Composable
fun PaymentMethodCard(
    icon: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(120.dp)
            .height(100.dp)
            .then(
                if (selected) {
                    Modifier.background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF8B1A1A), // Rojo oscuro
                                Color(0xFF5A0F0F)  // Rojo más oscuro
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                        .background(
                            color = Color(0xFF2E2E2E),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.5.dp,
                            color = Color(0xFF777150),
                            shape = RoundedCornerShape(12.dp)
                        )
                }
            )
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        // Contenido de la tarjeta
        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            // Icono del método de pago
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = if (selected) AccentGold else Color.Gray,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre del método
            Text(
                text = label,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Check de selección (solo si está seleccionado)
        if (selected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
                    .background(
                        color = Color(0xFF4CAF50), // Verde
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Seleccionado",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
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
fun PaymentMethodCardPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Column(modifier = Modifier.padding(16.dp)) {
            PaymentMethodCard(
                icon = R.drawable.ic_wallet,
                label = "TARJETA",
                selected = true,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            PaymentMethodCard(
                icon = R.drawable.ic_wallet,
                label = "TRANSFERENCIA",
                selected = false,
                onClick = {}
            )
        }
    }
}
