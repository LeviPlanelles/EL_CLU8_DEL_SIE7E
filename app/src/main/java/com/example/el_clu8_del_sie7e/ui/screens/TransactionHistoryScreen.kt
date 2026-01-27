package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed
import com.example.el_clu8_del_sie7e.ui.theme.RegisterBackground
import com.example.el_clu8_del_sie7e.viewmodel.Transaction
import com.example.el_clu8_del_sie7e.viewmodel.TransactionHistoryViewModel
import com.example.el_clu8_del_sie7e.viewmodel.TransactionStatus
import com.example.el_clu8_del_sie7e.viewmodel.TransactionType

/**
 * =====================================================================================
 * TRANSACTIONHISTORYSCREEN.KT - PANTALLA DE HISTORIAL DE TRANSACCIONES
 * =====================================================================================
 */
@Composable
fun TransactionHistoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: TransactionHistoryViewModel = viewModel()
) {
    var selectedFooterItem by remember { mutableStateOf("Cartera") }
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(RegisterBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppHeader(
                balance = "$5,000.00",
                navController = navController
            )

            // CONTENIDO PRINCIPAL
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp), // Solo padding vertical para permitir carrusel full-width
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // SECCIÓN 1: TÍTULO (con padding horizontal manual)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 21.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = AccentGold,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navController.popBackStack() }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "HISTORIAL TRANSACCIONES",
                        color = AccentGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // SECCIÓN 2: FILTROS (CARRUSEL con fondo diferente)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkBackground) // Fondo diferente para el carrusel
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(21.dp)) // Espaciado inicial
                    FilterChip(
                        text = "Todos",
                        icon = null,
                        isSelected = selectedFilter == "Todos",
                        onClick = { viewModel.setFilter("Todos") }
                    )
                    FilterChip(
                        text = "Depósitos",
                        icon = Icons.Filled.CreditCard,
                        isSelected = selectedFilter == "Depósitos",
                        onClick = { viewModel.setFilter("Depósitos") }
                    )
                    FilterChip(
                        text = "Retirados",
                        icon = Icons.Filled.AccountBalance,
                        isSelected = selectedFilter == "Retirados",
                        onClick = { viewModel.setFilter("Retirados") }
                    )
                    FilterChip(
                        text = "Ganados",
                        icon = Icons.Filled.EmojiEvents,
                        isSelected = selectedFilter == "Ganados",
                        onClick = { viewModel.setFilter("Ganados") }
                    )
                    Spacer(modifier = Modifier.width(21.dp)) // Espaciado final
                }

                // SECCIÓN 3: TRANSACCIONES (con padding horizontal manual)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 21.dp)
                ) {
                    val groupedTransactions = transactions.groupBy { it.date }

                    groupedTransactions.forEach { (date, transactionsOfDay) ->
                        Text(
                            text = date,
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        transactionsOfDay.forEach { transaction ->
                            TransactionItem(
                                transaction = transaction,
                                viewModel = viewModel
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { item -> selectedFooterItem = item },
                navController = navController
            )
        }
    }
}

/**
 * FILTERCHIP - COMPONENTE DE FILTRO (Con borde de 10dp)
 */
@Composable
private fun FilterChip(
    text: String,
    icon: ImageVector?,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (isSelected) PrimaryRed else Color.Transparent,
                shape = RoundedCornerShape(10.dp) // Redondeado de 10dp
            )
            .border(
                width = 1.dp,
                color = AccentGold,
                shape = RoundedCornerShape(10.dp) // Redondeado de 10dp
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AccentGold,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = text,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

/**
 * TRANSACTIONITEM - COMPONENTE DE ITEM DE TRANSACCIÓN
 */
@Composable
private fun TransactionItem(
    transaction: Transaction,
    viewModel: TransactionHistoryViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DarkBackground,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = when (transaction.type) {
                            TransactionType.DEPOSIT -> AccentGold.copy(alpha = 0.2f)
                            TransactionType.WITHDRAWAL -> Color(0xFF4B0000).copy(alpha = 0.3f)
                            TransactionType.WIN -> AccentGold.copy(alpha = 0.2f)
                            TransactionType.LOSS -> Color.White.copy(alpha = 0.1f)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (transaction.type) {
                        TransactionType.DEPOSIT -> Icons.Filled.CreditCard
                        TransactionType.WITHDRAWAL -> Icons.Filled.AccountBalance
                        TransactionType.WIN -> Icons.Filled.EmojiEvents
                        TransactionType.LOSS -> Icons.Filled.Casino
                    },
                    contentDescription = null,
                    tint = when (transaction.type) {
                        TransactionType.DEPOSIT -> AccentGold
                        TransactionType.WITHDRAWAL -> Color.White
                        TransactionType.WIN -> AccentGold
                        TransactionType.LOSS -> Color.White
                    },
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.description,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${transaction.time} · ID: ${transaction.id}",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = viewModel.formatAmount(transaction.amount),
                    color = viewModel.getAmountColor(transaction.amount),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = when (transaction.status) {
                        TransactionStatus.SUCCESS -> "EXITOSO"
                        TransactionStatus.COMPLETED -> "COMPLETADO"
                        TransactionStatus.PENDING -> "PENDIENTE"
                        TransactionStatus.CANCELLED -> "CANCELADA"
                    },
                    color = when (transaction.status) {
                        TransactionStatus.SUCCESS -> Color(0xFF00C853)
                        TransactionStatus.COMPLETED -> Color.White.copy(alpha = 0.5f)
                        TransactionStatus.PENDING -> Color(0xFFFFB300)
                        TransactionStatus.CANCELLED -> Color(0xFFFF5252)
                    },
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionHistoryScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        TransactionHistoryScreen(navController = rememberNavController())
    }
}
