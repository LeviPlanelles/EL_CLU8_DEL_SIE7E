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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.data.repository.AuthRepository
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.ProfileHeader
import com.example.el_clu8_del_sie7e.ui.components.ProfileMenuItem
import com.example.el_clu8_del_sie7e.ui.components.ProfileStatsCard
import com.example.el_clu8_del_sie7e.ui.components.RedButton
import com.example.el_clu8_del_sie7e.ui.navigation.Routes
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.GradientCenter
import com.example.el_clu8_del_sie7e.ui.theme.GradientEdge
import com.example.el_clu8_del_sie7e.ui.theme.Poppins
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel

/**
 * =====================================================================================
 * PROFILESCREEN.KT - PANTALLA DE PERFIL DEL USUARIO
 * =====================================================================================
 *
 * Esta pantalla muestra el perfil del usuario con:
 * - Header personalizado con boton de retroceso e icono de ayuda
 * - Foto de perfil, nombre y nivel del usuario
 * - Saldo actual y puntos VIP
 * - Opciones de menu:
 *   * Datos Personales
 *   * Seguridad (Contraseña y 2FA)
 *   * Límites de Juego
 * - Boton de cerrar sesion
 * - Footer de navegacion
 *
 * ESTRUCTURA:
 * -----------
 * - Box principal con fondo gradiente
 * - Column con scroll para todo el contenido
 * - Header personalizado (no usa AppHeader)
 * - ProfileHeader con foto y nombre
 * - Row con dos ProfileStatsCard (Saldo y Puntos VIP)
 * - Lista de ProfileMenuItem
 * - Boton de cerrar sesion
 * - AppFooter
 *
 * =====================================================================================
 */

@Composable
fun ProfileScreen(
    navController: NavController,
    balanceViewModel: BalanceViewModel,  // Se pasa desde NavGraph (compartido)
    onLogout: () -> Unit = {}  // Callback para cerrar sesión
) {
    // Estado para el item seleccionado en el footer
    var selectedFooterItem by remember { mutableStateOf("Perfil") }
    
    // Repositorio de autenticación para obtener datos del usuario
    val authRepository = remember { AuthRepository.getInstance() }
    val currentUser by authRepository.currentUser.collectAsState()
    
    // Obtener balance actual del ViewModel
    val formattedBalance by balanceViewModel.formattedBalance.collectAsState()
    
    // Obtener nombre del usuario de Firebase o usar default
    val userName = authRepository.getCurrentUserName()
    val userEmail = authRepository.getCurrentUserEmail()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // HEADER GLOBAL
            AppHeader(balance = formattedBalance, navController = navController)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de retroceso
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = AccentGold,
                        modifier = Modifier.size(24.dp).clickable { navController.popBackStack() }
                    )
                }

                // Título "MI PERFIL"
                Text(text = "MI PERFIL", color = AccentGold, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
                Spacer(modifier = Modifier.weight(1f))

                // Icono de ayuda - Navega a SupportScreen
                IconButton(onClick = { 
                    navController.navigate(Routes.SUPPORT_SCREEN)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.question),
                        contentDescription = "Ayuda y Soporte",
                        tint = Color.Gray,
                        modifier = Modifier.padding(4.dp)
                            .size(24.dp)
                    )
                }
            }

            // ------------------------------------------------------------------
            // CONTENIDO CON SCROLL
            // ------------------------------------------------------------------
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // ------------------------------------------------------------------
                // HEADER DEL PERFIL (Foto, Nombre, Nivel)
                // ------------------------------------------------------------------
                ProfileHeader(
                    modifier = Modifier
                        .fillMaxWidth(),
                    userName = userName,
                    userLevel = "Diamante",
                    isVip = true,
                    profileImageRes = R.drawable.pinguino_machetero
                )
                Spacer(modifier = Modifier.height(24.dp))

                // ------------------------------------------------------------------
                // CARDS DE ESTADÍSTICAS (Saldo y Puntos VIP)
                // ------------------------------------------------------------------
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProfileStatsCard(
                        title = "Saldo Actual",
                        value = formattedBalance,
                        modifier = Modifier.weight(1f)
                    )

                    ProfileStatsCard(
                        title = "Puntos VIP",
                        value = "67,533",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ------------------------------------------------------------------
                // OPCIONES DE MENÚ
                // ------------------------------------------------------------------
                ProfileMenuItem(
                    icon = Icons.Default.Person,
                    title = "Datos Personales",
                    subtitle = "Gestiona tu información",
                    onClick = {
                        // TODO: Navegar a pantalla de datos personales
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ProfileMenuItem(
                    icon = Icons.Default.Lock,
                    title = "Seguridad",
                    subtitle = "Contraseña y 2FA",
                    onClick = {
                        // TODO: Navegar a pantalla de seguridad
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ProfileMenuItem(
                    icon = Icons.Default.Star, // Placeholder para icono de límites
                    title = "Límites de Juego",
                    subtitle = "Juego responsable",
                    onClick = {
                        // TODO: Navegar a pantalla de límites
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------------------------------------------------------------
                // BOTÓN DE CERRAR SESIÓN
                // ------------------------------------------------------------------
                RedButton(
                    text = "CERRAR SESIÓN",
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    onClick = {
                        // Llamar al callback que maneja el logout desde el NavGraph
                        onLogout()
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            // ------------------------------------------------------------------
            // FOOTER DE NAVEGACIÓN
            // ------------------------------------------------------------------
            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { selectedFooterItem = it },
                navController = navController
            )
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        ProfileScreen(
            navController = rememberNavController(),
            balanceViewModel = BalanceViewModel(),
            onLogout = {}
        )
    }
}
