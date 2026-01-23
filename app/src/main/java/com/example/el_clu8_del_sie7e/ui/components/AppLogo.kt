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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.TextSecondary
import com.example.el_clu8_del_sie7e.ui.theme.Gideon
import com.example.el_clu8_del_sie7e.R



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
            painter = painterResource(id = R.drawable.icon_crown),               // Placeholder (estrella)
            contentDescription = "Logo Corona",             // Para accesibilidad
            tint = MaterialTheme.colorScheme.secondary,     // Color dorado del tema
            modifier = Modifier.size(80.dp)                 // Tamano del icono
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ------------------------------------------------------------------
        // NOMBRE DE LA APP
        // ------------------------------------------------------------------
        /**
         * El logo usa la fuente Gideon para mantener identidad de marca
         * NOTA: Especificamos fontFamily y fontSize directamente porque
         * es el unico lugar que NO debe usar Poppins
         */
        Text(
            text = "EL CLU8",
            fontFamily = Gideon,                            // Fuente especial para el logo
            fontSize = 38.sp,                               // Tamano personalizado
            color = MaterialTheme.colorScheme.secondary     // Color dorado
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "DEL SIE7E",
            fontFamily = Gideon,                            // Fuente especial para el logo
            fontSize = 38.sp,                               // Tamano personalizado
            color = MaterialTheme.colorScheme.secondary     // Color dorado
        )

        Spacer(modifier = Modifier.height(4.dp))

        // ------------------------------------------------------------------
        // ESLOGAN
        // ------------------------------------------------------------------
        /**
         * El eslogan usa Poppins (via MaterialTheme.typography)
         * para mantener consistencia con el resto de la app
         */
        Text(
            text = "EXCLUSIVIDAD . LUJO . JUEGO",
            style = MaterialTheme.typography.labelSmall,    // Usa Poppins automaticamente
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
