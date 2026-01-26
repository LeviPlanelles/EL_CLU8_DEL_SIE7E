package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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

/**
 * =====================================================================================
 * REDBUTTON.KT - BOTON ROJO DE ACCION
 * =====================================================================================
 *
 * Este es el boton rojo con gradiente que se usa para acciones dentro del lobby como:
 * - "RECLAMAR AHORA"
 * - "JUGAR AHORA"
 *
 * ESTILO:
 * -------
 * - Fondo: Gradiente rojo (ButtonRedStart -> ButtonRedCenter -> ButtonRedEnd)
 * - Borde: Dorado sutil
 * - Texto: Blanco
 * - Forma: Bordes ligeramente redondeados (12dp)
 *
 * PARAMETROS:
 * -----------
 * @param text El texto que muestra el boton
 * @param onClick Funcion que se ejecuta al presionar el boton
 * @param modifier Modificador opcional para personalizar
 * @param icon Icono opcional que aparece despues del texto (flecha por defecto)
 * @param showArrow Si se muestra la flecha al final
 *
 * =====================================================================================
 */
@Composable
fun RedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = Icons.AutoMirrored.Filled.ArrowForward,
    showArrow: Boolean = true
) {
    // Forma del boton - bordes mas cuadrados (12dp)
    val buttonShape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            // Borde dorado exterior
            .border(
                width = 1.dp,
                color = AccentGold.copy(alpha = 0.6f),
                shape = buttonShape
            )
            .clip(buttonShape)
            // Fondo con gradiente rojo
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        ButtonRedStart,
                        ButtonRedCenter,
                        ButtonRedEnd
                    )
                )
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            if (showArrow && icon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White
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
fun RedButtonPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        RedButton(
            text = "RECLAMAR AHORA",
            onClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun RedButtonNoArrowPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        RedButton(
            text = "JUGAR AHORA",
            onClick = { },
            showArrow = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}
