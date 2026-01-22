package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.TextSecondary

/**
 * =====================================================================================
 * APPLOGO.KT - COMPONENTE DEL LOGO DE LA APLICACION
 * =====================================================================================
 *
 * Este componente muestra el logo de la app con el nombre y eslogan.
 * Se usa en SplashScreen, LoginScreen y otras pantallas.
 *
 * QUE ES UN COMPONENTE REUTILIZABLE?
 * ----------------------------------
 * Es un Composable que encapsula una pieza de UI que se usa en varios lugares.
 * Beneficios:
 * 1. DRY (Don't Repeat Yourself): Escribes el codigo una sola vez
 * 2. Consistencia: El logo se ve igual en toda la app
 * 3. Mantenimiento: Si cambias algo, cambia en todos lados automaticamente
 *
 * PARAMETROS DEL COMPOSABLE:
 * --------------------------
 * - modifier: Permite personalizar el componente desde afuera
 *   Ejemplo: AppLogo(modifier = Modifier.padding(16.dp))
 *
 * =====================================================================================
 */

/**
 * Logo de la aplicacion con icono, nombre y eslogan.
 *
 * @param modifier Modificador opcional para personalizar el layout
 *
 * EJEMPLO DE USO:
 * ```kotlin
 * // Uso basico
 * AppLogo()
 *
 * // Con modificador personalizado
 * AppLogo(modifier = Modifier.padding(32.dp))
 * ```
 */
@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    /**
     * Column organiza los elementos verticalmente:
     * - Icono (arriba)
     * - Nombre de la app (medio)
     * - Eslogan (abajo)
     */
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally  // Centra horizontalmente
    ) {
        // ------------------------------------------------------------------
        // ICONO DEL LOGO
        // ------------------------------------------------------------------
        /**
         * TODO: Reemplazar Icons.Default.Star por el icono de corona real
         *
         * Para usar un icono personalizado de la carpeta res/drawable:
         * ```kotlin
         * Icon(
         *     painter = painterResource(id = R.drawable.ic_crown),
         *     contentDescription = "Logo Corona",
         *     tint = MaterialTheme.colorScheme.secondary
         * )
         * ```
         */
        Icon(
            imageVector = Icons.Default.Star,               // Placeholder (estrella)
            contentDescription = "Logo Corona",             // Para accesibilidad
            tint = MaterialTheme.colorScheme.secondary,     // Color dorado del tema
            modifier = Modifier.size(80.dp)                 // Tamano del icono
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ------------------------------------------------------------------
        // NOMBRE DE LA APP
        // ------------------------------------------------------------------
        Text(
            text = "EL CLU8 DEL SIE7E",
            style = MaterialTheme.typography.titleLarge,    // Estilo de titulo grande
            color = MaterialTheme.colorScheme.secondary     // Color dorado
        )

        Spacer(modifier = Modifier.height(4.dp))

        // ------------------------------------------------------------------
        // ESLOGAN
        // ------------------------------------------------------------------
        Text(
            text = "EXCLUSIVIDAD . LUJO . JUEGO",
            style = MaterialTheme.typography.labelSmall,    // Estilo de etiqueta pequena
            color = TextSecondary                           // Color gris
        )
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun AppLogoPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        AppLogo()
    }
}
