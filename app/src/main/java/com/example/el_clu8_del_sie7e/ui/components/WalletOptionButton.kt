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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedCenter
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedEnd
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedStart
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.Poppins

/**
 * =====================================================================================
 * WALLETOPTIONBUTTON.KT - BOTON DE OPCION PARA LA PANTALLA DE CARTERA
 * =====================================================================================
 *
 * Este componente es un botón grande con:
 * - Fondo con gradiente rojo
 * - Borde dorado
 * - Icono en el lado izquierdo
 * - Título y descripción
 * - Flecha en el lado derecho
 *
 * ESTILO:
 * -------
 * - Fondo: Gradiente rojo horizontal (ButtonRedStart -> ButtonRedCenter -> ButtonRedEnd)
 * - Borde: Dorado (AccentGold)
 * - Forma: Bordes redondeados (16dp)
 * - Altura: Auto (basado en contenido)
 *
 * PARAMETROS:
 * -----------
 * @param icon El icono que se muestra a la izquierda
 * @param title El título principal del botón (ej: "DEPOSITAR")
 * @param description La descripción debajo del título (ej: "Carga crédito a tu cuenta")
 * @param onClick Función que se ejecuta al presionar el botón
 * @param modifier Modificador opcional para personalizar
 *
 * USO:
 * ----
 * WalletOptionButton(
 *     icon = Icons.Default.Upload,
 *     title = "DEPOSITAR",
 *     description = "Carga crédito a tu cuenta",
 *     onClick = { /* Navegar a DepositScreen */ }
 * )
 *
 * =====================================================================================
 */
@Composable
fun WalletOptionButton(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Forma del botón - bordes redondeados
    val buttonShape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            // Borde dorado exterior (mismo que otros componentes)
            .border(
                width = 1.5.dp,
                color = Color(0xFF777150),
                shape = buttonShape
            )
            .clip(buttonShape)
            // Fondo con gradiente rojo (mismo que RedButton)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        ButtonRedStart,
                        ButtonRedCenter,
                        ButtonRedEnd
                    )
                )
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ===================================================================
            // LADO IZQUIERDO: ICONO + TEXTOS
            // ===================================================================
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Icono en fondo dorado/oscuro
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(
                            color = Color(0xFF2E2E2E),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = AccentGold.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = AccentGold,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Textos (título + descripción)
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins
                    )
                    Text(
                        text = description,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        fontFamily = Poppins
                    )
                }
            }

            // ===================================================================
            // LADO DERECHO: FLECHA
            // ===================================================================
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF2E2E2E)
@Composable
fun WalletOptionButtonPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            WalletOptionButton(
                icon = Icons.Filled.CreditCard,
                title = "DEPOSITAR",
                description = "Carga crédito a tu cuenta",
                onClick = { }
            )
        }
    }
}
