package com.example.el_clu8_del_sie7e.ui.navigation

/**
 * =====================================================================================
 * ROUTES.KT - DEFINICION DE RUTAS DE NAVEGACION
 * =====================================================================================
 *
 * Este archivo define TODAS las rutas (URLs internas) de la app.
 *
 * QUE ES UNA RUTA?
 * ----------------
 * Una ruta es un identificador unico (String) que representa una pantalla.
 * Es como la direccion de una pagina web, pero dentro de la app.
 *
 * POR QUE USAR UN OBJECT?
 * -----------------------
 * Usamos un `object` (singleton) para:
 * 1. Tener un unico lugar donde definir todas las rutas
 * 2. Evitar errores de tipeo (typos) al escribir las rutas
 * 3. Facilitar el autocompletado en el IDE
 *
 * COMO AGREGAR UNA NUEVA RUTA:
 * ----------------------------
 * 1. Agrega una nueva constante aqui con el nombre de la pantalla
 * 2. Usa esa constante en NavGraph.kt para definir la navegacion
 *
 * Ejemplo:
 * ```kotlin
 * const val MI_NUEVA_PANTALLA = "mi_nueva_pantalla"
 * ```
 *
 * CONVENCIONES DE NOMBRES:
 * ------------------------
 * - Constante: MAYUSCULAS_CON_GUIONES_BAJOS (ej: SPLASH_SCREEN)
 * - Valor: minusculas_con_guiones_bajos (ej: "splash_screen")
 *
 * =====================================================================================
 */
object Routes {

    // ==================================================================================
    // PANTALLAS DE AUTENTICACION
    // ==================================================================================

    /**
     * Pantalla de bienvenida (Splash)
     * Se muestra al iniciar la app por 3 segundos
     */
    const val SPLASH_SCREEN = "splash_screen"

    /**
     * Pantalla de inicio de sesion
     * Donde el usuario ingresa su usuario y contrasena
     */
    const val LOGIN_SCREEN = "login_screen"

    /**
     * Pantalla de registro
     * Donde nuevos usuarios crean su cuenta
     */
    const val REGISTER_SCREEN = "register_screen"

    // ==================================================================================
    // PANTALLAS PRINCIPALES
    // ==================================================================================

    /**
     * Lobby principal
     * Pantalla principal donde el usuario ve los juegos disponibles
     */
    const val LOBBY_SCREEN = "lobby_screen"

    // ==================================================================================
    // PANTALLAS DE JUEGOS
    // ==================================================================================

    /** Juego de Blackjack */
    const val BLACKJACK_GAME_SCREEN = "blackjack_game_screen"

    /** Juego de Tragaperras/Slots */
    const val SLOTS_GAME_SCREEN = "slots_game_screen"

    /** Juego de Ruleta */
    const val ROULETTE_GAME_SCREEN = "roulette_game_screen"

    /** Buscador de Juegos */
    const val GAME_SEARCH_SCREEN = "game_search_screen"

    // ==================================================================================
    // PANTALLAS DE FINANZAS
    // ==================================================================================

    /** Pantalla para depositar fondos */
    const val DEPOSIT_SCREEN = "deposit_screen"

    /** Pantalla para retirar fondos */
    const val WITHDRAW_SCREEN = "withdraw_screen"

    /** Historial de transacciones */
    const val TRANSACTION_HISTORY_SCREEN = "transaction_history_screen"

    // ==================================================================================
    // PANTALLAS DE USUARIO
    // ==================================================================================

    /** Perfil del usuario */
    const val PROFILE_SCREEN = "profile_screen"

    /** Bonos y promociones */
    const val BONUSES_SCREEN = "bonuses_screen"

    /** Torneos disponibles */
    const val TOURNAMENTS_SCREEN = "tournaments_screen"

    // ==================================================================================
    // PANTALLAS DE CONFIGURACION
    // ==================================================================================

    /** Ayuda y soporte */
    const val SUPPORT_SCREEN = "support_screen"

    /** Ajustes de la aplicacion */
    const val SETTINGS_SCREEN = "settings_screen"
}
