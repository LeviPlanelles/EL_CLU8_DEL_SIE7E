package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.viewmodel.Transaction
import com.example.el_clu8_del_sie7e.viewmodel.TransactionStatus
import com.example.el_clu8_del_sie7e.viewmodel.TransactionType

/**
 * =====================================================================================
 * TRANSACTIONITEM.KT - COMPONENTE DE ITEM DE TRANSACCIÓN
 * =====================================================================================
 *
 * Componente reutilizable para mostrar una transacción individual en el historial.
 *
 * USOS:
 * -----
 * - Listas de transacciones financieras
 * - Historial de movimientos bancarios
 * - Historial de apuestas y ganancias
 *
 * CARACTERÍSTICAS:
 * ----------------
 * - Icono circular con tipo de transacción (Depósito, Retiro, Ganancia, Pérdida)
 * - Descripción y detalles (hora, ID)
 * - Monto con color según positivo/negativo
 * - Estado con icono y texto (Exitoso, Completado, Pendiente, Cancelada)
 * - Fondo diferenciado según tipo (rojo oscuro para negativas, gris para positivas)
 * - Línea separadora inferior
 *
 * DISEÑO:
 * -------
 * - Fondo negativo (retiro/pérdida): #2A0C0D
 * - Fondo positivo (depósito/ganancia): #1E1E1E
 * - Iconos ganancia/depósito: Dorado (AccentGold)
 * - Iconos pérdida/retiro: Blanco semitransparente
 * - Estados: Verde (éxito), Naranja (pendiente), Rojo (cancelada)
 *
 * ESTRUCTURA:
 * -----------
 * [Icono circular] [Descripción] [Monto]
 *                  [Hora · ID]   [Estado]
 *
 * EJEMPLO DE USO:
 * ---------------
 * ```kotlin
 * TransactionItem(
 *     transaction = Transaction(
 *         id = "TXN001",
 *         type = TransactionType.DEPOSIT,
 *         description = "Depósito con Visa",
 *         amount = 500.0,
 *         date = "Hoy",
 *         time = "14:30",
 *         status = TransactionStatus.SUCCESS
 *     )
 * )
 * ```
 *
 * =====================================================================================
 */
@Composable
fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    // ===================================================================
    // CONFIGURACIÓN DE COLORES Y ESTADOS
    // ===================================================================
    
    // Determinar si la transacción es negativa (retiro o pérdida)
    val isNegative = transaction.type == TransactionType.WITHDRAWAL || 
                     transaction.type == TransactionType.LOSS
    
    // Color de fondo según tipo de transacción
    val itemBackground = if (isNegative) Color(0xFF2A0C0D) else Color(0xFF1E1E1E)
    
    // Color del separador inferior
    val separatorColor = Color.White.copy(alpha = 0.12f)

    // ===================================================================
    // CONTENEDOR DEL ITEM
    // ===================================================================
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(itemBackground)
            // Dibujar línea separadora en la parte inferior
            .drawBehind {
                drawLine(
                    color = separatorColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(horizontal = 21.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ============================================================
            // ICONO CIRCULAR (TIPO DE TRANSACCIÓN)
            // ============================================================
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color.White.copy(alpha = 0.05f), CircleShape)
                    .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (transaction.type) {
                        TransactionType.DEPOSIT -> Icons.Filled.ArrowDownward
                        TransactionType.WITHDRAWAL -> Icons.Filled.ArrowUpward
                        TransactionType.WIN -> Icons.Filled.EmojiEvents
                        TransactionType.LOSS -> Icons.Filled.Casino
                    },
                    contentDescription = null,
                    tint = when (transaction.type) {
                        TransactionType.DEPOSIT, TransactionType.WIN -> AccentGold
                        else -> Color.White.copy(alpha = 0.6f)
                    },
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // ============================================================
            // INFORMACIÓN DE LA TRANSACCIÓN (DESCRIPCIÓN, HORA, ID)
            // ============================================================
            Column(modifier = Modifier.weight(1f)) {
                // Descripción de la transacción
                Text(
                    text = transaction.description,
                    color = if (transaction.type == TransactionType.WIN) 
                        AccentGold else Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                // Detalles: hora e ID
                Text(
                    text = "${transaction.time} · ID: ${transaction.id}",
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 13.sp
                )
            }

            // ============================================================
            // MONTO Y ESTADO
            // ============================================================
            Column(horizontalAlignment = Alignment.End) {
                // Monto de la transacción
                Text(
                    text = formatAmount(transaction.amount),
                    color = getAmountColor(transaction.amount),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                
                // Estado de la transacción con icono
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val statusColor = when (transaction.status) {
                        TransactionStatus.SUCCESS -> Color(0xFF00C853)
                        TransactionStatus.COMPLETED -> Color.White.copy(alpha = 0.4f)
                        TransactionStatus.PENDING -> Color(0xFFFFA000)
                        TransactionStatus.CANCELLED -> Color(0xFFFF5252)
                    }
                    
                    Icon(
                        imageVector = when(transaction.status) {
                            TransactionStatus.SUCCESS -> Icons.Filled.CheckCircle
                            TransactionStatus.PENDING -> Icons.Filled.Schedule
                            TransactionStatus.CANCELLED -> Icons.Filled.Close
                            else -> Icons.Filled.CheckCircle
                        },
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = when (transaction.status) {
                            TransactionStatus.SUCCESS -> "EXITOSO"
                            TransactionStatus.COMPLETED -> "COMPLETADO"
                            TransactionStatus.PENDING -> "PENDIENTE"
                            TransactionStatus.CANCELLED -> "CANCELADA"
                        },
                        color = statusColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/**
 * Formatea el monto con signo + o - y símbolo de dólar.
 */
private fun formatAmount(amount: Double): String {
    val sign = if (amount >= 0) "+" else ""
    return "$sign$${"%.2f".format(amount)}"
}

/**
 * Retorna el color apropiado según el monto sea positivo o negativo.
 */
private fun getAmountColor(amount: Double): Color {
    return if (amount >= 0) AccentGold else Color(0xFFFF5252)
}
