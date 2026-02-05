package com.example.el_clu8_del_sie7e.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.el_clu8_del_sie7e.util.SoundManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * =====================================================================================
 * SLOTGAMEVIEWMODEL.KT - LÓGICA DEL JUEGO DE TRAGAMONEDAS
 * =====================================================================================
 *
 * ViewModel que gestiona toda la lógica del juego de slots "Zeus Slot".
 *
 * CARACTERÍSTICAS:
 * ----------------
 * - 5 columnas x 3 filas de símbolos
 * - 5 símbolos diferentes con distintos valores de pago
 * - Detección de victorias (3, 4 o 5 símbolos iguales en línea horizontal)
 * - Sistema de apuestas con validación de saldo
 * - Auto-roll con multiplicadores x3, x5, x10
 * - Animación de giro con revelación progresiva
 *
 * SÍMBOLOS Y PAGOS:
 * -----------------
 * - ⭐ Estrella: x2 (3), x5 (4), x10 (5)
 * - ❤️ Corazón: x3 (3), x8 (4), x15 (5)
 * - ⚡ Rayo: x5 (3), x12 (4), x25 (5)
 * - 🏛️ Columna (Zeus): x10 (3), x25 (4), x50 (5)
 * - 💎 Diamante: x15 (3), x40 (4), x100 (5)
 *
 * =====================================================================================
 */

// ===================================================================================
// MODELOS DE DATOS
// ===================================================================================

/**
 * Enum que representa los símbolos del slot
 * Cada símbolo tiene un identificador único y un peso de probabilidad
 */
enum class SlotSymbol(val id: String, val weight: Int) {
    STAR("star", 30),       // ⭐ Más común
    HEART("heart", 25),     // ❤️ Común
    BOLT("bolt", 20),       // ⚡ Medio
    TEMPLE("temple", 15),   // 🏛️ Raro
    DIAMOND("diamond", 10); // 💎 Muy raro

    companion object {
        /**
         * Genera un símbolo aleatorio basado en los pesos de probabilidad
         * Los símbolos más comunes tienen mayor probabilidad de aparecer
         */
        fun random(): SlotSymbol {
            val totalWeight = entries.sumOf { it.weight }
            var randomValue = Random.nextInt(totalWeight)
            
            for (symbol in entries) {
                randomValue -= symbol.weight
                if (randomValue < 0) return symbol
            }
            return STAR // Fallback
        }
    }
}

/**
 * Tabla de pagos: define el multiplicador por símbolo y cantidad
 * Clave: Pair(símbolo, cantidad de matches) -> Valor: multiplicador
 */
object PayTable {
    private val payouts = mapOf(
        // Estrella - símbolo más común, pagos bajos
        Pair(SlotSymbol.STAR, 3) to 2,
        Pair(SlotSymbol.STAR, 4) to 5,
        Pair(SlotSymbol.STAR, 5) to 10,
        
        // Corazón - pagos medios-bajos
        Pair(SlotSymbol.HEART, 3) to 3,
        Pair(SlotSymbol.HEART, 4) to 8,
        Pair(SlotSymbol.HEART, 5) to 15,
        
        // Rayo - pagos medios
        Pair(SlotSymbol.BOLT, 3) to 5,
        Pair(SlotSymbol.BOLT, 4) to 12,
        Pair(SlotSymbol.BOLT, 5) to 25,
        
        // Templo (Zeus) - pagos altos
        Pair(SlotSymbol.TEMPLE, 3) to 10,
        Pair(SlotSymbol.TEMPLE, 4) to 25,
        Pair(SlotSymbol.TEMPLE, 5) to 50,
        
        // Diamante - pagos muy altos (jackpot)
        Pair(SlotSymbol.DIAMOND, 3) to 15,
        Pair(SlotSymbol.DIAMOND, 4) to 40,
        Pair(SlotSymbol.DIAMOND, 5) to 100
    )

    /**
     * Obtiene el multiplicador de pago para un símbolo y cantidad de matches
     * @return multiplicador o 0 si no hay premio
     */
    fun getMultiplier(symbol: SlotSymbol, matchCount: Int): Int {
        return payouts[Pair(symbol, matchCount)] ?: 0
    }
}

/**
 * Estado del juego
 */
enum class GameState {
    IDLE,       // Esperando acción del jugador
    SPINNING,   // Rodillos girando
    REVEALING,  // Revelando resultado
    WIN,        // Victoria - mostrando premio
    LOSE        // Derrota - sin premio
}

/**
 * Resultado de una línea ganadora
 */
data class WinLine(
    val rowIndex: Int,          // Fila donde ocurrió la victoria (0, 1, 2)
    val symbol: SlotSymbol,     // Símbolo ganador
    val matchCount: Int,        // Cantidad de símbolos consecutivos (3, 4, 5)
    val multiplier: Int,        // Multiplicador de pago
    val winAmount: Double       // Cantidad ganada
)

/**
 * Resultado completo de un giro
 */
data class SpinResult(
    val reels: List<List<SlotSymbol>>,  // Estado final de los rodillos [fila][columna]
    val winLines: List<WinLine>,         // Líneas ganadoras
    val totalWin: Double,                // Total ganado
    val isWin: Boolean                   // Si hubo alguna victoria
)

// ===================================================================================
// VIEWMODEL
// ===================================================================================

class SlotGameViewModel(
    application: Application,
    private val balanceViewModel: BalanceViewModel
) : AndroidViewModel(application) {
    
    // Inicializar SoundManager
    init {
        SoundManager.initialize(application)
    }

    // ===============================================================================
    // CONSTANTES
    // ===============================================================================
    
    companion object {
        const val ROWS = 3          // Filas de símbolos
        const val COLS = 5          // Columnas (rodillos)
        const val MIN_BET = 1       // Apuesta mínima
        const val SPIN_DELAY = 150L // Delay entre revelación de columnas (ms)
    }

    // ===============================================================================
    // ESTADOS
    // ===============================================================================
    
    /** Estado actual del juego */
    private val _gameState = MutableStateFlow(GameState.IDLE)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    /** Apuesta actual del jugador */
    private val _currentBet = MutableStateFlow(5)
    val currentBet: StateFlow<Int> = _currentBet.asStateFlow()

    /** Chip seleccionado para incremento/decremento */
    private val _selectedChip = MutableStateFlow(5)
    val selectedChip: StateFlow<Int> = _selectedChip.asStateFlow()

    /** Multiplicador de auto-roll seleccionado (0 = desactivado) */
    private val _autoRollMultiplier = MutableStateFlow(0)
    val autoRollMultiplier: StateFlow<Int> = _autoRollMultiplier.asStateFlow()

    /** Tiradas restantes en auto-roll */
    private val _autoRollRemaining = MutableStateFlow(0)
    val autoRollRemaining: StateFlow<Int> = _autoRollRemaining.asStateFlow()

    /** Estado actual de los rodillos (3 filas x 5 columnas) */
    private val _reels = MutableStateFlow(generateInitialReels())
    val reels: StateFlow<List<List<SlotSymbol>>> = _reels.asStateFlow()

    /** Columnas reveladas durante la animación (0-5) */
    private val _revealedColumns = MutableStateFlow(COLS) // Todas visibles inicialmente
    val revealedColumns: StateFlow<Int> = _revealedColumns.asStateFlow()

    /** Último resultado del giro */
    private val _lastSpinResult = MutableStateFlow<SpinResult?>(null)
    val lastSpinResult: StateFlow<SpinResult?> = _lastSpinResult.asStateFlow()

    /** Mensaje para mostrar al usuario */
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    /** Última cantidad ganada (para animación) */
    private val _lastWinAmount = MutableStateFlow(0.0)
    val lastWinAmount: StateFlow<Double> = _lastWinAmount.asStateFlow()

    /** Job para auto-roll (para poder cancelarlo) */
    private var autoRollJob: Job? = null

    // ===============================================================================
    // INICIALIZACIÓN
    // ===============================================================================

    /**
     * Genera el estado inicial de los rodillos con símbolos aleatorios
     */
    private fun generateInitialReels(): List<List<SlotSymbol>> {
        return List(ROWS) { List(COLS) { SlotSymbol.random() } }
    }

    // ===============================================================================
    // GESTIÓN DE APUESTAS
    // ===============================================================================

    /**
     * Selecciona un chip de apuesta rápida
     * Establece la apuesta actual al valor del chip
     */
    fun selectChip(chipValue: Int) {
        if (_gameState.value != GameState.IDLE) return
        
        _selectedChip.value = chipValue
        setBet(chipValue)
    }

    /**
     * Establece la apuesta directamente
     * Valida que esté entre MIN_BET y el balance disponible
     */
    fun setBet(amount: Int) {
        if (_gameState.value != GameState.IDLE) return
        
        val maxBet = balanceViewModel.balance.value.toInt()
        _currentBet.value = amount.coerceIn(MIN_BET, maxOf(MIN_BET, maxBet))
    }

    /**
     * Incrementa la apuesta por el valor del chip seleccionado
     */
    fun increaseBet() {
        if (_gameState.value != GameState.IDLE) return
        
        val newBet = _currentBet.value + _selectedChip.value
        val maxBet = balanceViewModel.balance.value.toInt()
        _currentBet.value = minOf(newBet, maxOf(MIN_BET, maxBet))
    }

    /**
     * Decrementa la apuesta por el valor del chip seleccionado
     */
    fun decreaseBet() {
        if (_gameState.value != GameState.IDLE) return
        
        val newBet = _currentBet.value - _selectedChip.value
        _currentBet.value = maxOf(newBet, MIN_BET)
    }

    /**
     * Apuesta máxima (todo el saldo disponible)
     */
    fun setMaxBet() {
        if (_gameState.value != GameState.IDLE) return
        
        val maxBet = balanceViewModel.balance.value.toInt()
        if (maxBet >= MIN_BET) {
            _currentBet.value = maxBet
        }
    }

    // ===============================================================================
    // SISTEMA DE GIRO
    // ===============================================================================

    /**
     * Ejecuta un giro de los rodillos
     * - Verifica saldo suficiente
     * - Descuenta la apuesta
     * - Genera resultado aleatorio
     * - Detecta victorias
     * - Paga premios
     */
    fun spin() {
        // Validaciones
        if (_gameState.value != GameState.IDLE) return
        
        val bet = _currentBet.value.toDouble()
        
        // Verificar saldo suficiente
        if (!balanceViewModel.hasSufficientFunds(bet)) {
            _message.value = "Saldo insuficiente"
            return
        }

        // Descontar apuesta
        balanceViewModel.withdraw(bet, "Apuesta Zeus Slot")

        // Iniciar giro
        viewModelScope.launch {
            _gameState.value = GameState.SPINNING
            _message.value = ""
            _lastWinAmount.value = 0.0
            _revealedColumns.value = 0
            
            // SONIDO: Inicio del giro
            SoundManager.playSpin()

            // Animación de giro - mostrar símbolos aleatorios rápidamente
            repeat(10) {
                _reels.value = generateInitialReels()
                delay(50)
            }

            // Generar resultado final
            val finalReels = generateInitialReels()
            _reels.value = finalReels

            // Revelar columnas progresivamente
            _gameState.value = GameState.REVEALING
            for (col in 1..COLS) {
                _revealedColumns.value = col
                // SONIDO: Cada vez que se revela una columna
                SoundManager.playReelStop()
                delay(SPIN_DELAY)
            }

            // Detectar victorias
            val winLines = detectWins(finalReels, bet)
            val totalWin = winLines.sumOf { it.winAmount }
            val isWin = winLines.isNotEmpty()

            // Guardar resultado
            _lastSpinResult.value = SpinResult(
                reels = finalReels,
                winLines = winLines,
                totalWin = totalWin,
                isWin = isWin
            )

            // Actualizar estado y balance
            if (isWin) {
                _gameState.value = GameState.WIN
                _lastWinAmount.value = totalWin
                _message.value = "!GANASTE ${String.format("%.2f", totalWin)}!"
                
                // SONIDO: Victoria según el monto
                val isBigWin = totalWin >= 50
                val isJackpot = totalWin >= 100
                SoundManager.playWin(totalWin)
                
                // SONIDO: Efecto de monedas cayendo
                if (isBigWin || isJackpot) {
                    repeat(5) {
                        delay(200)
                        SoundManager.playCoinDrop()
                    }
                }
                
                // Depositar ganancias
                balanceViewModel.deposit(totalWin, "Premio Zeus Slot")
            } else {
                _gameState.value = GameState.LOSE
                _message.value = "Sin premio"
                // SONIDO: Derrota
                SoundManager.playLose()
            }

            // Esperar un momento para mostrar resultado (las animaciones visuales duran 3 segundos)
            delay(3000)

            // Volver a estado idle o continuar auto-roll
            _gameState.value = GameState.IDLE
            
            // Si hay auto-roll activo, continuar
            handleAutoRollContinue()
        }
    }

    /**
     * Detecta las líneas ganadoras en los rodillos
     * Una línea gana si tiene 3, 4 o 5 símbolos iguales consecutivos en CUALQUIER posición
     * (no solo desde la izquierda, también en el medio o derecha)
     */
    private fun detectWins(reels: List<List<SlotSymbol>>, bet: Double): List<WinLine> {
        val winLines = mutableListOf<WinLine>()

        // Revisar cada fila (3 filas = 3 líneas de pago)
        for (rowIndex in 0 until ROWS) {
            val row = reels[rowIndex]
            
            // Buscar la mejor secuencia de símbolos consecutivos en cualquier posición
            var bestMatchCount = 0
            var bestSymbol: SlotSymbol? = null
            
            var currentSymbol = row[0]
            var currentCount = 1
            
            // Recorrer la fila buscando secuencias consecutivas
            for (colIndex in 1 until COLS) {
                if (row[colIndex] == currentSymbol) {
                    // Mismo símbolo, incrementar contador
                    currentCount++
                } else {
                    // Símbolo diferente, verificar si la secuencia anterior es la mejor
                    if (currentCount > bestMatchCount) {
                        bestMatchCount = currentCount
                        bestSymbol = currentSymbol
                    }
                    // Reiniciar con el nuevo símbolo
                    currentSymbol = row[colIndex]
                    currentCount = 1
                }
            }
            
            // Verificar la última secuencia (puede terminar al final de la fila)
            if (currentCount > bestMatchCount) {
                bestMatchCount = currentCount
                bestSymbol = currentSymbol
            }

            // Si hay al menos 3 símbolos iguales consecutivos, es victoria
            if (bestMatchCount >= 3 && bestSymbol != null) {
                val multiplier = PayTable.getMultiplier(bestSymbol, bestMatchCount)
                val winAmount = bet * multiplier

                winLines.add(
                    WinLine(
                        rowIndex = rowIndex,
                        symbol = bestSymbol,
                        matchCount = bestMatchCount,
                        multiplier = multiplier,
                        winAmount = winAmount
                    )
                )
            }
        }

        return winLines
    }

    // ===============================================================================
    // SISTEMA DE AUTO-ROLL
    // ===============================================================================

    /**
     * Activa/desactiva el multiplicador de auto-roll
     */
    fun toggleAutoRollMultiplier(multiplier: Int) {
        if (_gameState.value != GameState.IDLE) return
        
        _autoRollMultiplier.value = if (_autoRollMultiplier.value == multiplier) 0 else multiplier
        _autoRollRemaining.value = 0 // Resetear contador
    }

    /**
     * Inicia el auto-roll con el multiplicador seleccionado
     */
    fun startAutoRoll() {
        if (_gameState.value != GameState.IDLE) return
        if (_autoRollMultiplier.value == 0) return

        _autoRollRemaining.value = _autoRollMultiplier.value
        spin() // Iniciar primer giro
    }

    /**
     * Detiene el auto-roll
     */
    fun stopAutoRoll() {
        autoRollJob?.cancel()
        _autoRollRemaining.value = 0
        _autoRollMultiplier.value = 0
    }

    /**
     * Continúa el auto-roll si quedan tiradas
     */
    private fun handleAutoRollContinue() {
        if (_autoRollRemaining.value > 0) {
            _autoRollRemaining.value--
            
            if (_autoRollRemaining.value > 0) {
                // Verificar que aún hay saldo
                val bet = _currentBet.value.toDouble()
                if (balanceViewModel.hasSufficientFunds(bet)) {
                    viewModelScope.launch {
                        delay(500) // Pausa entre giros automáticos
                        spin()
                    }
                } else {
                    _message.value = "Auto-roll detenido: saldo insuficiente"
                    _autoRollRemaining.value = 0
                    _autoRollMultiplier.value = 0
                }
            } else {
                // Auto-roll completado
                _autoRollMultiplier.value = 0
            }
        }
    }

    // ===============================================================================
    // UTILIDADES
    // ===============================================================================

    /**
     * Limpia el mensaje actual
     */
    fun clearMessage() {
        _message.value = ""
    }

    /**
     * Verifica si se puede girar (saldo suficiente y estado idle)
     */
    fun canSpin(): Boolean {
        return _gameState.value == GameState.IDLE && 
               balanceViewModel.hasSufficientFunds(_currentBet.value.toDouble())
    }

    /**
     * Genera un resultado predefinido para testing
     * Útil para probar combinaciones específicas
     */
    fun spinWithResult(predefinedReels: List<List<SlotSymbol>>) {
        if (_gameState.value != GameState.IDLE) return
        
        val bet = _currentBet.value.toDouble()
        if (!balanceViewModel.hasSufficientFunds(bet)) return

        balanceViewModel.withdraw(bet, "Apuesta Zeus Slot")

        viewModelScope.launch {
            _gameState.value = GameState.SPINNING
            _revealedColumns.value = 0

            delay(300)

            _reels.value = predefinedReels
            
            _gameState.value = GameState.REVEALING
            for (col in 1..COLS) {
                _revealedColumns.value = col
                delay(SPIN_DELAY)
            }

            val winLines = detectWins(predefinedReels, bet)
            val totalWin = winLines.sumOf { it.winAmount }
            val isWin = winLines.isNotEmpty()

            _lastSpinResult.value = SpinResult(
                reels = predefinedReels,
                winLines = winLines,
                totalWin = totalWin,
                isWin = isWin
            )

            if (isWin) {
                _gameState.value = GameState.WIN
                _lastWinAmount.value = totalWin
                _message.value = "!GANASTE ${String.format("%.2f", totalWin)}!"
                balanceViewModel.deposit(totalWin, "Premio Zeus Slot")
            } else {
                _gameState.value = GameState.LOSE
                _message.value = "Sin premio"
            }

            delay(1500)
            _gameState.value = GameState.IDLE
        }
    }
}
