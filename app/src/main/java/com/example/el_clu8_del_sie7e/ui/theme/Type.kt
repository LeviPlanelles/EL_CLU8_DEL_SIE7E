package com.example.el_clu8_del_sie7e.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.R

/**
 * =====================================================================================
 * FAMILIAS DE FUENTES DE LA APLICACION
 * =====================================================================================
 *
 * Gideon: Fuente especial para el logo de la app
 * Poppins: Fuente principal para todo el contenido de la app
 * Tomorrow: Fuente para números (balance, cantidades, etc.) fuera de los juegos
 */

/**
 * Fuente Gideon - Solo para el logo "EL CLU8 DEL SIE7E"
 * Esta fuente mantiene la identidad de marca del casino
 */
val Gideon = FontFamily(
    Font(R.font.gideon_roman)
)

/**
 * Fuente Poppins - Fuente principal de la aplicacion
 * 
 * Incluye 7 variantes de peso para maxima flexibilidad:
 * - Light (300): Textos muy sutiles
 * - Regular (400): Texto de cuerpo normal
 * - Medium (500): Enfasis moderado
 * - SemiBold (600): Subtitulos
 * - Bold (700): Titulos importantes
 * - ExtraBold (800): Elementos destacados
 * - Black (900): Maxima enfasis
 */
val Poppins = FontFamily(
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_extrabold, FontWeight.ExtraBold),
    Font(R.font.poppins_black, FontWeight.Black)
)

/**
 * Fuente Tomorrow - Para números fuera de juegos
 * 
 * Se usa específicamente para:
 * - Balance del usuario (en el header)
 * - Cantidades monetarias (depósitos, retiros, historial)
 * - Cualquier número que NO esté dentro de un juego
 * 
 * NOTA: Los números DENTRO de los juegos (slots, blackjack, ruleta)
 * usan Poppins para mantener consistencia visual del juego.
 */
val Tomorrow = FontFamily(
    Font(R.font.tomorrow_regular, FontWeight.Normal),
    Font(R.font.tomorrow_medium, FontWeight.Medium),
    Font(R.font.tomorrow_semibold, FontWeight.SemiBold),
    Font(R.font.tomorrow_bold, FontWeight.Bold)
)
/**
 * =====================================================================================
 * TYPE.KT - CONFIGURACION DE TIPOGRAFIA DE LA APLICACION
 * =====================================================================================
 *
 * Este archivo define los estilos de texto (tipografia) de la app.
 *
 * QUE ES LA TIPOGRAFIA EN COMPOSE?
 * --------------------------------
 * La tipografia define como se ve el texto: tamano, peso (bold/normal),
 * espaciado entre letras, altura de linea, etc.
 *
 * Material Design 3 define varios "roles" de tipografia:
 * - displayLarge/Medium/Small: Textos muy grandes (titulos de pantalla)
 * - headlineLarge/Medium/Small: Encabezados
 * - titleLarge/Medium/Small: Titulos de secciones
 * - bodyLarge/Medium/Small: Texto de contenido normal
 * - labelLarge/Medium/Small: Etiquetas y texto pequeno
 *
 * COMO USAR LA TIPOGRAFIA:
 * ------------------------
 * En cualquier composable dentro del tema:
 *
 * ```kotlin
 * Text(
 *     text = "Hola Mundo",
 *     style = MaterialTheme.typography.titleLarge  // Usa el estilo de titulo
 * )
 * ```
 *
 * UNIDADES DE MEDIDA:
 * -------------------
 * - sp (Scale-independent Pixels): Para tamano de fuente
 *   Se escala segun las preferencias de accesibilidad del usuario
 *
 * =====================================================================================
 */

/**
 * Configuracion de tipografia de la app usando Poppins.
 *
 * IMPORTANTE: Todos los estilos usan la fuente Poppins excepto cuando
 * explicitamente se override con Gideon (solo en el logo).
 *
 * Material Design 3 define roles de tipografia que seguimos aqui:
 * - Display: Texto muy grande (pantallas de bienvenida)
 * - Headline: Encabezados principales
 * - Title: Titulos de secciones y pantallas
 * - Body: Texto de contenido principal
 * - Label: Etiquetas y texto pequeno
 */
val Typography = Typography(

    // ==================================================================================
    // DISPLAY - Textos muy grandes (pantallas especiales)
    // ==================================================================================

    /**
     * Display Large - Texto mas grande de la app
     * Uso: Pantallas de bienvenida, splash screens, mensajes importantes
     */
    displayLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Black,      // Peso Black (900) para maximo impacto
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),

    displayMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),

    displaySmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),

    // ==================================================================================
    // HEADLINE - Encabezados principales
    // ==================================================================================

    /**
     * Headline Large - Encabezados muy grandes
     * Uso: Titulos principales de paginas, secciones importantes
     */
    headlineLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),

    headlineSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // ==================================================================================
    // TITLE - Titulos de secciones
    // ==================================================================================

    /**
     * Title Large - Titulos grandes de pantallas
     * Uso: Titulos de pantallas, nombres de juegos, secciones principales
     * Ejemplo: "Blackjack", "Mi Perfil", "Depositar"
     */
    titleLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,       // Bold (700)
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    /**
     * Title Medium - Titulos medianos
     * Uso: Subtitulos, nombres de secciones secundarias
     */
    titleMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,   // SemiBold (600)
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),

    /**
     * Title Small - Titulos pequenos
     * Uso: Titulos de cards, encabezados de listas
     */
    titleSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,     // Medium (500)
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // ==================================================================================
    // BODY - Texto de contenido
    // ==================================================================================

    /**
     * Body Large - Texto de cuerpo grande
     * Uso: Texto principal de contenido, parrafos, descripciones
     * Ejemplo: Descripciones de juegos, terminos y condiciones, mensajes
     */
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,     // Regular (400)
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    /**
     * Body Medium - Texto de cuerpo mediano
     * Uso: Texto secundario, contenido de menor jerarquia
     */
    bodyMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),

    /**
     * Body Small - Texto de cuerpo pequeno
     * Uso: Texto de apoyo, notas al pie
     */
    bodySmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    // ==================================================================================
    // LABEL - Etiquetas y botones
    // ==================================================================================

    /**
     * Label Large - Etiquetas grandes
     * Uso: Texto de botones, etiquetas destacadas
     * Ejemplo: "INICIAR SESION", "JUGAR AHORA"
     */
    labelLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,   // SemiBold para destacar
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    /**
     * Label Medium - Etiquetas medianas
     * Uso: Etiquetas de campos de texto, tabs, chips
     * Ejemplo: "Usuario", "Contraseña"
     */
    labelMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),

    /**
     * Label Small - Etiquetas pequenas
     * Uso: Texto muy pequeno, avisos, etiquetas secundarias
     * Ejemplo: "EXCLUSIVIDAD . LUJO . JUEGO" debajo del logo
     */
    labelSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

