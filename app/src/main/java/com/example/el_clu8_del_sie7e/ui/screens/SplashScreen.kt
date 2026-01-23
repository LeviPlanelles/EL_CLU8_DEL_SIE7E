package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.navigation.Routes
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.GradientCenter
import com.example.el_clu8_del_sie7e.ui.theme.GradientEdge
import kotlinx.coroutines.delay

/**
 * =====================================================================================
 * SPLASHSCREEN.KT - PANTALLA DE BIENVENIDA
 * =====================================================================================
 *
 * Esta es la primera pantalla que ve el usuario al abrir la app.
 * Se muestra durante 3 segundos y luego navega automaticamente al Login.
 *
 * PROPOSITO:
 * ----------
 * 1. Mostrar la marca/logo de la app
 * 2. Dar tiempo para cargar recursos si es necesario
 * 3. Crear una primera impresion visual atractiva
 *
 * CONCEPTOS DE COMPOSE QUE SE USAN AQUI:
 * --------------------------------------
 *
 * 1. LaunchedEffect:
 *    - Es un "efecto secundario" que se ejecuta UNA vez cuando el composable aparece
 *    - Perfecto para operaciones asincronas como delays, llamadas a API, etc.
 *    - El key1 = true significa que se ejecuta solo al inicio
 *
 * 2. Brush.radialGradient:
 *    - Crea un gradiente circular (degradado radial)
 *    - El color del centro se difumina hacia el color del borde
 *    - radius controla que tan grande es el gradiente
 *
 * 3. Box:
 *    - Es como un "contenedor" que permite superponer elementos
 *    - contentAlignment centra todo su contenido
 *
 * 4. Column:
 *    - Organiza elementos verticalmente (uno debajo de otro)
 *
 * 5. Spacer:
 *    - Agrega espacio vacio entre elementos
 *
 * =====================================================================================
 */

/**
 * Pantalla de Splash (Bienvenida)
 *
 * @param navController Controlador de navegacion para ir a la siguiente pantalla
 *
 * NOTA: Esta pantalla necesita el navController para poder navegar automaticamente
 * al LoginScreen despues de 3 segundos.
 */
@Composable
fun SplashScreen(navController: NavController) {

    // ====================================================================================
    // EFECTO DE NAVEGACION AUTOMATICA
    // ====================================================================================
    /**
     * LaunchedEffect se ejecuta cuando el composable entra en la composicion.
     *
     * key1 = true: El efecto solo se ejecuta una vez (al inicio)
     *
     * FLUJO:
     * 1. Espera 3 segundos (3000 milisegundos)
     * 2. Limpia el back stack (para que no se pueda volver a splash)
     * 3. Navega al Login
     */
    LaunchedEffect(key1 = true) {
        // Esperamos 3 segundos mostrando el splash
        delay(3000)

        // Navegamos al Login y quitamos el Splash del historial
        // Esto evita que el usuario pueda volver al splash presionando "atras"
        navController.navigate(Routes.LOGIN_SCREEN) {
            // popUpTo elimina pantallas del historial hasta llegar a SPLASH_SCREEN
            // inclusive = true significa que tambien elimina SPLASH_SCREEN
            popUpTo(Routes.SPLASH_SCREEN) { inclusive = true }
        }
    }

    // ====================================================================================
    // INTERFAZ DE USUARIO (UI)
    // ====================================================================================
    /**
     * Box como contenedor principal con el fondo gradiente
     *
     * El fondo usa un Brush.radialGradient que crea el efecto de
     * "luz en el centro" caracteristico de la app
     */
    Box(
        modifier = Modifier
            .fillMaxSize()  // Ocupa toda la pantalla
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        GradientCenter,  // Rojo claro en el centro (0xFF851618)
                        GradientEdge     // Rojo oscuro en los bordes (0xFF2B0C0D)
                    ),
                    radius = 900f  // Radio del gradiente (ajustar segun necesidad)
                )
            ),
        contentAlignment = Alignment.Center  // Centra todo el contenido
    ) {
        // Columna que organiza: Icono -> Texto -> Loader
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ------------------------------------------------------------------
            // ICONO/LOGO
            // ------------------------------------------------------------------
            /**
             * TODO: Reemplazar Icons.Default.Star con el icono de corona real
             *
             * Para usar un icono personalizado:
             * 1. Agregar el archivo SVG/PNG en res/drawable
             * 2. Usar painterResource(id = R.drawable.mi_icono)
             *
             * Ejemplo:
             * Icon(
             *     painter = painterResource(id = R.drawable.ic_crown),
             *     contentDescription = "Corona",
             *     tint = AccentGold
             * )
             */
            Icon(
                imageVector = Icons.Default.Star,  // Placeholder - cambiar por corona
                contentDescription = "Corona",     // Descripcion para accesibilidad
                tint = AccentGold,                 // Color dorado del tema
                modifier = Modifier.size(64.dp)    // Tamano del icono
            )

            // Espacio entre el icono y el texto
            Spacer(modifier = Modifier.height(16.dp))

            // ------------------------------------------------------------------
            // NOMBRE DE LA APP
            // ------------------------------------------------------------------
            /**
             * Usa headlineLarge de MaterialTheme para consistencia tipografica
             * Esto aplicara automaticamente la fuente Poppins configurada en Type.kt
             */
            Text(
                text = "EL CLU8 DEL SIE7E",
                color = AccentGold,                         // Color dorado
                style = MaterialTheme.typography.headlineLarge  // Estilo consistente con el tema
            )

            // Espacio entre el texto y el loader
            Spacer(modifier = Modifier.height(32.dp))

            // ------------------------------------------------------------------
            // INDICADOR DE CARGA
            // ------------------------------------------------------------------
            /**
             * CircularProgressIndicator muestra una animacion de carga
             * Le da al usuario feedback de que la app esta "haciendo algo"
             */
            CircularProgressIndicator(
                color = AccentGold,                // Color dorado
                modifier = Modifier.size(48.dp)   // Tamano del indicador
            )
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
/**
 * Preview para ver la pantalla en Android Studio sin ejecutar la app.
 *
 * COMO USAR:
 * - En Android Studio, haz click en el icono de "Split" o "Design" a la derecha
 * - Veras la preview de este composable
 *
 * NOTA: Usamos rememberNavController() porque SplashScreen requiere un navController,
 * aunque en la preview no funciona la navegacion.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        SplashScreen(navController = rememberNavController())
    }
}
