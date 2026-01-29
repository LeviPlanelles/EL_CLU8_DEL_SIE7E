package com.example.el_clu8_del_sie7e.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

/**
 * =====================================================================================
 * BALANCEVIEWMODEL.KT - VIEWMODEL GLOBAL DE BALANCE
 * =====================================================================================
 *
 * ViewModel compartido que gestiona el balance del usuario en toda la aplicación.
 *
 * RESPONSABILIDADES:
 * ------------------
 * - Mantener el balance actual del usuario
 * - Procesar depósitos y actualizar balance
 * - Procesar retiros y actualizar balance
 * - Registrar todas las transacciones
 * - Generar números de tarjeta aleatorios
 * - Formatear el balance para mostrar en UI
 *
 * USO:
 * ----
 * Este ViewModel debe ser compartido entre todas las pantallas que necesiten
 * acceder o modificar el balance del usuario. Se crea UNA SOLA instancia
 * en NavGraph.kt y se pasa a todas las pantallas.
 *
 * IMPORTANTE:
 * -----------
 * - El balance inicial es $0.00 (el usuario debe depositar para tener fondos)
 * - Todas las transacciones se registran automáticamente
 * - Los retiros verifican que haya fondos suficientes (no se permiten negativos)
 * - Los números de tarjeta son aleatorios pero con formato válido
 *
 * =====================================================================================
 */
class BalanceViewModel : ViewModel() {

    // ===================================================================
    // ESTADO DEL BALANCE
    // ===================================================================
    
    /**
     * Balance actual del usuario
     * Valor inicial: $0.00 (el usuario empieza sin fondos)
     */
    private val _balance = MutableStateFlow(0.00)
    val balance: StateFlow<Double> = _balance.asStateFlow()

    /**
     * Balance formateado para mostrar en UI
     * Se actualiza AUTOMÁTICAMENTE cuando cambia _balance
     * Ejemplo: "$0.00", "$1,500.00"
     */
    val formattedBalance: StateFlow<String> = _balance
        .map { formatBalance(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = formatBalance(_balance.value)
        )

    // ===================================================================
    // HISTORIAL DE TRANSACCIONES
    // ===================================================================
    
    /**
     * Lista mutable de todas las transacciones realizadas
     * Comienza vacía - se llena conforme el usuario hace operaciones
     */
    private val _transactionHistory = MutableStateFlow<List<Transaction>>(emptyList())
    val transactionHistory: StateFlow<List<Transaction>> = _transactionHistory.asStateFlow()

    // ===================================================================
    // INICIALIZACIÓN
    // ===================================================================
    
    // No se cargan transacciones iniciales - el usuario empieza de cero

    // ===================================================================
    // OPERACIONES DE BALANCE
    // ===================================================================

    /**
     * Realiza un depósito de fondos
     *
     * @param amount Cantidad a depositar (debe ser > 0)
     * @param method Método de pago utilizado
     * @return true si el depósito fue exitoso, false si hubo error
     */
    fun deposit(amount: Double, method: String): Boolean {
        if (amount <= 0) return false

        // Actualizar balance
        _balance.value += amount

        // Registrar transacción
        addTransaction(
            type = TransactionType.DEPOSIT,
            description = "Depósito con $method",
            amount = amount,
            status = TransactionStatus.SUCCESS
        )

        return true
    }

    /**
     * Realiza un retiro de fondos
     *
     * @param amount Cantidad a retirar (debe ser > 0 y <= balance actual)
     * @param method Método de retiro utilizado
     * @return true si el retiro fue exitoso, false si no hay fondos suficientes
     */
    fun withdraw(amount: Double, method: String): Boolean {
        if (amount <= 0) return false
        if (amount > _balance.value) return false // Fondos insuficientes

        // Actualizar balance
        _balance.value -= amount

        // Registrar transacción (con monto negativo)
        addTransaction(
            type = TransactionType.WITHDRAWAL,
            description = "Retiro con $method",
            amount = -amount,
            status = TransactionStatus.SUCCESS
        )

        return true
    }

    /**
     * Verifica si hay fondos suficientes para un retiro
     *
     * @param amount Cantidad que se desea retirar
     * @return true si hay fondos suficientes
     */
    fun hasSufficientFunds(amount: Double): Boolean {
        return amount <= _balance.value
    }

    // ===================================================================
    // GESTIÓN DE TRANSACCIONES
    // ===================================================================

    /**
     * Agrega una nueva transacción al historial
     *
     * @param type Tipo de transacción (DEPOSIT, WITHDRAWAL, WIN, LOSS)
     * @param description Descripción de la transacción
     * @param amount Monto de la transacción (positivo o negativo)
     * @param status Estado de la transacción (por defecto SUCCESS)
     */
    private fun addTransaction(
        type: TransactionType,
        description: String,
        amount: Double,
        status: TransactionStatus = TransactionStatus.SUCCESS
    ) {
        val newTransaction = Transaction(
            id = generateTransactionId(),
            type = type,
            description = description,
            amount = amount,
            status = status,
            time = getCurrentTime(),
            date = getCurrentDate()
        )

        // Agregar al inicio de la lista (más reciente primero)
        _transactionHistory.value = listOf(newTransaction) + _transactionHistory.value
    }

    // ===================================================================
    // UTILIDADES
    // ===================================================================

    /**
     * Formatea el balance para mostrar en UI
     *
     * @param amount Balance a formatear
     * @return String formateado tipo "$5,000.00"
     */
    fun formatBalance(amount: Double): String {
        return "$" + String.format(Locale.US, "%,.2f", amount)
    }

    /**
     * Genera un número de tarjeta aleatorio con formato válido
     *
     * Formato: XXXX XXXX XXXX XXXX (16 dígitos)
     *
     * @return String con número de tarjeta formateado
     */
    fun generateRandomCardNumber(): String {
        val part1 = Random.nextInt(1000, 9999)
        val part2 = Random.nextInt(1000, 9999)
        val part3 = Random.nextInt(1000, 9999)
        val part4 = Random.nextInt(1000, 9999)
        
        return "$part1 $part2 $part3 $part4"
    }

    /**
     * Genera un CVV aleatorio (3 dígitos)
     *
     * @return String con CVV de 3 dígitos
     */
    fun generateRandomCVV(): String {
        return Random.nextInt(100, 999).toString()
    }

    /**
     * Genera una fecha de expiración aleatoria (MM/YY)
     * Fecha entre el mes actual y 5 años en el futuro
     *
     * @return String con formato MM/YY
     */
    fun generateRandomExpiry(): String {
        val month = Random.nextInt(1, 12)
        val year = Random.nextInt(25, 30) // 2025-2030
        return String.format("%02d/%02d", month, year)
    }

    /**
     * Genera un ID único para la transacción
     *
     * @return String con formato "#XXXXX"
     */
    private fun generateTransactionId(): String {
        val randomNumber = Random.nextInt(10000, 99999)
        return "#$randomNumber"
    }

    /**
     * Obtiene la hora actual en formato HH:mm
     *
     * @return String con hora formateada
     */
    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(Date())
    }

    /**
     * Obtiene la fecha actual en formato para agrupar
     *
     * @return "HOY", "AYER", o fecha formateada
     */
    private fun getCurrentDate(): String {
        // Por simplicidad, siempre retornamos "HOY"
        // En una implementación real, aquí se compararía con la fecha actual
        return "HOY"
    }

    /**
     * Resetea el balance a cero y limpia el historial (útil para testing o logout)
     */
    fun resetBalance() {
        _balance.value = 0.00
        _transactionHistory.value = emptyList()
    }
}
