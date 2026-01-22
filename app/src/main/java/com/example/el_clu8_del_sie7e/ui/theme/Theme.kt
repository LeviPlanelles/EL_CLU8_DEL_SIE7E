package com.example.el_clu8_del_sie7e.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

/**
 * =====================================================================================
 * THEME.KT - CONFIGURACION DEL TEMA DE LA APLICACION
 * =====================================================================================
 *
 * Este archivo configura el tema global de Material Design 3 para nuestra app.
 *
 * QUE ES UN TEMA EN COMPOSE?
 * --------------------------
 * Un tema es un conjunto de configuraciones (colores, tipografia, formas) que se
 * aplican a TODA la app de forma consistente. Es como definir las "reglas de estilo"
 * que todos los componentes seguiran automaticamente.
 *
 * COMO FUNCIONA?
 * --------------
 * 1. Definimos un ColorScheme con nuestros colores personalizados
 * 2. Creamos una funcion @Composable que envuelve MaterialTheme
 * 3. Esa funcion se usa en MainActivity para envolver toda la app
 *
 * BENEFICIOS:
 * -----------
 * - Consistencia visual en toda la app
 * - Facil de mantener (cambias un color aqui y cambia en toda la app)
 * - Los componentes de Material usan estos colores automaticamente
 *
 * =====================================================================================
 */

/**
 * Esquema de colores oscuro para la app.
 *
 * Material Design 3 define roles de color especificos:
 * - primary: Color principal (nuestro rojo)
 * - secondary: Color secundario/acento (nuestro dorado)
 * - background: Color de fondo de las pantallas
 * - surface: Color de superficies elevadas (cards, dialogs, etc.)
 * - onPrimary: Color del texto/iconos sobre el color primary
 * - onSecondary: Color del texto/iconos sobre el color secondary
 * - onBackground: Color del texto sobre el background
 * - onSurface: Color del texto sobre surfaces
 *
 * El prefijo "on" significa "encima de". Por ejemplo, onPrimary es el color
 * que se usa para texto que esta encima de un fondo de color primary.
 */
private val DarkColorScheme = darkColorScheme(
    // Colores principales
    primary = PrimaryRed,           // Rojo oscuro para elementos principales
    secondary = AccentGold,         // Dorado para acentos y elementos destacados

    // Colores de fondo
    background = DarkBackground,    // Fondo oscuro de las pantallas
    surface = DarkBackground,       // Superficies (cards, sheets, etc.)

    // Colores de texto e iconos
    onPrimary = TextPrimary,        // Texto blanco sobre fondo rojo
    onSecondary = TextPrimary,      // Texto blanco sobre fondo dorado
    onBackground = TextPrimary,     // Texto blanco sobre fondo oscuro
    onSurface = TextPrimary,        // Texto blanco sobre superficies
)

/**
 * Funcion principal del tema de la aplicacion.
 *
 * COMO USARLA:
 * ------------
 * Esta funcion se llama en MainActivity y envuelve TODA la app:
 *
 * ```kotlin
 * setContent {
 *     EL_CLU8_DEL_SIE7ETheme {
 *         // Todo el contenido de la app va aqui
 *         NavGraph()
 *     }
 * }
 * ```
 *
 * ACCEDER A LOS COLORES DESDE CUALQUIER COMPOSABLE:
 * -------------------------------------------------
 * Una vez dentro del tema, puedes acceder a los colores asi:
 *
 * ```kotlin
 * val colorPrimario = MaterialTheme.colorScheme.primary
 * val colorDorado = MaterialTheme.colorScheme.secondary
 * val colorFondo = MaterialTheme.colorScheme.background
 * ```
 *
 * @param content El contenido de la app que usara este tema
 */
@Composable
fun EL_CLU8_DEL_SIE7ETheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,  // Aplicamos nuestros colores
        typography = Typography,         // Aplicamos nuestra tipografia (de Type.kt)
        content = content                // El contenido de la app
    )
}
