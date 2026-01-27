package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.navigation.Routes
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme

/**
 * =====================================================================================
 * APPFOOTER.KT - COMPONENTE DE FOOTER DE NAVEGACIÓN
 * =====================================================================================
 *
 * Este componente muestra la barra de navegación inferior de la app.
 *
 * FUNCIONALIDADES:
 * ----------------
 * - Navegación entre las principales secciones de la app
 * - Resalta el item seleccionado
 * - Integración con NavController para navegación real
 *
 * NAVEGACIÓN:
 * -----------
 * - Inicio: Navega al Lobby (pantalla principal)
 * - Mesas: Navega al Buscador de Juegos
 * - Cartera: Navega a la pantalla de Cartera (Wallet)
 * - Perfil: (Pendiente de implementar)
 *
 * =====================================================================================
 */
@Composable
fun AppFooter(
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    navController: NavController? = null
) {
    // -------------------------
    // LINEA DIVISORIA
    // -------------------------
    HorizontalDivider(
        color = AccentGold.copy(alpha = 0.4f),
        thickness = 2.dp
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(79.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        FooterItem(
            icon = R.drawable.ic_home,
            label = "Inicio",
            selected = selectedItem == "Inicio",
            onClick = {
                onItemSelected("Inicio")
                navController?.navigate(Routes.LOBBY_SCREEN) {
                    // Evita múltiples copias de la misma pantalla en el stack
                    launchSingleTop = true
                }
            }
        )

        FooterItem(
            icon = R.drawable.ic_cards,
            label = "Mesas",
            selected = selectedItem == "Mesas",
            onClick = {
                onItemSelected("Mesas")
                // Navegar a la pantalla de búsqueda de juegos
                navController?.navigate(Routes.GAME_SEARCH_SCREEN) {
                    launchSingleTop = true
                }
            }
        )

        FooterItem(
            icon = R.drawable.ic_wallet,
            label = "Cartera",
            selected = selectedItem == "Cartera",
            onClick = {
                onItemSelected("Cartera")
                // Navegar a la pantalla de Cartera (Wallet)
                navController?.navigate(Routes.WALLET_SCREEN) {
                    launchSingleTop = true
                }
            }
        )

        FooterItem(
            icon = R.drawable.ic_profile,
            label = "Perfil",
            selected = selectedItem == "Perfil",
            onClick = {
                onItemSelected("Perfil")
                // Navegar a la pantalla de Perfil
                navController?.navigate(Routes.PROFILE_SCREEN) {
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun FooterItem(
    icon: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp), // 👈 baja el contenido
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(28.dp), // 👈 icono más grande
            tint = if (selected) Color.White else Color.Gray
        )

        Spacer(modifier = Modifier.height(1.dp)) // 👈 separación icono-texto

        Text(
            text = label,
            color = if (selected) Color.White else Color.Gray,
            fontSize = 11.sp
        )
    }
}



@Preview(
    showBackground = true,
    backgroundColor = 0xFF1E1E1E
)
@Composable
fun AppFooterPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        AppFooter(
            selectedItem = "Inicio",
            onItemSelected = {},
            navController = rememberNavController()
        )
    }
}
