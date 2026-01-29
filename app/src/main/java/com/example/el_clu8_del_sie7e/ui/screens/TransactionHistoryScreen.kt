package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.DateHeader
import com.example.el_clu8_del_sie7e.ui.components.FilterChip
import com.example.el_clu8_del_sie7e.ui.components.TransactionItem
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.RegisterBackground
import com.example.el_clu8_del_sie7e.viewmodel.TransactionHistoryViewModel

/**
 * =====================================================================================
 * TRANSACTIONHISTORYSCREEN.KT - PANTALLA DE HISTORIAL DE TRANSACCIONES
 * =====================================================================================
 *
 * Pantalla que muestra el historial completo de transacciones del usuario.
 *
 * FUNCIONALIDADES:
 * ----------------
 * - Visualización de todas las transacciones agrupadas por fecha
 * - Filtros por tipo: Todos, Depósitos, Retiradas, Ganados, Pérdidas
 * - Detalles de cada transacción: tipo, monto, estado, hora, ID
 * - Estados: Exitoso, Completado, Pendiente, Cancelada
 *
 * DISEÑO:
 * -------
 * - Fondo: Gris oscuro (RegisterBackground)
 * - Header: Logo + Saldo
 * - Filtros: Carrusel horizontal con chips seleccionables
 * - Transacciones: Agrupadas por fecha con separadores
 * - Footer: Navegación inferior
 *
 * COMPONENTES UTILIZADOS:
 * -----------------------
 * - AppHeader: Barra superior con logo y saldo
 * - FilterChip: Botones de filtro reutilizables
 * - DateHeader: Encabezados de fecha para agrupar
 * - TransactionItem: Item individual de transacción
 * - AppFooter: Barra de navegación inferior
 *
 * NAVEGACIÓN:
 * -----------
 * Accesible desde: WalletScreen (opción "Historial")
 * Footer seleccionado: "Cartera"
 *
 * =====================================================================================
 */
@Composable
fun TransactionHistoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: TransactionHistoryViewModel = viewModel()
) {
    // ===================================================================
    // ESTADO DE LA PANTALLA
    // ===================================================================
    var selectedFooterItem by remember { mutableStateOf("Cartera") }
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    // ===================================================================
    // UI DE LA PANTALLA
    // ===================================================================
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(RegisterBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ============================================================
            // HEADER CON LOGO Y SALDO
            // ============================================================
            AppHeader(
                balance = "$5,000.00",
                navController = navController
            )

            // ============================================================
            // CONTENIDO PRINCIPAL (scrollable)
            // ============================================================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // ========================================================
                // SECCIÓN 1: TÍTULO Y BOTÓN VOLVER
                // ========================================================
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

                Spacer(modifier = Modifier.height(16.dp))

                // ========================================================
                // SECCIÓN 2: FILTROS (CARRUSEL HORIZONTAL)
                // ========================================================
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
                    
                    // Filtro: Todos
                    FilterChip(
                        text = "Todos",
                        icon = null,
                        isSelected = selectedFilter == "Todos",
                        onClick = { viewModel.setFilter("Todos") }
                    )
                    
                    // Filtro: Depósitos
                    FilterChip(
                        text = "Depósitos",
                        icon = Icons.Filled.CreditCard,
                        isSelected = selectedFilter == "Depósitos",
                        onClick = { viewModel.setFilter("Depósitos") }
                    )
                    
                    // Filtro: Retiradas
                    FilterChip(
                        text = "Retiradas",
                        icon = Icons.Filled.AccountBalance,
                        isSelected = selectedFilter == "Retiradas",
                        onClick = { viewModel.setFilter("Retiradas") }
                    )
                    
                    // Filtro: Ganados
                    FilterChip(
                        text = "Ganados",
                        icon = Icons.Filled.EmojiEvents,
                        isSelected = selectedFilter == "Ganados",
                        onClick = { viewModel.setFilter("Ganados") }
                    )
                    
                    // Filtro: Pérdidas
                    FilterChip(
                        text = "Pérdidas",
                        icon = Icons.Filled.Casino,
                        isSelected = selectedFilter == "Pérdidas",
                        onClick = { viewModel.setFilter("Pérdidas") }
                    )
                    
                    Spacer(modifier = Modifier.width(21.dp))
                }

                // ========================================================
                // SECCIÓN 3: LISTA DE TRANSACCIONES AGRUPADAS POR FECHA
                // ========================================================
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Agrupar transacciones por fecha
                    val groupedTransactions = transactions.groupBy { it.date }

                    // Iterar por cada grupo de fecha
                    groupedTransactions.forEach { (date, transactionsOfDay) ->
                        // Encabezado de fecha
                        DateHeader(date = date)

                        // Transacciones de ese día
                        transactionsOfDay.forEach { transaction ->
                            TransactionItem(transaction = transaction)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // ============================================================
            // FOOTER DE NAVEGACIÓN
            // ============================================================
            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { item -> selectedFooterItem = item },
                navController = navController
            )
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true)
@Composable
fun TransactionHistoryScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        TransactionHistoryScreen(navController = rememberNavController())
    }
}
