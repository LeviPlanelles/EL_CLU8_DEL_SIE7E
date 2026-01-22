package com.example.el_clu8_del_sie7e.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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
 * Configuracion de tipografia de la app.
 *
 * Solo definimos los estilos que usamos. Material 3 tiene valores por defecto
 * para los que no especificamos.
 */
val Typography = Typography(

    // ==================================================================================
    // TEXTO DE CUERPO (Body) - Para contenido general
    // ==================================================================================

    /**
     * Estilo para texto de cuerpo grande.
     * Uso: Texto principal de contenido, parrafos, descripciones largas.
     */
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,    // Fuente del sistema
        fontWeight = FontWeight.Normal,     // Peso normal (no negrita)
        fontSize = 16.sp,                   // Tamano 16sp (tamano estandar de lectura)
        lineHeight = 24.sp,                 // Altura de linea (1.5x el tamano)
        letterSpacing = 0.5.sp              // Pequeno espaciado entre letras
    ),

    // ==================================================================================
    // TITULOS (Title) - Para encabezados y secciones
    // ==================================================================================

    /**
     * Estilo para titulos grandes.
     * Uso: Nombre de la app, titulos principales de pantalla.
     *
     * Ejemplo: "EL CLU8 DEL SIE7E" en el logo
     */
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,       // NEGRITA para destacar
        fontSize = 28.sp,                   // Tamano grande
        lineHeight = 36.sp,
        letterSpacing = 0.sp                // Sin espaciado extra
    ),

    // ==================================================================================
    // ETIQUETAS (Label) - Para texto pequeno
    // ==================================================================================

    /**
     * Estilo para etiquetas pequenas.
     * Uso: Texto secundario, etiquetas de campos, avisos pequenos.
     *
     * Ejemplo: "EXCLUSIVIDAD . LUJO . JUEGO" debajo del logo
     */
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,     // Peso medio
        fontSize = 11.sp,                   // Tamano pequeno
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
