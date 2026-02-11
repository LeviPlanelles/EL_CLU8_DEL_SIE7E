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
import androidx.compose.material.icons.filled.History
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
