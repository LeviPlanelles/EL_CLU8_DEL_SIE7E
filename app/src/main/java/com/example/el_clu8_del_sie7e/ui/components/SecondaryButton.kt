package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme

/**
 * =====================================================================================
 * SECONDARYBUTTON.KT - BOTON SECUNDARIO DE LA APLICACION
 * =====================================================================================
 *
 * Este boton se usa para acciones secundarias o alternativas como:
 * - "REGISTRARSE" (alternativa a iniciar sesion)
 * - "CANCELAR"
 * - "VOLVER"
 *
 * DIFERENCIA CON PRIMARY BUTTON:
 * ------------------------------
 * - PrimaryButton: Fondo dorado solido (accion principal)
 * - SecondaryButton: Fondo transparente con borde dorado (accion secundaria)
 *
 * ESTILO:
 * -------
 * - Fondo: Transparente
 * - Borde: Dorado (1dp de grosor)
 * - Texto: Dorado
 * - Forma: Bordes redondeados (8dp)
 *
 * PARAMETROS:
 * -----------
 * @param text El texto que muestra el boton
 * @param onClick Funcion que se ejecuta al presionar el boton
 * @param modifier Modificador opcional para personalizar
 * @param enabled Si es false, el boton esta deshabilitado
 *
 * =====================================================================================
 */

/**
 * Boton secundario con borde.
 *
 * EJEMPLOS DE USO:
 * ```kotlin
 * // Boton basico
 * SecondaryButton(
 *     text = "REGISTRARSE",
 *     onClick = { navController.navigate(Routes.REGISTER_SCREEN) }
 * )
 *
 * // Boton deshabilitado
 * SecondaryButton(
 *     text = "CANCELAR",
 *     onClick = { },
 *     enabled = false
 * )
 * ```
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    /**
     * OutlinedButton es un boton con borde pero sin fondo solido.
     * Es perfecto para acciones secundarias porque es menos prominente
     * que un Button normal.
     */
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        // Borde dorado de 1dp de grosor
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) {
                MaterialTheme.colorScheme.secondary  // Dorado cuando habilitado
            } else {
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)  // Dorado semi-transparente
            }
        )
    ) {
        Text(
            text = text,
            color = if (enabled) {
                MaterialTheme.colorScheme.secondary  // Dorado
            } else {
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
            }
        )
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun SecondaryButtonPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        SecondaryButton(
            text = "REGISTRARSE",
            onClick = { }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun SecondaryButtonDisabledPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        SecondaryButton(
            text = "DESHABILITADO",
            onClick = { },
            enabled = false
        )
    }
}
