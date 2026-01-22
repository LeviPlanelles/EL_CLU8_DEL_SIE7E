package com.example.el_clu8_del_sie7e.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * =====================================================================================
 * COLOR.KT - PALETA DE COLORES DE LA APLICACION
 * =====================================================================================
 *
 * Este archivo define TODOS los colores que usaremos en la app.
 *
 * IMPORTANTE PARA JUNIORS:
 * ------------------------
 * 1. NUNCA uses colores directamente en los composables (ej: Color(0xFF123456))
 * 2. SIEMPRE usa los colores definidos aqui para mantener consistencia
 * 3. Si necesitas un nuevo color, agregalo aqui primero
 *
 * COMO FUNCIONAN LOS COLORES EN COMPOSE:
 * --------------------------------------
 * - Los colores se definen usando Color(0xAARRGGBB)
 * - AA = Alpha (transparencia): FF = opaco, 00 = transparente
 * - RR = Rojo (00-FF)
 * - GG = Verde (00-FF)
 * - BB = Azul (00-FF)
 *
 * Ejemplo: Color(0xFFFF0000) = Rojo puro y opaco
 *
 * =====================================================================================
 */

// ======================================================================================
// COLORES PRINCIPALES (Primary Colors)
// Estos son los colores que definen la identidad visual de la app
// ======================================================================================

/**
 * Color primario de la app - Rojo oscuro elegante
 * Se usa para: elementos principales, botones importantes
 */
val PrimaryRed = Color(0xFF6D0000)

/**
 * Variante mas oscura del color primario
 * Se usa para: estados pressed, sombras, acentos
 */
val PrimaryVariantRed = Color(0xFF4B0000)

/**
 * Color de acento - Dorado
 * Se usa para: elementos destacados, iconos, texto importante
 * Este dorado da el toque de lujo y exclusividad del casino
 */
val AccentGold = Color(0xFFFFD700)

// ======================================================================================
// COLORES DE FONDO (Background Colors)
// Colores para los fondos de pantallas y componentes
// ======================================================================================

/**
 * Fondo oscuro principal de la app
 * Se usa para: fondo de pantallas, superficies
 */
val DarkBackground = Color(0xFF1E1E1E)

/**
 * Fondo de los campos de texto y componentes elevados
 * Un poco mas claro que el fondo principal para crear contraste
 */
val SurfaceDark = Color(0xFF2E2E2E)

// ======================================================================================
// COLORES PARA EL GRADIENTE DEL FONDO
// Estos colores crean el efecto de degradado radial en SplashScreen y LoginScreen
// ======================================================================================

/**
 * Color del centro del gradiente (mas claro)
 * Es un rojo vibrante que atrae la atencion
 */
val GradientCenter = Color(0xFF851618)

/**
 * Color del borde del gradiente (mas oscuro)
 * Crea profundidad y el efecto de "viñeta"
 */
val GradientEdge = Color(0xFF2B0C0D)

// ======================================================================================
// COLORES DE TEXTO (Text Colors)
// Colores especificos para el texto
// ======================================================================================

/**
 * Color del texto principal - Blanco
 * Se usa para: titulos, texto importante, texto sobre fondos oscuros
 */
val TextPrimary = Color.White

/**
 * Color del texto secundario - Gris
 * Se usa para: subtitulos, descripciones, texto menos importante
 */
val TextSecondary = Color.Gray
