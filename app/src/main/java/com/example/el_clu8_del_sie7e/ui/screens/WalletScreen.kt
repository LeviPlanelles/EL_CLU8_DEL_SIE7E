package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.HelpDialog
import com.example.el_clu8_del_sie7e.ui.components.HelpSection
import com.example.el_clu8_del_sie7e.ui.components.WalletOptionButton
import com.example.el_clu8_del_sie7e.ui.navigation.Routes
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.Poppins
import com.example.el_clu8_del_sie7e.ui.theme.RegisterBackground
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel

/**
 * =====================================================================================
 * WALLETSCREEN.KT - PANTALLA PRINCIPAL DE CARTERA
 * =====================================================================================
 *
 * Esta pantalla permite al usuario gestionar sus fondos de manera segura y exclusiva.
 *
 * FUNCIONALIDADES:
 * ----------------
 * - Acceso a Depósito de fondos
 * - Acceso a Retiro de fondos
 * - Acceso a Historial de transacciones
 * - Banner informativo sobre seguridad (grado militar)
 *
 * DISEÑO:
 * -------
 * - Fondo: Gris oscuro (RegisterBackground)
 * - Botones: Gradiente rojo con borde dorado
 * - Header: Logo + Saldo
 * - Footer: Navegación inferior
 *
 * NAVEGACIÓN:
 * -----------
 * Desde esta pantalla se puede navegar a:
 * - DepositScreen (Depositar)
 * - WithdrawScreen (Retirar)
 * - TransactionHistoryScreen (Historial)
 *
 * =====================================================================================
 */
@Composable
fun WalletScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    balanceViewModel: BalanceViewModel  // Se pasa desde NavGraph (compartido)
) {
    // ===================================================================
    // ESTADO DE LA PANTALLA
    // ===================================================================
    var selectedFooterItem by remember { mutableStateOf("Cartera") }
    
    // Estado para mostrar/ocultar el dialogo de ayuda
    var showHelpDialog by remember { mutableStateOf(false) }
    
    // Obtener balance actual del ViewModel
    val formattedBalance = balanceViewModel.formatBalance(balanceViewModel.balance.value)

    // ===================================================================
    // UI DE LA PANTALLA
    // ===================================================================
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(RegisterBackground) // Fondo gris típico
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ===================================================================
            // HEADER CON LOGO Y SALDO
            // ===================================================================
            AppHeader(
                balance = formattedBalance,
                navController = navController
            )

            // ===================================================================
            // CONTENIDO PRINCIPAL (scrollable)
            // ===================================================================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 21.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // ============================================================
                // SECCIÓN 1: TÍTULO Y BOTÓN VOLVER
                // ============================================================
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón volver
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = AccentGold,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Título "CARTERA"
                    Text(
                        text = "CARTERA",
                        color = AccentGold,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Boton de ayuda (?)
                    Icon(
                        imageVector = Icons.Default.Help,
                        contentDescription = "Ayuda de Cartera",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { showHelpDialog = true }
                    )
                }

                // Subtítulo descriptivo
                Text(
                    text = "Gestiona tus fondos con seguridad y exclusividad.",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 13.sp,
                    fontFamily = Poppins
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ============================================================
                // SECCIÓN 2: OPCIONES DE CARTERA
                // ============================================================

                // Opción 1: DEPOSITAR
                WalletOptionButton(
                    icon = Icons.Filled.CreditCard,
                    title = "DEPOSITAR",
                    description = "Carga crédito a tu cuenta",
                    onClick = {
                        navController.navigate(Routes.DEPOSIT_SCREEN)
                    }
                )

                // Opción 2: RETIRAR
                WalletOptionButton(
                    icon = Icons.Filled.AccountBalance,
                    title = "RETIRAR",
                    description = "Transfiere tus ganancias",
                    onClick = {
                        navController.navigate(Routes.WITHDRAW_SCREEN)
                    }
                )

                // Opción 3: HISTORIAL
                WalletOptionButton(
                    icon = Icons.Filled.History,
                    title = "HISTORIAL",
                    description = "Revisa tus movimientos",
                    onClick = {
                        navController.navigate(Routes.TRANSACTION_HISTORY_SCREEN)
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                // ============================================================
                // SECCIÓN 3: BANNER DE SEGURIDAD (PREMIUM)
                // ============================================================
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = DarkBackground,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icono de escudo/seguridad
                        Icon(
                            painter = painterResource(id = R.drawable.icon_crown),
                            contentDescription = null,
                            tint = AccentGold,
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        // Texto del banner
                        Column {
                            Text(
                                text = "Transacciones protegidas por encriptación de ",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 10.sp,
                                fontFamily = Poppins
                            )
                            Row {
                                Text(
                                    text = "grado militar",
                                    color = AccentGold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Poppins
                                )
                                Text(
                                    text = ". Mantente distinguido de la red ",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 10.sp,
                                    fontFamily = Poppins
                                )
                            }
                            Text(
                                text = "premium.",
                                color = AccentGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Poppins
                            )
                        }
                    }
                }

                // Espaciado final para que el contenido no quede pegado al footer
                Spacer(modifier = Modifier.height(16.dp))
            }

            // ===================================================================
            // FOOTER DE NAVEGACIÓN
            // ===================================================================
            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { item ->
                    selectedFooterItem = item
                },
                navController = navController
            )
        }
    }

    // ============================================
    // DIALOGO DE AYUDA DE LA CARTERA
    // ============================================
    HelpDialog(
        showDialog = showHelpDialog,
        onDismiss = { showHelpDialog = false },
        title = "Ayuda de Cartera",
        helpSections = listOf(
            HelpSection(
                icon = Icons.Default.CreditCard,
                title = "Depositar Fondos",
                description = "Pulsa DEPOSITAR para anadir credito a tu cuenta. Puedes usar tarjeta de credito, debito o billetera electronica. Los fondos se reflejan al instante."
            ),
            HelpSection(
                icon = Icons.Default.AccountBalance,
                title = "Retirar Fondos",
                description = "Pulsa RETIRAR para transferir tus ganancias. Selecciona el metodo de retiro y el monto deseado. El retiro se procesara de forma segura."
            ),
            HelpSection(
                icon = Icons.Default.History,
                title = "Historial de Transacciones",
                description = "Consulta todos tus movimientos: depositos, retiros, ganancias y perdidas. Filtra por fecha o tipo de transaccion para encontrar lo que buscas."
            ),
            HelpSection(
                icon = Icons.Default.MonetizationOn,
                title = "Tu Saldo",
                description = "Tu saldo actual se muestra en la parte superior de la pantalla. Se actualiza automaticamente con cada operacion que realices (depositos, retiros y resultados de juegos)."
            ),
            HelpSection(
                icon = Icons.Default.Security,
                title = "Seguridad",
                description = "Todas tus transacciones estan protegidas con encriptacion de grado militar. Tus datos financieros nunca se comparten con terceros."
            ),
            HelpSection(
                icon = Icons.Default.Schedule,
                title = "Tiempos de Procesamiento",
                description = "Depositos: instantaneos. Retiros: 1-3 dias habiles segun el metodo. Las transferencias bancarias pueden tardar hasta 5 dias habiles."
            )
        )
    )
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true)
@Composable
fun WalletScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        WalletScreen(
            navController = rememberNavController(),
            balanceViewModel = BalanceViewModel()
        )
    }
}
