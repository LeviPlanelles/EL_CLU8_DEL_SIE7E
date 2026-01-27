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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Schedule
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
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

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Top // Eliminamos spacedBy para control manual
            ) {
                // SECCIÓN 1: TÍTULO
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

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre título y filtros

                // SECCIÓN 2: FILTROS (CARRUSEL)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkBackground)
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(21.dp))
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
                        text = "Retiradas",
                        icon = Icons.Filled.AccountBalance,
                        isSelected = selectedFilter == "Retiradas",
                        onClick = { viewModel.setFilter("Retiradas") }
                    )
                    FilterChip(
                        text = "Ganados",
                        icon = Icons.Filled.EmojiEvents,
                        isSelected = selectedFilter == "Ganados",
                        onClick = { viewModel.setFilter("Ganados") }
                    )
                    FilterChip(
                        text = "Pérdidas",
                        icon = Icons.Filled.Casino,
                        isSelected = selectedFilter == "Pérdidas",
                        onClick = { viewModel.setFilter("Pérdidas") }
                    )
                    Spacer(modifier = Modifier.width(21.dp))
                }

                // SECCIÓN 3: TRANSACCIONES
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val groupedTransactions = transactions.groupBy { it.date }

                    groupedTransactions.forEach { (date, transactionsOfDay) ->
                        // Título de fecha
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 21.dp, end = 21.dp, top = 18.dp, bottom = 12.dp)
                        ) {
                            Text(
                                text = date.uppercase(),
                                color = Color.White.copy(alpha = 0.4f),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        transactionsOfDay.forEach { transaction ->
                            TransactionItem(
                                transaction = transaction,
                                viewModel = viewModel
                            )
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
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = AccentGold.copy(alpha = 0.6f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(icon, null, tint = AccentGold, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(text, color = Color.White, fontSize = 13.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: Transaction,
    viewModel: TransactionHistoryViewModel,
    modifier: Modifier = Modifier
) {
    val isNegative = transaction.type == TransactionType.WITHDRAWAL || transaction.type == TransactionType.LOSS
    val itemBackground = if (isNegative) Color(0xFF2A0C0D) else Color(0xFF1E1E1E)
    val separatorColor = Color.White.copy(alpha = 0.12f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(itemBackground)
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

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.description,
                    color = if (transaction.type == TransactionType.WIN) AccentGold else Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${transaction.time} · ID: ${transaction.id}",
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 13.sp
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = viewModel.formatAmount(transaction.amount),
                    color = viewModel.getAmountColor(transaction.amount),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
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

@Preview(showBackground = true)
@Composable
fun TransactionHistoryScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        TransactionHistoryScreen(navController = rememberNavController())
    }
}
