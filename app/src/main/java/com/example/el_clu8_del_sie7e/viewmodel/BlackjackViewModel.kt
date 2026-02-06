package com.example.el_clu8_del_sie7e.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.el_clu8_del_sie7e.ui.components.blackjack.Card
import com.example.el_clu8_del_sie7e.ui.components.blackjack.CardSuit
import com.example.el_clu8_del_sie7e.ui.components.blackjack.CardValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * =====================================================================================
 * BLACKJACKVIEWMODEL.KT - LÓGICA DEL JUEGO DE BLACKJACK
 * =====================================================================================
 *
 * ViewModel que gestiona toda la lógica del juego de Blackjack.
 *
 * REGLAS DEL BLACKJACK:
 * ---------------------
 * - El objetivo es conseguir 21 puntos o acercarse lo más posible sin pasarse
 * - Las cartas numéricas valen su número (2-10)
 * - Las figuras (J, Q, K) valen 10
 * - El As vale 11, pero si te pasas de 21, vale 1
 * - Blackjack natural (As + 10/J/Q/K) paga 3:2
 * - Si el jugador se pasa de 21, pierde automáticamente
 * - El dealer debe pedir con 16 o menos y plantarse con 17 o más
 *
 * ACCIONES DISPONIBLES:
 * ---------------------
 * - PEDIR (Hit): Recibir una carta adicional
 * - PLANTARSE (Stand): Terminar el turno
 * - DOBLAR (Double Down): Doblar la apuesta y recibir una sola carta más
 * - DIVIDIR (Split): Separar una pareja en dos manos (si las cartas son iguales)
 *
 * =====================================================================================
 */

// ===================================================================================
// ESTADOS DEL JUEGO
// ===================================================================================

/**
 * Estado del juego de Blackjack
 */
enum class BlackjackState {
    BETTING,        // Esperando que el jugador apueste
    DEALING,        // Repartiendo cartas iniciales
    PLAYER_TURN,    // Turno del jugador
    DEALER_TURN,    // Turno del dealer
    PLAYER_BLACKJACK, // El jugador tiene Blackjack natural
    PLAYER_BUST,    // El jugador se pasó de 21
    DEALER_BUST,    // El dealer se pasó de 21
    PLAYER_WINS,    // El jugador gana
    DEALER_WINS,    // El dealer gana
    PUSH            // Empate
}

/**
 * Resultado del juego para mostrar en la UI
 */
enum class GameResult {
    NONE,
    PLAYER_BLACKJACK,
    PLAYER_WINS,
    DEALER_WINS,
    PUSH,
    PLAYER_BUST,
    DEALER_BUST
}

// ===================================================================================
// BARAJA DE CARTAS
// ===================================================================================

/**
 * Clase que representa una baraja de cartas
 * Contiene 52 cartas (13 valores x 4 palos)
 */
class Deck {
    private val cards = mutableListOf<Card>()
    
    init {
        reset()
    }
    
    /**
     * Reinicia y baraja la baraja
     */
    fun reset() {
        cards.clear()
        // Crear todas las cartas (52 cartas)
        for (suit in CardSuit.entries) {
            for (value in CardValue.entries) {
                cards.add(Card(value = value, suit = suit, isFaceUp = true))
            }
        }
        shuffle()
    }
    
    /**
     * Baraja las cartas
     */
    fun shuffle() {
        cards.shuffle()
    }
    
    /**
     * Saca una carta de la baraja
     * @param faceUp Si la carta debe mostrarse boca arriba
     */
    fun draw(faceUp: Boolean = true): Card {
        if (cards.isEmpty()) {
            reset()
        }
        return cards.removeAt(0).copy(isFaceUp = faceUp)
    }
    
    /**
     * Cantidad de cartas restantes
     */
    fun remaining(): Int = cards.size
}

// ===================================================================================
// VIEWMODEL
// ===================================================================================

class BlackjackViewModel(
    private val balanceViewModel: BalanceViewModel
) : ViewModel() {

    // ===============================================================================
    // CONSTANTES
    // ===============================================================================
    
    companion object {
        val CHIP_VALUES = listOf(1, 5, 10, 25, 50, 100, 200, 500)
        const val MIN_BET = 1
        const val BLACKJACK_PAYOUT = 1.5  // Pago 3:2 para Blackjack natural
        const val NORMAL_PAYOUT = 1.0     // Pago 1:1 para victoria normal
        const val DEALER_STAND = 17       // Dealer se planta con 17 o más
        const val DEAL_DELAY = 400L       // Delay entre cartas al repartir (ms)
        const val DEALER_DELAY = 800L     // Delay entre acciones del dealer (ms)
    }

    // ===============================================================================
    // BARAJA
    // ===============================================================================
    
    private val deck = Deck()

    // ===============================================================================
    // ESTADOS
    // ===============================================================================
    
    /** Estado actual del juego */
    private val _gameState = MutableStateFlow(BlackjackState.BETTING)
    val gameState: StateFlow<BlackjackState> = _gameState.asStateFlow()

    /** Apuesta actual */
    private val _currentBet = MutableStateFlow(0.0)
    val currentBet: StateFlow<Double> = _currentBet.asStateFlow()

    /** Chip seleccionado actualmente */
    private val _selectedChip = MutableStateFlow(10)
    val selectedChip: StateFlow<Int> = _selectedChip.asStateFlow()

    /** Cartas del jugador */
    private val _playerCards = MutableStateFlow<List<Card>>(emptyList())
    val playerCards: StateFlow<List<Card>> = _playerCards.asStateFlow()

    /** Cartas del dealer */
    private val _dealerCards = MutableStateFlow<List<Card>>(emptyList())
    val dealerCards: StateFlow<List<Card>> = _dealerCards.asStateFlow()

    /** Puntuación del jugador */
    private val _playerScore = MutableStateFlow(0)
    val playerScore: StateFlow<Int> = _playerScore.asStateFlow()

    /** Puntuación del dealer (solo cuenta cartas boca arriba) */
    private val _dealerScore = MutableStateFlow(0)
    val dealerScore: StateFlow<Int> = _dealerScore.asStateFlow()

    /** Resultado del juego */
    private val _gameResult = MutableStateFlow(GameResult.NONE)
    val gameResult: StateFlow<GameResult> = _gameResult.asStateFlow()

    /** Mensaje para mostrar al usuario */
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    /** Última cantidad ganada/perdida */
    private val _lastWinAmount = MutableStateFlow(0.0)
    val lastWinAmount: StateFlow<Double> = _lastWinAmount.asStateFlow()

    /** Si se puede doblar (solo con 2 cartas iniciales) */
    private val _canDouble = MutableStateFlow(false)
    val canDouble: StateFlow<Boolean> = _canDouble.asStateFlow()

    /** Si se puede dividir (solo si las dos cartas tienen el mismo valor) */
    private val _canSplit = MutableStateFlow(false)
    val canSplit: StateFlow<Boolean> = _canSplit.asStateFlow()

    // ===============================================================================
    // GESTIÓN DE APUESTAS
    // ===============================================================================

    /**
     * Selecciona un chip de apuesta
     */
    fun selectChip(chipValue: Int) {
        if (_gameState.value != BlackjackState.BETTING) return
        _selectedChip.value = chipValue
    }

    /**
     * Agrega la cantidad del chip seleccionado a la apuesta
     */
    fun addBet() {
        if (_gameState.value != BlackjackState.BETTING) return
        
        val chipValue = _selectedChip.value.toDouble()
        val maxBet = balanceViewModel.balance.value
        val newBet = (_currentBet.value + chipValue).coerceAtMost(maxBet)
        
        _currentBet.value = newBet
    }

    /**
     * Agrega una cantidad específica a la apuesta
     */
    fun addBetAmount(amount: Int) {
        if (_gameState.value != BlackjackState.BETTING) return
        
        val maxBet = balanceViewModel.balance.value
        val newBet = (_currentBet.value + amount).coerceAtMost(maxBet)
        
        _currentBet.value = newBet
    }

    /**
     * Limpia la apuesta actual
     */
    fun clearBet() {
        if (_gameState.value != BlackjackState.BETTING) return
        _currentBet.value = 0.0
    }

    /**
     * Apuesta máxima (all-in)
     */
    fun maxBet() {
        if (_gameState.value != BlackjackState.BETTING) return
        _currentBet.value = balanceViewModel.balance.value
    }

    // ===============================================================================
    // LÓGICA DEL JUEGO
    // ===============================================================================

    /**
     * Inicia una nueva mano
     * - Verifica que haya apuesta
     * - Descuenta la apuesta del balance
     * - Reparte las cartas iniciales
     */
    fun deal() {
        // Validaciones
        if (_gameState.value != BlackjackState.BETTING) {
            android.util.Log.d("BlackjackVM", "deal() - No está en fase de apuestas")
            return
        }
        if (_currentBet.value < MIN_BET) {
            _message.value = "¡COLOCA UNA APUESTA!"
            android.util.Log.d("BlackjackVM", "deal() - Apuesta insuficiente: ${_currentBet.value}")
            return
        }
        if (!balanceViewModel.hasSufficientFunds(_currentBet.value)) {
            _message.value = "¡SALDO INSUFICIENTE!"
            android.util.Log.d("BlackjackVM", "deal() - Saldo insuficiente")
            return
        }

        android.util.Log.d("BlackjackVM", "deal() - Iniciando juego con apuesta: ${_currentBet.value}")
        
        // Descontar apuesta
        balanceViewModel.withdraw(_currentBet.value, "Apuesta Blackjack")

        // Iniciar reparto
        viewModelScope.launch {
            _gameState.value = BlackjackState.DEALING
            _message.value = ""
            _gameResult.value = GameResult.NONE
            _lastWinAmount.value = 0.0
            
            // Limpiar cartas anteriores
            _playerCards.value = emptyList()
            _dealerCards.value = emptyList()
            
            // Reiniciar baraja si hay pocas cartas
            if (deck.remaining() < 15) {
                deck.reset()
            }

            // Repartir cartas: Jugador, Dealer, Jugador, Dealer (boca abajo)
            delay(DEAL_DELAY)
            _playerCards.value = _playerCards.value + deck.draw()
            updateScores()
            
            delay(DEAL_DELAY)
            _dealerCards.value = _dealerCards.value + deck.draw()
            updateScores()
            
            delay(DEAL_DELAY)
            _playerCards.value = _playerCards.value + deck.draw()
            updateScores()
            
            delay(DEAL_DELAY)
            // Segunda carta del dealer boca abajo
            _dealerCards.value = _dealerCards.value + deck.draw(faceUp = false)
            updateScores()

            // Verificar Blackjack natural del jugador
            if (_playerScore.value == 21) {
                // Voltear carta del dealer
                revealDealerCard()
                updateScores()
                
                if (_dealerScore.value == 21) {
                    // Ambos tienen Blackjack - Empate
                    endGame(GameResult.PUSH)
                } else {
                    // Jugador tiene Blackjack natural
                    endGame(GameResult.PLAYER_BLACKJACK)
                }
                return@launch
            }

            // Turno del jugador
            _gameState.value = BlackjackState.PLAYER_TURN
            _message.value = "TU TURNO"
            updateAvailableActions()
        }
    }

    /**
     * Jugador pide una carta (Hit)
     */
    fun hit() {
        if (_gameState.value != BlackjackState.PLAYER_TURN) return

        viewModelScope.launch {
            // Dar carta al jugador
            _playerCards.value = _playerCards.value + deck.draw()
            updateScores()

            // Ya no puede doblar ni dividir después de pedir
            _canDouble.value = false
            _canSplit.value = false

            // Verificar si se pasó
            if (_playerScore.value > 21) {
                endGame(GameResult.PLAYER_BUST)
                return@launch
            }

            // Verificar si llegó a 21
            if (_playerScore.value == 21) {
                stand() // Automáticamente se planta
            }
        }
    }

    /**
     * Jugador se planta (Stand)
     */
    fun stand() {
        if (_gameState.value != BlackjackState.PLAYER_TURN) return

        viewModelScope.launch {
            _gameState.value = BlackjackState.DEALER_TURN
            _message.value = "TURNO DEL DEALER"

            // Voltear carta oculta del dealer
            revealDealerCard()
            updateScores()
            
            delay(DEALER_DELAY)

            // El dealer juega
            playDealerTurn()
        }
    }

    /**
     * Jugador dobla la apuesta (Double Down)
     * - Dobla la apuesta
     * - Recibe exactamente una carta más
     * - Se planta automáticamente
     */
    fun doubleDown() {
        if (_gameState.value != BlackjackState.PLAYER_TURN) return
        if (!_canDouble.value) return
        if (!balanceViewModel.hasSufficientFunds(_currentBet.value)) {
            _message.value = "Saldo insuficiente para doblar"
            return
        }

        viewModelScope.launch {
            // Doblar apuesta
            val additionalBet = _currentBet.value
            balanceViewModel.withdraw(additionalBet, "Doblar Blackjack")
            _currentBet.value = _currentBet.value * 2

            // Dar una carta
            _playerCards.value = _playerCards.value + deck.draw()
            updateScores()

            // Ya no puede hacer más acciones
            _canDouble.value = false
            _canSplit.value = false

            delay(DEAL_DELAY)

            // Verificar si se pasó
            if (_playerScore.value > 21) {
                endGame(GameResult.PLAYER_BUST)
                return@launch
            }

            // Automáticamente se planta
            stand()
        }
    }

    /**
     * Jugador divide la mano (Split)
     * NOTA: Implementación simplificada - en una versión completa,
     * manejaría dos manos separadas
     */
    fun split() {
        if (_gameState.value != BlackjackState.PLAYER_TURN) return
        if (!_canSplit.value) return
        
        // TODO: Implementar lógica de split completa
        // Por ahora, solo mostramos mensaje
        _message.value = "Split no disponible aún"
    }

    // ===============================================================================
    // LÓGICA INTERNA
    // ===============================================================================

    /**
     * Voltea la carta oculta del dealer
     */
    private fun revealDealerCard() {
        _dealerCards.value = _dealerCards.value.map { card ->
            if (!card.isFaceUp) card.copy(isFaceUp = true) else card
        }
    }

    /**
     * El dealer juega su turno según las reglas
     * - Pide con 16 o menos
     * - Se planta con 17 o más
     */
    private suspend fun playDealerTurn() {
        while (_dealerScore.value < DEALER_STAND) {
            // Dealer pide carta
            _dealerCards.value = _dealerCards.value + deck.draw()
            updateScores()
            
            delay(DEALER_DELAY)

            // Verificar si se pasó
            if (_dealerScore.value > 21) {
                endGame(GameResult.DEALER_BUST)
                return
            }
        }

        // Dealer se planta - determinar ganador
        determineWinner()
    }

    /**
     * Determina el ganador comparando puntuaciones
     */
    private fun determineWinner() {
        val playerScore = _playerScore.value
        val dealerScore = _dealerScore.value

        when {
            playerScore > dealerScore -> endGame(GameResult.PLAYER_WINS)
            dealerScore > playerScore -> endGame(GameResult.DEALER_WINS)
            else -> endGame(GameResult.PUSH)
        }
    }

    /**
     * Termina el juego y procesa el resultado
     */
    private fun endGame(result: GameResult) {
        _gameResult.value = result
        
        val bet = _currentBet.value
        var winnings = 0.0
        
        when (result) {
            GameResult.PLAYER_BLACKJACK -> {
                // Blackjack paga 3:2 (apuesta + 1.5x apuesta)
                winnings = bet + (bet * BLACKJACK_PAYOUT)
                _message.value = "BLACKJACK! GANASTE \$${String.format("%.2f", winnings)}"
                _gameState.value = BlackjackState.PLAYER_BLACKJACK
            }
            GameResult.PLAYER_WINS -> {
                // Victoria normal paga 1:1 (apuesta + apuesta)
                winnings = bet + (bet * NORMAL_PAYOUT)
                _message.value = "GANASTE \$${String.format("%.2f", winnings)}"
                _gameState.value = BlackjackState.PLAYER_WINS
            }
            GameResult.DEALER_BUST -> {
                // Dealer se pasó - jugador gana 1:1
                winnings = bet + (bet * NORMAL_PAYOUT)
                _message.value = "DEALER SE PASÓ! GANASTE \$${String.format("%.2f", winnings)}"
                _gameState.value = BlackjackState.DEALER_BUST
            }
            GameResult.PUSH -> {
                // Empate - devolver apuesta
                winnings = bet
                _message.value = "EMPATE - Apuesta devuelta"
                _gameState.value = BlackjackState.PUSH
            }
            GameResult.PLAYER_BUST -> {
                // Jugador se pasó - pierde todo
                winnings = 0.0
                _message.value = "TE PASASTE! PERDISTE \$${String.format("%.2f", bet)}"
                _gameState.value = BlackjackState.PLAYER_BUST
            }
            GameResult.DEALER_WINS -> {
                // Dealer gana - jugador pierde todo
                winnings = 0.0
                _message.value = "DEALER GANA ${_dealerScore.value} A ${_playerScore.value}"
                _gameState.value = BlackjackState.DEALER_WINS
            }
            GameResult.NONE -> { /* No hacer nada */ }
        }

        // Depositar ganancias si las hay
        if (winnings > 0) {
            balanceViewModel.deposit(winnings, "Premio Blackjack")
            _lastWinAmount.value = winnings - bet // Ganancia neta
        } else {
            _lastWinAmount.value = -bet // Pérdida
        }
    }

    /**
     * Actualiza las acciones disponibles para el jugador
     */
    private fun updateAvailableActions() {
        val cards = _playerCards.value
        
        // Puede doblar solo con las 2 cartas iniciales y si tiene saldo
        _canDouble.value = cards.size == 2 && 
                          balanceViewModel.hasSufficientFunds(_currentBet.value)
        
        // Puede dividir solo si las 2 cartas tienen el mismo valor
        _canSplit.value = cards.size == 2 && 
                         cards[0].value == cards[1].value &&
                         balanceViewModel.hasSufficientFunds(_currentBet.value)
    }

    /**
     * Calcula la puntuación de una mano
     * - Maneja Ases que pueden valer 1 u 11
     */
    private fun calculateScore(cards: List<Card>): Int {
        // Solo contar cartas boca arriba
        val visibleCards = cards.filter { it.isFaceUp }
        
        var score = 0
        var aces = 0

        for (card in visibleCards) {
            if (card.value == CardValue.ACE) {
                aces++
                score += 11
            } else {
                score += card.value.points
            }
        }

        // Ajustar Ases si nos pasamos de 21
        while (score > 21 && aces > 0) {
            score -= 10 // Cambiar As de 11 a 1
            aces--
        }

        return score
    }

    /**
     * Actualiza las puntuaciones de ambos jugadores
     */
    private fun updateScores() {
        _playerScore.value = calculateScore(_playerCards.value)
        _dealerScore.value = calculateScore(_dealerCards.value)
    }

    /**
     * Inicia una nueva ronda (después de que termine una mano)
     */
    fun newRound() {
        _gameState.value = BlackjackState.BETTING
        _currentBet.value = 0.0
        _playerCards.value = emptyList()
        _dealerCards.value = emptyList()
        _playerScore.value = 0
        _dealerScore.value = 0
        _gameResult.value = GameResult.NONE
        _message.value = ""
        _lastWinAmount.value = 0.0
        _canDouble.value = false
        _canSplit.value = false
    }

    /**
     * Repite la última apuesta
     */
    fun repeatBet(lastBet: Double) {
        if (_gameState.value != BlackjackState.BETTING) return
        if (balanceViewModel.hasSufficientFunds(lastBet)) {
            _currentBet.value = lastBet
        } else {
            _currentBet.value = balanceViewModel.balance.value
        }
    }

    /**
     * Verifica si el juego terminó
     */
    fun isGameOver(): Boolean {
        return _gameState.value in listOf(
            BlackjackState.PLAYER_BLACKJACK,
            BlackjackState.PLAYER_BUST,
            BlackjackState.DEALER_BUST,
            BlackjackState.PLAYER_WINS,
            BlackjackState.DEALER_WINS,
            BlackjackState.PUSH
        )
    }

    /**
     * Verifica si es turno del jugador
     */
    fun isPlayerTurn(): Boolean {
        return _gameState.value == BlackjackState.PLAYER_TURN
    }

    /**
     * Verifica si está en fase de apuestas
     */
    fun isBettingPhase(): Boolean {
        return _gameState.value == BlackjackState.BETTING
    }
}
