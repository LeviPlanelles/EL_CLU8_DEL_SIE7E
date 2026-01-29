package com.example.el_clu8_del_sie7e.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.screens.DepositScreen
import com.example.el_clu8_del_sie7e.ui.screens.GameSearchScreen
import com.example.el_clu8_del_sie7e.ui.screens.LobbyScreen
import com.example.el_clu8_del_sie7e.ui.screens.LoginScreen
import com.example.el_clu8_del_sie7e.ui.screens.ProfileScreen
import com.example.el_clu8_del_sie7e.ui.screens.SlotsScreen
import com.example.el_clu8_del_sie7e.ui.screens.SplashScreen
import com.example.el_clu8_del_sie7e.ui.screens.SupportScreen
import com.example.el_clu8_del_sie7e.ui.screens.TransactionHistoryScreen
import com.example.el_clu8_del_sie7e.ui.screens.WalletScreen
import com.example.el_clu8_del_sie7e.ui.screens.WithdrawScreen
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel

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
    
    // ===================================================================
    // VIEWMODEL COMPARTIDO DE BALANCE
    // ===================================================================
    // Creamos UNA SOLA instancia del BalanceViewModel a nivel de NavGraph
    // para que sea compartida por TODAS las pantallas de la aplicación.
    // Esto asegura que el balance se mantenga sincronizado en toda la app.
    val balanceViewModel: BalanceViewModel = viewModel()

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
            LobbyScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        // ==================================================================================
        // PANTALLAS DE JUEGOS
        // ==================================================================================

        /**
         * Pantalla de Búsqueda de Juegos
         * - Permite buscar y filtrar juegos
         * - Muestra juegos populares con calificación
         * - Filtros por categoría (Todos, Slots, Cartas, Otros)
         */
        composable(route = Routes.GAME_SEARCH_SCREEN) {
            GameSearchScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        /**
         * Pantalla de Galería de Slots
         * - Muestra todos los juegos de slots disponibles
         * - Grid de 2 columnas con scroll vertical
         * - 6 slots: Neon Fortune, Golden Empire, Inferno Fortunes, Zeus, Bonus Slot 1, Bonus Slot 2
         */
        composable(route = Routes.SLOTS_GAME_SCREEN) {
            SlotsScreen(navController = navController)
        }

        // ==================================================================================
        // PANTALLAS DE FINANZAS
        // ==================================================================================

        /**
         * Pantalla de Depósito
         * - Permite depositar fondos en la cuenta
         * - Selección de método de pago (Tarjeta, Transferencia, E-Wallet)
         * - Montos rápidos y formulario de tarjeta
         */
        composable(route = Routes.DEPOSIT_SCREEN) {
            DepositScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        /**
         * Pantalla de Retiro de Fondos
         * - Permite retirar ganancias de la cuenta
         * - Selección de método de retiro (Transferencia, Visa/Mastercard, Cripto)
         * - Muestra saldo disponible y límites
         * - Sin comisiones
         */
        composable(route = Routes.WITHDRAW_SCREEN) {
            WithdrawScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        /**
         * Pantalla de Cartera (Wallet)
         * - Pantalla principal de gestión financiera
         * - Opciones: Depositar, Retirar, Historial
         * - Banner de seguridad premium
         */
        composable(route = Routes.WALLET_SCREEN) {
            WalletScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        /**
         * Pantalla de Historial de Transacciones
         * - Muestra todas las transacciones del usuario
         * - Filtros: Todos, Depósitos, Retirados, Ganados
         * - Transacciones agrupadas por fecha (HOY, AYER, etc.)
         * - Indicadores de estado: EXITOSO, COMPLETADO, PENDIENTE, CANCELADA
         */
        composable(route = Routes.TRANSACTION_HISTORY_SCREEN) {
            TransactionHistoryScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        // ==================================================================================
        // PANTALLAS DE USUARIO
        // ==================================================================================

        /**
         * Pantalla de Perfil
         * - Muestra información del usuario (foto, nombre, nivel)
         * - Estadísticas: Saldo Actual y Puntos VIP
         * - Opciones: Datos Personales, Seguridad, Límites de Juego
         * - Botón de cerrar sesión
         */
        composable(route = Routes.PROFILE_SCREEN) {
            ProfileScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        // ==================================================================================
        // PANTALLAS DE CONFIGURACION Y AYUDA
        // ==================================================================================

        /**
         * Pantalla de Soporte y Ayuda
         * - Canales de atención: Chat en Vivo y Tickets
         * - FAQs (Preguntas Frecuentes) expandibles
         * - Información de contacto telefónico
         * - Accesible desde el icono de ayuda en ProfileScreen
         */
        composable(route = Routes.SUPPORT_SCREEN) {
            SupportScreen(navController = navController)
        }
    }
}
