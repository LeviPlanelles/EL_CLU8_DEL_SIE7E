package com.example.el_clu8_del_sie7e.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * =====================================================================================
 * ZEUSSLOTVIEWMODEL.KT - LÓGICA DE NEGOCIO PARA LA TRAGAPERRAS ZEUS SLOT
 * =====================================================================================
 *
 * Este ViewModel maneja toda la lógica del juego de tragaperras:
 * - Sistema de apuestas con botones rápidos
 * - Generación aleatoria de símbolos
 * - Cálculo de ganancias según combinaciones
 * - Sistema de multiplicadores para combinaciones especiales
 * - Auto-roll (repetir apuesta automáticamente)
 * - Gestión del balance del jugador
 *
 * SÍMBOLOS DE LA TRAGAPERRAS:
 * ---------------------------
 * 🌟 Estrella (Star) - Multiplicador: x5
 * ❤️ Corazón (Heart) - Multiplicador: x4
 * ⚡ Rayo (Lightning) - Multiplicador: x3
 * 🎁 Regalo (Gift) - Multiplicador: x2
 * 💎 Diamante (Diamond) - Multiplicador: x10 (JACKPOT)
 * 7️⃣ Siete (Seven) - Símbolo por defecto
 *
 * REGLAS DE PAGO:
 * ---------------
 * - 5 símbolos iguales: Apuesta x Multiplicador del símbolo x 100
 * - 4 símbolos iguales: Apuesta x Multiplicador del símbolo x 20
 * - 3 símbolos iguales: Apuesta x Multiplicador del símbolo x 5
 * - 2 símbolos iguales: Apuesta x Multiplicador del símbolo x 1
 *
 * =====================================================================================
 */

/**
 * Enum que representa los diferentes símbolos de la tragaperras
 */
enum class SlotSymbol(val emoji: String, val multiplier: Int) {
    STAR("⭐", 5),       // Estrella
    HEART("❤️", 4),      // Corazón
    LIGHTNING("⚡", 3),  // Rayo
    GIFT("🎁", 2),       // Regalo
    DIAMOND("💎", 10),   // Diamante (JACKPOT)
    SEVEN("7", 1)       // Siete (por defecto en pantalla)
}

/**
 * Estados posibles del resultado de un giro
 */
enum class SpinResult {
    IDLE,       // No hay resultado (estado inicial)
    WIN,        // Ganó
    LOSE,       // Perdió
    SPINNING    // Girando
}

/**
 * ViewModel para la pantalla de Zeus Slot
 */
class ZeusSlotViewModel : ViewModel() {

    // ===================================================================
    // ESTADO DEL JUEGO
    // ===================================================================
    
    // Balance del jugador
    private val _balance = MutableStateFlow(5000.0)
    val balance: StateFlow<Double> = _balance.asStateFlow()

    // Apuesta actual (inicia en 0.0, sin ningún botón seleccionado)
    private val _currentBet = MutableStateFlow(0.0)
    val currentBet: StateFlow<Double> = _currentBet.asStateFlow()

    // Ganancias de la última tirada
    private val _winnings = MutableStateFlow(120.0)
    val winnings: StateFlow<Double> = _winnings.asStateFlow()

    // Símbolos actuales en los 5 rodillos
    private val _reels = MutableStateFlow(List(5) { SlotSymbol.SEVEN })
    val reels: StateFlow<List<SlotSymbol>> = _reels.asStateFlow()

    // Estado del giro (IDLE, SPINNING, WIN, LOSE)
    private val _spinState = MutableStateFlow(SpinResult.IDLE)
    val spinState: StateFlow<SpinResult> = _spinState.asStateFlow()

    // Bandera de si está girando actualmente
    private val _isSpinning = MutableStateFlow(false)
    val isSpinning: StateFlow<Boolean> = _isSpinning.asStateFlow()

    // Auto-roll activado
    private val _autoRoll = MutableStateFlow(false)
    val autoRoll: StateFlow<Boolean> = _autoRoll.asStateFlow()

    // Mensaje del resultado
    private val _resultMessage = MutableStateFlow("")
    val resultMessage: StateFlow<String> = _resultMessage.asStateFlow()

    // ===================================================================
    // FUNCIONES DE APUESTA
    // ===================================================================

    /**
     * Establece la apuesta actual
     */
    fun setBet(amount: Double) {
        if (!_isSpinning.value && amount <= _balance.value) {
            _currentBet.value = amount
        }
    }

    /**
     * Incrementa la apuesta en una cantidad específica
     */
    fun incrementBet(amount: Double) {
        val newBet = _currentBet.value + amount
        if (newBet <= _balance.value) {
            _currentBet.value = newBet
        }
    }

    /**
     * Decrementa la apuesta en una cantidad específica
     */
    fun decrementBet(amount: Double) {
        val newBet = _currentBet.value - amount
        if (newBet >= 1.0) {
            _currentBet.value = newBet
        }
    }

    /**
     * Activa/desactiva el auto-roll
     */
    fun toggleAutoRoll() {
        _autoRoll.value = !_autoRoll.value
    }

    // ===================================================================
    // FUNCIÓN PRINCIPAL: GIRAR LA TRAGAPERRAS
    // ===================================================================

    /**
     * Gira la tragaperras y calcula el resultado
     */
    suspend fun spin() {
        // Validar que no esté girando y que tenga suficiente balance
        if (_isSpinning.value || _currentBet.value > _balance.value) {
            return
        }

        // Iniciar el giro
        _isSpinning.value = true
        _spinState.value = SpinResult.SPINNING
        _resultMessage.value = ""

        // Descontar la apuesta del balance
        _balance.value -= _currentBet.value

        // Simular el tiempo de giro (1.5 segundos)
        kotlinx.coroutines.delay(1500)

        // Generar símbolos aleatorios para los 5 rodillos
        val newReels = generateRandomReels()
        _reels.value = newReels

        // Calcular el resultado y las ganancias
        val result = calculateWinnings(newReels)
        
        if (result > 0) {
            // GANÓ
            _winnings.value = result
            _balance.value += result
            _spinState.value = SpinResult.WIN
            _resultMessage.value = "¡GANASTE $${String.format("%.2f", result)}!"
        } else {
            // PERDIÓ
            _spinState.value = SpinResult.LOSE
            _resultMessage.value = "Sin premio. ¡Inténtalo de nuevo!"
        }

        // Finalizar el giro
        _isSpinning.value = false

        // Si auto-roll está activado, volver a girar después de 2 segundos
        if (_autoRoll.value && _currentBet.value <= _balance.value) {
            kotlinx.coroutines.delay(2000)
            spin()
        }
    }

    // ===================================================================
    // FUNCIÓN AUXILIAR: GENERAR SÍMBOLOS ALEATORIOS
    // ===================================================================

    /**
     * Genera 5 símbolos aleatorios para los rodillos
     * 
     * Probabilidades:
     * - DIAMOND (💎): 5% (más raro, mayor premio)
     * - STAR (⭐): 15%
     * - HEART (❤️): 20%
     * - LIGHTNING (⚡): 25%
     * - GIFT (🎁): 35% (más común, menor premio)
     */
    private fun generateRandomReels(): List<SlotSymbol> {
        return List(5) {
            val random = (1..100).random()
            when {
                random <= 5 -> SlotSymbol.DIAMOND   // 5%
                random <= 20 -> SlotSymbol.STAR     // 15%
                random <= 40 -> SlotSymbol.HEART    // 20%
                random <= 65 -> SlotSymbol.LIGHTNING // 25%
                else -> SlotSymbol.GIFT             // 35%
            }
        }
    }

    // ===================================================================
    // FUNCIÓN AUXILIAR: CALCULAR GANANCIAS
    // ===================================================================

    /**
     * Calcula las ganancias según los símbolos obtenidos
     * 
     * Reglas:
     * - 5 iguales: Apuesta x Multiplicador x 100
     * - 4 iguales: Apuesta x Multiplicador x 20
     * - 3 iguales: Apuesta x Multiplicador x 5
     * - 2 iguales: Apuesta x Multiplicador x 1
     */
    private fun calculateWinnings(reels: List<SlotSymbol>): Double {
        // Agrupar los símbolos por tipo y contar cuántos hay de cada uno
        val symbolCounts = reels.groupingBy { it }.eachCount()
        
        // Encontrar el símbolo más repetido
        val maxCount = symbolCounts.maxByOrNull { it.value }
        
        if (maxCount == null || maxCount.value < 2) {
            // No hay ninguna combinación ganadora
            return 0.0
        }

        // Calcular las ganancias según la cantidad de símbolos iguales
        val symbol = maxCount.key
        val count = maxCount.value
        val multiplier = symbol.multiplier

        val baseWinning = when (count) {
            5 -> _currentBet.value * multiplier * 100  // 5 iguales: x100
            4 -> _currentBet.value * multiplier * 20   // 4 iguales: x20
            3 -> _currentBet.value * multiplier * 5    // 3 iguales: x5
            2 -> _currentBet.value * multiplier * 1    // 2 iguales: x1
            else -> 0.0
        }

        return baseWinning
    }

    // ===================================================================
    // FUNCIÓN AUXILIAR: RESETEAR RESULTADO
    // ===================================================================

    /**
     * Resetea el estado del resultado (útil para limpiar mensajes)
     */
    fun resetResult() {
        _spinState.value = SpinResult.IDLE
        _resultMessage.value = ""
    }

    // ===================================================================
    // FUNCIÓN DE DEBUG: AGREGAR BALANCE (SOLO PARA PRUEBAS)
    // ===================================================================

    /**
     * Agrega balance al jugador (solo para testing)
     */
    fun addBalance(amount: Double) {
        _balance.value += amount
    }
}
