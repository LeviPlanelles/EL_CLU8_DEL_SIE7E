package com.example.el_clu8_del_sie7e.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * =====================================================================================
 * ROULETTEVIEWMODEL.KT - LÓGICA DEL JUEGO DE RULETA
 * =====================================================================================
 *
 * ViewModel que gestiona toda la lógica del juego de Ruleta Europea.
 *
 * REGLAS DE LA RULETA EUROPEA:
 * ----------------------------
 * - 37 números: 0 (verde) + 1-36 (rojos y negros)
 * - El jugador coloca fichas en la mesa de apuestas
 * - Se gira la ruleta y sale un número aleatorio
 * - Si el número coincide con alguna apuesta, el jugador gana
 *
 * TIPOS DE APUESTAS:
 * ------------------
 * - Número directo (Straight): Paga 35:1
 * - Rojo/Negro: Paga 1:1
 * - Par/Impar: Paga 1:1
 * - 1-18 / 19-36: Paga 1:1
 * - Docenas (1-12, 13-24, 25-36): Paga 2:1
 * - Columnas (2to1): Paga 2:1
 *
 * =====================================================================================
 */

// ===================================================================================
// TIPOS DE APUESTA
// ===================================================================================

/**
 * Tipos de apuesta posibles en la ruleta
 */
enum class RouletteBetType {
    STRAIGHT,    // Número directo (35:1)
    RED,         // Rojo (1:1)
    BLACK,       // Negro (1:1)
    EVEN,        // Par (1:1)
    ODD,         // Impar (1:1)
    LOW,         // 1-18 (1:1)
    HIGH,        // 19-36 (1:1)
    DOZEN_1,     // 1ra docena 1-12 (2:1)
    DOZEN_2,     // 2da docena 13-24 (2:1)
    DOZEN_3,     // 3ra docena 25-36 (2:1)
    COLUMN_1,    // 1ra columna (2:1)
    COLUMN_2,    // 2da columna (2:1)
    COLUMN_3     // 3ra columna (2:1)
}

/**
 * Representa una apuesta individual en la mesa
 */
data class RouletteBet(
    val type: RouletteBetType,
    val number: Int? = null,     // Solo para apuestas STRAIGHT
    val amount: Double,
    val chipValue: Int           // Valor de la ficha usada
)

/**
 * Estado de la ruleta
 */
enum class RouletteState {
    BETTING,     // Fase de apuestas - el jugador coloca fichas
    SPINNING,    // La ruleta está girando
    RESULT       // Mostrando resultado
}

// ===================================================================================
// VIEWMODEL
// ===================================================================================

class RouletteViewModel(
    private val balanceViewModel: BalanceViewModel
) : ViewModel() {

    // ===============================================================================
    // CONSTANTES
    // ===============================================================================

    companion object {
        /** Números rojos en la ruleta europea */
        val RED_NUMBERS = setOf(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36)

        /** Pagos por tipo de apuesta */
        val PAYOUTS = mapOf(
            RouletteBetType.STRAIGHT to 35,
            RouletteBetType.RED to 1,
            RouletteBetType.BLACK to 1,
            RouletteBetType.EVEN to 1,
            RouletteBetType.ODD to 1,
            RouletteBetType.LOW to 1,
            RouletteBetType.HIGH to 1,
            RouletteBetType.DOZEN_1 to 2,
            RouletteBetType.DOZEN_2 to 2,
            RouletteBetType.DOZEN_3 to 2,
            RouletteBetType.COLUMN_1 to 2,
            RouletteBetType.COLUMN_2 to 2,
            RouletteBetType.COLUMN_3 to 2
        )

        /** Tiempo que "gira" la ruleta en ms */
        const val SPIN_DURATION = 2500L

        /** Tiempo mostrando resultado antes de poder apostar otra vez */
        const val RESULT_DISPLAY = 3000L
    }

    // ===============================================================================
    // ESTADOS
    // ===============================================================================

    /** Estado actual del juego */
    private val _gameState = MutableStateFlow(RouletteState.BETTING)
    val gameState: StateFlow<RouletteState> = _gameState.asStateFlow()

    /** Ficha seleccionada actualmente */
    private val _selectedChip = MutableStateFlow(10)
    val selectedChip: StateFlow<Int> = _selectedChip.asStateFlow()

    /** Lista de apuestas actuales en la mesa */
    private val _bets = MutableStateFlow<List<RouletteBet>>(emptyList())
    val bets: StateFlow<List<RouletteBet>> = _bets.asStateFlow()

    /** Total apostado en esta ronda */
    private val _totalBet = MutableStateFlow(0.0)
    val totalBet: StateFlow<Double> = _totalBet.asStateFlow()

    /** Número ganador de la última tirada */
    private val _winningNumber = MutableStateFlow<Int?>(null)
    val winningNumber: StateFlow<Int?> = _winningNumber.asStateFlow()

    /** Historial de números que han salido */
    private val _numberHistory = MutableStateFlow<List<Int>>(emptyList())
    val numberHistory: StateFlow<List<Int>> = _numberHistory.asStateFlow()

    /** Mensaje para la UI */
    private val _message = MutableStateFlow("HAGAN SUS APUESTAS")
    val message: StateFlow<String> = _message.asStateFlow()

    /** Ganancias de la última ronda */
    private val _lastWin = MutableStateFlow(0.0)
    val lastWin: StateFlow<Double> = _lastWin.asStateFlow()

    /** Mapa de fichas colocadas por posición para mostrar en la UI */
    // Clave: "straight_X" para números, "red", "black", "even", "odd", etc.
    private val _chipPositions = MutableStateFlow<Map<String, List<Int>>>(emptyMap())
    val chipPositions: StateFlow<Map<String, List<Int>>> = _chipPositions.asStateFlow()

    /** Guardar apuestas anteriores para repetir */
    private var _lastBets: List<RouletteBet> = emptyList()

    // ===============================================================================
    // SELECCIÓN DE FICHA
    // ===============================================================================

    /**
     * Selecciona la ficha con la que se va a apostar
     */
    fun selectChip(chipValue: Int) {
        _selectedChip.value = chipValue
    }

    // ===============================================================================
    // COLOCACIÓN DE APUESTAS
    // ===============================================================================

    /**
     * Coloca una apuesta en un número directo (0-36)
     */
    fun betOnNumber(number: Int) {
        if (_gameState.value != RouletteState.BETTING) return
        val chipValue = _selectedChip.value
        if (!balanceViewModel.hasSufficientFunds(chipValue.toDouble())) {
            _message.value = "¡SALDO INSUFICIENTE!"
            return
        }

        // Descontar del balance
        balanceViewModel.withdraw(chipValue.toDouble(), "Apuesta Ruleta #$number")

        // Añadir apuesta
        val bet = RouletteBet(
            type = RouletteBetType.STRAIGHT,
            number = number,
            amount = chipValue.toDouble(),
            chipValue = chipValue
        )
        _bets.value = _bets.value + bet
        _totalBet.value += chipValue

        // Actualizar posiciones de fichas en la UI
        val key = "straight_$number"
        val currentChips = _chipPositions.value.toMutableMap()
        currentChips[key] = (currentChips[key] ?: emptyList()) + chipValue
        _chipPositions.value = currentChips

        _message.value = "APUESTA: \$${_totalBet.value.toInt()}"
    }

    /**
     * Coloca una apuesta en una opción externa (rojo, negro, par, impar, etc.)
     */
    fun betOnOption(betType: RouletteBetType) {
        if (_gameState.value != RouletteState.BETTING) return
        if (betType == RouletteBetType.STRAIGHT) return // Usar betOnNumber para directos
        val chipValue = _selectedChip.value
        if (!balanceViewModel.hasSufficientFunds(chipValue.toDouble())) {
            _message.value = "¡SALDO INSUFICIENTE!"
            return
        }

        // Descontar del balance
        balanceViewModel.withdraw(chipValue.toDouble(), "Apuesta Ruleta ${betType.name}")

        val bet = RouletteBet(
            type = betType,
            amount = chipValue.toDouble(),
            chipValue = chipValue
        )
        _bets.value = _bets.value + bet
        _totalBet.value += chipValue

        // Actualizar posiciones de fichas
        val key = betType.name.lowercase()
        val currentChips = _chipPositions.value.toMutableMap()
        currentChips[key] = (currentChips[key] ?: emptyList()) + chipValue
        _chipPositions.value = currentChips

        _message.value = "APUESTA: \$${_totalBet.value.toInt()}"
    }

    // ===============================================================================
    // GIRAR LA RULETA
    // ===============================================================================

    /**
     * Gira la ruleta y calcula el resultado
     */
    fun spin() {
        if (_gameState.value != RouletteState.BETTING) return
        if (_bets.value.isEmpty()) {
            _message.value = "¡COLOCA UNA APUESTA!"
            return
        }

        viewModelScope.launch {
            // Fase de giro
            _gameState.value = RouletteState.SPINNING
            _message.value = "¡NO VA MÁS!"

            // Generar número aleatorio (0-36)
            val result = (0..36).random()

            delay(SPIN_DURATION)

            // Mostrar resultado
            _winningNumber.value = result
            _numberHistory.value = listOf(result) + _numberHistory.value.take(19) // Máximo 20

            // Calcular ganancias
            var totalWinnings = 0.0
            for (bet in _bets.value) {
                if (isBetWinner(bet, result)) {
                    val payout = PAYOUTS[bet.type] ?: 0
                    // Ganancia = apuesta + (apuesta * multiplicador)
                    totalWinnings += bet.amount + (bet.amount * payout)
                }
            }

            // Depositar ganancias
            if (totalWinnings > 0) {
                balanceViewModel.deposit(totalWinnings, "Premio Ruleta")
                _lastWin.value = totalWinnings
                val colorName = getNumberColorName(result)
                _message.value = "¡$result $colorName! GANASTE \$${totalWinnings.toInt()}"
            } else {
                _lastWin.value = 0.0
                val colorName = getNumberColorName(result)
                _message.value = "$result $colorName - PERDISTE \$${_totalBet.value.toInt()}"
            }

            // Guardar apuestas para repetir
            _lastBets = _bets.value.toList()

            _gameState.value = RouletteState.RESULT
        }
    }

    // ===============================================================================
    // ACCIONES DE MESA
    // ===============================================================================

    /**
     * Deshace la última apuesta colocada
     */
    fun undoLastBet() {
        if (_gameState.value != RouletteState.BETTING) return
        if (_bets.value.isEmpty()) return

        val lastBet = _bets.value.last()
        _bets.value = _bets.value.dropLast(1)
        _totalBet.value -= lastBet.amount

        // Devolver al balance
        balanceViewModel.deposit(lastBet.amount, "Deshacer apuesta Ruleta")

        // Actualizar posiciones de fichas
        val key = if (lastBet.type == RouletteBetType.STRAIGHT) {
            "straight_${lastBet.number}"
        } else {
            lastBet.type.name.lowercase()
        }
        val currentChips = _chipPositions.value.toMutableMap()
        val chipList = currentChips[key]?.toMutableList()
        if (chipList != null && chipList.isNotEmpty()) {
            chipList.removeAt(chipList.lastIndex)
            if (chipList.isEmpty()) {
                currentChips.remove(key)
            } else {
                currentChips[key] = chipList
            }
        }
        _chipPositions.value = currentChips

        if (_bets.value.isEmpty()) {
            _message.value = "HAGAN SUS APUESTAS"
        } else {
            _message.value = "APUESTA: \$${_totalBet.value.toInt()}"
        }
    }

    /**
     * Limpia todas las apuestas de la mesa
     */
    fun clearAllBets() {
        if (_gameState.value != RouletteState.BETTING) return

        // Devolver todo al balance
        for (bet in _bets.value) {
            balanceViewModel.deposit(bet.amount, "Limpiar apuesta Ruleta")
        }

        _bets.value = emptyList()
        _totalBet.value = 0.0
        _chipPositions.value = emptyMap()
        _message.value = "HAGAN SUS APUESTAS"
    }

    /**
     * Repite las apuestas de la ronda anterior
     */
    fun repeatLastBets() {
        if (_gameState.value != RouletteState.BETTING) return
        if (_lastBets.isEmpty()) {
            _message.value = "NO HAY APUESTA ANTERIOR"
            return
        }

        // Limpiar apuestas actuales primero
        clearAllBets()

        // Re-colocar las apuestas anteriores
        for (bet in _lastBets) {
            if (!balanceViewModel.hasSufficientFunds(bet.amount)) {
                _message.value = "¡SALDO INSUFICIENTE!"
                break
            }
            balanceViewModel.withdraw(bet.amount, "Repetir Ruleta")
            _bets.value = _bets.value + bet
            _totalBet.value += bet.amount

            val key = if (bet.type == RouletteBetType.STRAIGHT) {
                "straight_${bet.number}"
            } else {
                bet.type.name.lowercase()
            }
            val currentChips = _chipPositions.value.toMutableMap()
            currentChips[key] = (currentChips[key] ?: emptyList()) + bet.chipValue
            _chipPositions.value = currentChips
        }

        if (_bets.value.isNotEmpty()) {
            _message.value = "APUESTA: \$${_totalBet.value.toInt()}"
        }
    }

    /**
     * Inicia una nueva ronda (limpia mesa para apostar de nuevo)
     */
    fun newRound() {
        _gameState.value = RouletteState.BETTING
        _bets.value = emptyList()
        _totalBet.value = 0.0
        _chipPositions.value = emptyMap()
        _lastWin.value = 0.0
        _winningNumber.value = null
        _message.value = "HAGAN SUS APUESTAS"
    }

    // ===============================================================================
    // LÓGICA INTERNA
    // ===============================================================================

    /**
     * Verifica si una apuesta es ganadora para un número resultado
     */
    private fun isBetWinner(bet: RouletteBet, result: Int): Boolean {
        return when (bet.type) {
            RouletteBetType.STRAIGHT -> bet.number == result
            RouletteBetType.RED -> result in RED_NUMBERS
            RouletteBetType.BLACK -> result > 0 && result !in RED_NUMBERS
            RouletteBetType.EVEN -> result > 0 && result % 2 == 0
            RouletteBetType.ODD -> result > 0 && result % 2 != 0
            RouletteBetType.LOW -> result in 1..18
            RouletteBetType.HIGH -> result in 19..36
            RouletteBetType.DOZEN_1 -> result in 1..12
            RouletteBetType.DOZEN_2 -> result in 13..24
            RouletteBetType.DOZEN_3 -> result in 25..36
            RouletteBetType.COLUMN_1 -> result > 0 && result % 3 == 1
            RouletteBetType.COLUMN_2 -> result > 0 && result % 3 == 2
            RouletteBetType.COLUMN_3 -> result > 0 && result % 3 == 0
        }
    }

    /**
     * Devuelve el nombre del color de un número
     */
    private fun getNumberColorName(number: Int): String {
        return when {
            number == 0 -> "VERDE"
            number in RED_NUMBERS -> "ROJO"
            else -> "NEGRO"
        }
    }

    /**
     * Devuelve si un número es rojo
     */
    fun isRed(number: Int): Boolean = number in RED_NUMBERS

    /**
     * Devuelve si un número es negro
     */
    fun isBlack(number: Int): Boolean = number > 0 && number !in RED_NUMBERS

    /**
     * Obtiene la cantidad total apostada en una posición específica
     */
    fun getTotalBetOnPosition(key: String): Int {
        return _chipPositions.value[key]?.sum() ?: 0
    }
}
