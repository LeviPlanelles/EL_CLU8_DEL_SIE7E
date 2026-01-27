package com.example.el_clu8_del_sie7e.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * =====================================================================================
 * TRANSACTIONHISTORYVIEWMODEL.KT - LÓGICA DE NEGOCIO PARA EL HISTORIAL DE TRANSACCIONES
 * =====================================================================================
 *
 * Este ViewModel maneja:
 * - Lista de transacciones del usuario
 * - Filtrado de transacciones por tipo (Todos, Depósitos, Retirados, Ganados)
 * - Estado de las transacciones
 *
 * ARQUITECTURA:
 * ------------
 * Usamos StateFlow para gestionar el estado de manera reactiva.
 * La UI observa estos estados y se actualiza automáticamente cuando cambian.
 *
 * =====================================================================================
 */

/**
 * Enum que representa el tipo de transacción
 */
enum class TransactionType {
    DEPOSIT,    // Depósito
    WITHDRAWAL, // Retiro/Retirada
    WIN,        // Ganancia
    LOSS        // Pérdida (apuesta)
}

/**
 * Enum que representa el estado de una transacción
 */
enum class TransactionStatus {
    SUCCESS,    // EXITOSO
    COMPLETED,  // COMPLETADO
    PENDING,    // PENDIENTE
    CANCELLED   // CANCELADA
}

/**
 * Clase de datos que representa una transacción individual
 */
data class Transaction(
    val id: String,                         // ID único de la transacción (ej: #98281)
    val type: TransactionType,              // Tipo de transacción
    val description: String,                // Descripción (ej: "Depósito Bitcoin")
    val amount: Double,                     // Monto de la transacción
    val status: TransactionStatus,          // Estado de la transacción
    val time: String,                       // Hora de la transacción (ej: "14:30")
    val date: String                        // Fecha de agrupación (ej: "HOY", "AYER")
)

/**
 * ViewModel para la pantalla de Historial de Transacciones
 */
class TransactionHistoryViewModel : ViewModel() {

    // ===================================================================
    // ESTADO DEL FILTRO SELECCIONADO
    // ===================================================================
    private val _selectedFilter = MutableStateFlow("Todos")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    // ===================================================================
    // LISTA DE TODAS LAS TRANSACCIONES (SIMULADAS)
    // ===================================================================
    private val allTransactions = listOf(
        // TRANSACCIONES DE HOY
        Transaction(
            id = "#98281",
            type = TransactionType.DEPOSIT,
            description = "Depósito Bitcoin",
            amount = 500.00,
            status = TransactionStatus.SUCCESS,
            time = "14:30",
            date = "HOY"
        ),
        Transaction(
            id = "#99123",
            type = TransactionType.LOSS,
            description = "Blackjack VIP",
            amount = -50.00,
            status = TransactionStatus.COMPLETED,
            time = "14:15",
            date = "HOY"
        ),
        Transaction(
            id = "#9891",
            type = TransactionType.WIN,
            description = "Ruleta Europea",
            amount = 1200.00,
            status = TransactionStatus.SUCCESS,
            time = "14:10",
            date = "HOY"
        ),

        // TRANSACCIONES DE AYER
        Transaction(
            id = "#71942",
            type = TransactionType.WITHDRAWAL,
            description = "Retirada Bancaria",
            amount = -100.00,
            status = TransactionStatus.PENDING,
            time = "09:15",
            date = "AYER"
        ),
        Transaction(
            id = "#88342",
            type = TransactionType.WITHDRAWAL,
            description = "Retirada Bancaria",
            amount = -2000.00,
            status = TransactionStatus.CANCELLED,
            time = "09:09",
            date = "AYER"
        ),
        Transaction(
            id = "#76123",
            type = TransactionType.LOSS,
            description = "Zeus Slot",
            amount = -15.00,
            status = TransactionStatus.COMPLETED,
            time = "09:00",
            date = "AYER"
        ),
        Transaction(
            id = "#75001",
            type = TransactionType.WIN,
            description = "Tragaperras Zeus",
            amount = 200.00,
            status = TransactionStatus.SUCCESS,
            time = "08:45",
            date = "AYER"
        )
    )

    // ===================================================================
    // LISTA FILTRADA DE TRANSACCIONES
    // ===================================================================
    private val _transactions = MutableStateFlow(allTransactions)
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    // ===================================================================
    // FUNCIÓN PARA CAMBIAR EL FILTRO
    // ===================================================================
    /**
     * Cambia el filtro seleccionado y actualiza la lista de transacciones
     *
     * @param filter El nombre del filtro ("Todos", "Depósitos", "Retirados", "Ganados")
     */
    fun setFilter(filter: String) {
        _selectedFilter.value = filter

        // Filtrar las transacciones según el filtro seleccionado
        _transactions.value = when (filter) {
            "Todos" -> allTransactions

            "Depósitos" -> allTransactions.filter {
                it.type == TransactionType.DEPOSIT
            }

            "Retirados" -> allTransactions.filter {
                it.type == TransactionType.WITHDRAWAL
            }

            "Ganados" -> allTransactions.filter {
                it.type == TransactionType.WIN
            }

            else -> allTransactions
        }
    }

    // ===================================================================
    // FUNCIÓN AUXILIAR: OBTENER COLOR DEL MONTO
    // ===================================================================
    /**
     * Retorna el color apropiado para el monto según si es positivo o negativo
     */
    fun getAmountColor(amount: Double): androidx.compose.ui.graphics.Color {
        return if (amount > 0) {
            com.example.el_clu8_del_sie7e.ui.theme.AccentGold
        } else {
            androidx.compose.ui.graphics.Color.White
        }
    }

    // ===================================================================
    // FUNCIÓN AUXILIAR: FORMATEAR MONTO
    // ===================================================================
    /**
     * Formatea el monto con el signo correcto
     */
    fun formatAmount(amount: Double): String {
        val sign = if (amount > 0) "+" else ""
        return "$sign$${"%.2f".format(amount)}"
    }
}
