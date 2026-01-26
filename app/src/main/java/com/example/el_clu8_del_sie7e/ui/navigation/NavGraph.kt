package com.example.el_clu8_del_sie7e.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.screens.LobbyScreen
import com.example.el_clu8_del_sie7e.ui.screens.LoginScreen
import com.example.el_clu8_del_sie7e.ui.screens.SplashScreen

/**
 * =====================================================================================
 * NAVGRAPH.KT - GRAFO DE NAVEGACION DE LA APLICACION
 * =====================================================================================
 *
 * Este archivo define COMO se navega entre las pantallas de la app.
 *
 * QUE ES UN GRAFO DE NAVEGACION?
 * ------------------------------
 * Es un "mapa" que define:
 * 1. Que pantallas existen en la app
 * 2. Como se puede ir de una pantalla a otra
 * 3. Cual es la pantalla inicial
 *
 * COMPONENTES PRINCIPALES:
 * ------------------------
 *
 * NavController:
 * - Es el "controlador" de la navegacion
 * - Guarda el historial de pantallas (back stack)
 * - Permite navegar a otras pantallas con navigate()
 * - Permite volver atras con popBackStack()
 *
 * NavHost:
 * - Es el "contenedor" donde se muestran las pantallas
 * - Define todas las rutas posibles y sus pantallas
 * - Solo muestra UNA pantalla a la vez
 *
 * composable():
 * - Asocia una ruta (String) con un Composable (pantalla)
 * - Cuando navegas a esa ruta, se muestra ese Composable
 *
 * COMO AGREGAR UNA NUEVA PANTALLA:
 * --------------------------------
 * 1. Crear el archivo de la pantalla en ui/screens/
 * 2. Agregar la ruta en Routes.kt
 * 3. Agregar el composable aqui en el NavHost
 *
 * Ejemplo:
 * ```kotlin
 * composable(Routes.MI_PANTALLA) {
 *     MiPantallaScreen(navController = navController)
 * }
 * ```
 *
 * COMO NAVEGAR ENTRE PANTALLAS:
 * -----------------------------
 * Desde cualquier pantalla que tenga acceso al navController:
 *
 * ```kotlin
 * // Ir a otra pantalla (se agrega al historial)
 * navController.navigate(Routes.OTRA_PANTALLA)
 *
 * // Ir a otra pantalla y borrar el historial (no se puede volver atras)
 * navController.navigate(Routes.OTRA_PANTALLA) {
 *     popUpTo(Routes.PANTALLA_ACTUAL) { inclusive = true }
 * }
 *
 * // Volver a la pantalla anterior
 * navController.popBackStack()
 * ```
 *
 * =====================================================================================
 */

/**
 * Composable principal que define la navegacion de toda la app.
 *
 * Este composable se llama desde MainActivity y contiene todas las pantallas.
 *
 * FLUJO DE NAVEGACION ACTUAL:
 * ---------------------------
 * SplashScreen (3 seg) --> LoginScreen --> [Proximas pantallas]
 */
@Composable
fun NavGraph() {
    // Creamos el controlador de navegacion
    // rememberNavController() asegura que sobreviva a recomposiciones
    val navController = rememberNavController()

    // NavHost es el contenedor de todas las pantallas
    NavHost(
        navController = navController,          // El controlador que gestiona la navegacion
        startDestination = Routes.SPLASH_SCREEN // La primera pantalla que se muestra
    ) {
        // ==================================================================================
        // PANTALLAS DE AUTENTICACION
        // ==================================================================================

        /**
         * Pantalla de Splash (Bienvenida)
         * - Primera pantalla que ve el usuario
         * - Muestra el logo por 3 segundos
         * - Navega automaticamente al Login
         */
        composable(route = Routes.SPLASH_SCREEN) {
            SplashScreen(navController = navController)
        }

        /**
         * Pantalla de Login
         * - El usuario ingresa sus credenciales
         * - Puede navegar a Registro si no tiene cuenta
         */
        composable(route = Routes.LOGIN_SCREEN) {
            LoginScreen(navController = navController)
        }

        // ==================================================================================
        // PROXIMAS PANTALLAS (agregar aqui cuando se implementen)
        // ==================================================================================

        /**
         * Pantalla de Registro
         * - Formulario para crear una nueva cuenta
         * - Puede volver al Login si ya tiene cuenta
         */
        composable(route = Routes.REGISTER_SCREEN) {
            com.example.el_clu8_del_sie7e.ui.screens.RegisterScreen(navController = navController)
        }

        // ==================================================================================
        // PANTALLAS PRINCIPALES
        // ==================================================================================

        /**
         * Pantalla de Lobby (Principal)
         * - Pantalla principal despues del login
         * - Muestra juegos destacados, bonos y navegacion
         */
        composable(route = Routes.LOBBY_SCREEN) {
            LobbyScreen(navController = navController)
        }
    }
}
