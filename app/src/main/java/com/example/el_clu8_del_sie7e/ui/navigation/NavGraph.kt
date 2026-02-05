package com.example.el_clu8_del_sie7e.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.data.repository.AuthRepository
import com.example.el_clu8_del_sie7e.ui.screens.DepositScreen
import com.example.el_clu8_del_sie7e.ui.screens.GameSearchScreen
import com.example.el_clu8_del_sie7e.ui.screens.LobbyScreen
import com.example.el_clu8_del_sie7e.ui.screens.LoginScreen
import com.example.el_clu8_del_sie7e.ui.screens.ProfileScreen
import com.example.el_clu8_del_sie7e.ui.screens.RouletteGameScreen
import com.example.el_clu8_del_sie7e.ui.screens.SlotsScreen
import com.example.el_clu8_del_sie7e.ui.screens.SplashScreen
import com.example.el_clu8_del_sie7e.ui.screens.SupportScreen
import com.example.el_clu8_del_sie7e.ui.screens.TransactionHistoryScreen
import com.example.el_clu8_del_sie7e.ui.screens.WalletScreen
import com.example.el_clu8_del_sie7e.ui.screens.WithdrawScreen
import com.example.el_clu8_del_sie7e.ui.screens.PromocionesScreen
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel

/**
 * =====================================================================================
 * NAVGRAPH.KT - GRAFO DE NAVEGACION DE LA APLICACION CON FIREBASE AUTH
 * =====================================================================================
 *
 * Este archivo define COMO se navega entre las pantallas de la app.
 *
 * FLUJO DE AUTENTICACIÓN:
 * ----------------------
 * 1. SplashScreen (3 seg) verifica si hay sesión activa
 * 2. Si hay sesión -> Navega a Lobby
 * 3. Si NO hay sesión -> Navega a Login
 * 4. Desde Login puede ir a Register o recuperar contraseña
 * 5. Todas las pantallas principales requieren autenticación
 *
 * PERSISTENCIA DE SESIÓN:
 * ----------------------
 * Firebase Auth mantiene la sesión activa automáticamente.
 * Al reiniciar la app, el usuario sigue logueado.
 *
 * =====================================================================================
 */

@Composable
fun NavGraph() {
    // Controlador de navegación
    val navController = rememberNavController()
    
    // ViewModel compartido de Balance
    val balanceViewModel: BalanceViewModel = viewModel()
    
    // Repositorio de autenticación para verificar estado
    val authRepository = AuthRepository.getInstance()
    val currentUser by authRepository.currentUser.collectAsState()

    // Determinar pantalla inicial basada en estado de autenticación
    val startDestination = Routes.SPLASH_SCREEN

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // ==================================================================================
        // PANTALLAS DE AUTENTICACION
        // ==================================================================================

        /**
         * Pantalla de Splash (Bienvenida)
         * - Muestra el logo por 3 segundos
         * - Verifica si hay sesión activa
         * - Navega a Lobby (si hay sesión) o Login (si no hay sesión)
         */
        composable(route = Routes.SPLASH_SCREEN) {
            SplashScreen(
                navController = navController,
                onSplashFinished = {
                    // Verificar si hay usuario logueado
                    if (currentUser != null) {
                        // Hay sesión activa -> Ir al Lobby
                        navController.navigate(Routes.LOBBY_SCREEN) {
                            popUpTo(Routes.SPLASH_SCREEN) { inclusive = true }
                        }
                    } else {
                        // No hay sesión -> Ir a Login
                        navController.navigate(Routes.LOGIN_SCREEN) {
                            popUpTo(Routes.SPLASH_SCREEN) { inclusive = true }
                        }
                    }
                }
            )
        }

        /**
         * Pantalla de Login
         * - El usuario ingresa email y contraseña
         * - Usa Firebase Auth para autenticación
         * - Navega a Lobby si el login es exitoso
         * - Puede navegar a Registro si no tiene cuenta
         */
        composable(route = Routes.LOGIN_SCREEN) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    // Navegar al Lobby y limpiar historial de auth
                    navController.navigate(Routes.LOBBY_SCREEN) {
                        popUpTo(Routes.LOGIN_SCREEN) { inclusive = true }
                    }
                }
            )
        }

        /**
         * Pantalla de Registro
         * - Formulario para crear una nueva cuenta con Firebase
         * - Envía email de verificación
         * - Puede volver al Login si ya tiene cuenta
         */
        composable(route = Routes.REGISTER_SCREEN) {
            com.example.el_clu8_del_sie7e.ui.screens.RegisterScreen(
                navController = navController,
                onRegisterSuccess = {
                    // Después de registrar, ir al Login
                    navController.navigate(Routes.LOGIN_SCREEN) {
                        popUpTo(Routes.REGISTER_SCREEN) { inclusive = true }
                    }
                }
            )
        }

        // ==================================================================================
        // PANTALLAS PRINCIPALES (Requieren autenticación)
        // ==================================================================================

        /**
         * Pantalla de Lobby (Principal)
         * - Pantalla principal después del login
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
         */
        composable(route = Routes.GAME_SEARCH_SCREEN) {
            GameSearchScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        /**
         * Pantalla de Galería de Slots
         */
        composable(route = Routes.SLOTS_GAME_SCREEN) {
            SlotsScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        /**
         * Pantalla de Ruleta de Casino
         */
        composable(route = Routes.ROULETTE_GAME_SCREEN) {
            RouletteGameScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        // ==================================================================================
        // PANTALLAS DE FINANZAS
        // ==================================================================================

        /**
         * Pantalla de Depósito
         */
        composable(route = Routes.DEPOSIT_SCREEN) {
            DepositScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        /**
         * Pantalla de Retiro de Fondos
         */
        composable(route = Routes.WITHDRAW_SCREEN) {
            WithdrawScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        /**
         * Pantalla de Cartera (Wallet)
         */
        composable(route = Routes.WALLET_SCREEN) {
            WalletScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }

        /**
         * Pantalla de Historial de Transacciones
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
         * - Muestra información del usuario
         * - Incluye botón de cerrar sesión
         */
        composable(route = Routes.PROFILE_SCREEN) {
            ProfileScreen(
                navController = navController,
                balanceViewModel = balanceViewModel,
                onLogout = {
                    // Cerrar sesión y volver al Login
                    authRepository.logout()
                    navController.navigate(Routes.LOGIN_SCREEN) {
                        popUpTo(Routes.LOBBY_SCREEN) { inclusive = true }
                    }
                }
            )
        }

        // ==================================================================================
        // PANTALLAS DE CONFIGURACION Y AYUDA
        // ==================================================================================

        /**
         * Pantalla de Soporte y Ayuda
         */
        composable(route = Routes.SUPPORT_SCREEN) {
            SupportScreen(navController = navController)
        }

        /**
         * Pantalla de Promociones
         */
        composable(route = Routes.PROMOTIONS_SCREEN) {
            PromocionesScreen(
                navController = navController,
                balanceViewModel = balanceViewModel
            )
        }
    }
}