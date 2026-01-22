package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme

/**
 * =====================================================================================
 * PRIMARYBUTTON.KT - BOTON PRINCIPAL DE LA APLICACION
 * =====================================================================================
 *
 * Este es el boton de accion principal que se usa para acciones importantes como:
 * - "INICIAR SESION"
 * - "CREAR CUENTA"
 * - "DEPOSITAR"
 * - "JUGAR"
 *
 * ESTILO:
 * -------
 * - Fondo: Dorado (color secundario/acento)
 * - Texto: Oscuro (para contraste con el dorado)
 * - Forma: Bordes redondeados (8dp)
 * - Ancho: Ocupa todo el ancho disponible
 *
 * PARAMETROS:
 * -----------
 * @param text El texto que muestra el boton
 * @param onClick Funcion que se ejecuta al presionar el boton
 * @param modifier Modificador opcional para personalizar
 * @param icon Icono opcional que aparece antes del texto
 * @param enabled Si es false, el boton esta deshabilitado (gris y no clickeable)
 *
 * =====================================================================================
 */

/**
 * Boton principal de accion.
 *
 * EJEMPLOS DE USO:
 * ```kotlin
 * // Boton simple sin icono
 * PrimaryButton(
 *     text = "CONTINUAR",
 *     onClick = { /* hacer algo */ }
 * )
 *
 * // Boton con icono
 * PrimaryButton(
 *     text = "INICIAR SESION",
 *     onClick = { /* hacer algo */ },
 *     icon = Icons.Filled.Login
 * )
 *
 * // Boton deshabilitado
 * PrimaryButton(
 *     text = "ENVIAR",
 *     onClick = { },
 *     enabled = false
 * )
 * ```
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,          // Icono opcional
    enabled: Boolean = true             // Por defecto habilitado
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()             // Ocupa todo el ancho
            .height(50.dp),             // Altura fija para consistencia
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),  // Bordes redondeados
        colors = ButtonDefaults.buttonColors(
            // Color de fondo cuando esta habilitado
            containerColor = MaterialTheme.colorScheme.secondary,  // Dorado
            // Color del texto/icono cuando esta habilitado
            contentColor = Color.Black,  // Negro para contrastar con dorado
            // Color de fondo cuando esta deshabilitado
            disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
            // Color del texto cuando esta deshabilitado
            disabledContentColor = Color.Black.copy(alpha = 0.5f)
        )
    ) {
        // Si hay un icono, lo mostramos antes del texto
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null  // El texto ya describe la accion
            )
            Spacer(modifier = Modifier.width(8.dp))  // Espacio entre icono y texto
        }

        Text(text = text)
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun PrimaryButtonPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        PrimaryButton(
            text = "INICIAR SESION",
            onClick = { },
            icon = Icons.Filled.Login
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun PrimaryButtonDisabledPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        PrimaryButton(
            text = "DESHABILITADO",
            onClick = { },
            enabled = false
        )
    }
}
