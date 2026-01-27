package com.example.el_clu8_del_sie7e.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class TransactionType {
    DEPOSIT,    // Depósito
    WITHDRAWAL, // Retiro/Retirada
    WIN,        // Ganancia
    LOSS        // Pérdida (apuesta)
}

enum class TransactionStatus {
    SUCCESS,    // EXITOSO
    COMPLETED,  // COMPLETADO
    PENDING,    // PENDIENTE
    CANCELLED   // CANCELADA
}

data class Transaction(
    val id: String,
    val type: TransactionType,
    val description: String,
    val amount: Double,
    val status: TransactionStatus,
    val time: String,
    val date: String
)

class TransactionHistoryViewModel : ViewModel() {

    private val _selectedFilter = MutableStateFlow("Todos")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    private val allTransactions = listOf(
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

    private val _transactions = MutableStateFlow(allTransactions)
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    fun setFilter(filter: String) {
        _selectedFilter.value = filter

        _transactions.value = when (filter) {
            "Todos" -> allTransactions
            "Depósitos" -> allTransactions.filter { it.type == TransactionType.DEPOSIT }
            "Retiradas" -> allTransactions.filter { it.type == TransactionType.WITHDRAWAL }
            "Ganados" -> allTransactions.filter { it.type == TransactionType.WIN }
            "Pérdidas" -> allTransactions.filter { it.type == TransactionType.LOSS }
            else -> allTransactions
        }
    }

    fun getAmountColor(amount: Double): androidx.compose.ui.graphics.Color {
        return if (amount > 0) {
            com.example.el_clu8_del_sie7e.ui.theme.AccentGold
        } else {
            androidx.compose.ui.graphics.Color.White
        }
    }

    fun formatAmount(amount: Double): String {
        val sign = if (amount > 0) "+" else ""
        return "$sign$${"%.2f".format(amount).replace(",", ".")}"
    }
}
