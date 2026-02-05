package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.BonusBanner
import com.example.el_clu8_del_sie7e.ui.components.GameCard
import com.example.el_clu8_del_sie7e.ui.navigation.Routes
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel

/**
 * =====================================================================================
 * LOBBYSCREEN.KT - PANTALLA PRINCIPAL DEL LOBBY
 * =====================================================================================
 *
 * Esta es la pantalla principal que ve el usuario después de iniciar sesión.
 * Muestra:
 * - Mensaje de bienvenida personalizado
 * - Banner de bono de bienvenida
 * - Lista de juegos destacados (Slots, Roulette, BlackJack, Poker, etc.)
 * - Navegación inferior para cambiar entre secciones
 *
 * ESTRUCTURA:
 * -----------
 * - AppHeader: Logo y balance del usuario
 * - Contenido scrolleable:
 *   - Saludo de bienvenida
 *   - Banner de bonos
 *   - Sección "Juegos Destacados" con grid de tarjetas
 * - AppFooter: Navegación inferior (Inicio, Mesas, Cartera, Perfil)
 *
 * FONDO:
 * ------
 * - Fondo OSCURO (DarkBackground) - NO el gradiente rojo
 * - Los botones son ROJOS con gradiente
 *
 * NAVEGACION:
 * -----------
 * Esta pantalla sirve como hub principal, desde aquí se puede navegar a:
 * - Juegos específicos (Slots, Roulette, etc.)
 * - Cartera
 * - Perfil
 * - Otras secciones
 *
 * =====================================================================================
 */

// Data class para representar un juego
data class Game(
    val id: String,
    val name: String,
    val icon: Int,
    val count: String,
    val backgroundImage: Int
)

@Composable
fun LobbyScreen(
    navController: NavController,
    balanceViewModel: BalanceViewModel,  // Se pasa desde NavGraph (compartido)
    modifier: Modifier = Modifier
) {
    // Estado para el item seleccionado del footer
    var selectedFooterItem by remember { mutableStateOf("Inicio") }
    
    // Estado para mostrar/ocultar el banner de bono
    var showBonusBanner by remember { mutableStateOf(true) }
    
    // Obtener balance formateado del ViewModel (se actualiza automáticamente)
    val formattedBalance by balanceViewModel.formattedBalance.collectAsState()

    // Lista de juegos destacados
    val featuredGames = remember {
        listOf(
            Game(
                id = "slots",
                name = "Slot",
                icon = R.drawable.ic_cards,
                count = "Más de 200 juegos",
                backgroundImage = R.drawable.game_slots
            ),
            Game(
                id = "roulette",
                name = "Roulette",
                icon = R.drawable.ic_cards,
                count = "Más de 200 juegos",
                backgroundImage = R.drawable.game_roulette
            ),
            Game(
                id = "blackjack",
                name = "BlackJack",
                icon = R.drawable.ic_cards,
                count = "Más de 200 juegos",
                backgroundImage = R.drawable.game_blackjack
            ),
            Game(
                id = "poker",
                name = "Poker",
                icon = R.drawable.ic_cards,
                count = "Más de 200 juegos",
                backgroundImage = R.drawable.game_poker
            )
        )
    }

    Scaffold(
        // Header superior con logo y balance
        topBar = {
            AppHeader(
                balance = formattedBalance,
                navController = navController
            )
        },
        // Footer inferior con navegacion
        bottomBar = {
            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { item ->
                    selectedFooterItem = item
                },
                navController = navController
            )
        },
        // Color de fondo del Scaffold
        containerColor = DarkBackground
    ) { paddingValues ->
        // Contenido principal con scroll
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(DarkBackground) // Fondo OSCURO, no gradiente rojo
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // ==========================================
                // SECCION: SALUDO DE BIENVENIDA
                // ==========================================
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // "Hola de nuevo,"
                    Text(
                        text = "Hola de nuevo,",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    )
                    
                    // "¡BIENVENIDO, USUARIO!"
                    Text(
                        text = "¡BIENVENIDO, USUARIO!",
                        color = AccentGold,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // ==========================================
                // SECCION: BANNER DE BONO DE BIENVENIDA
                // ==========================================
                if (showBonusBanner) {
                    BonusBanner(
                        title = "BONO DE BIENVENIDA",
                        subtitle = "100% hasta \$500 en tu primer depósito.",
                        badgeText = "EXCLUSIVO",
                        buttonText = "RECLAMAR AHORA",
                        backgroundImage = R.drawable.game_casino,
                        onButtonClick = {
                            // Navegar a la pantalla de promociones
                            navController.navigate(Routes.PROMOTIONS_SCREEN)
                        },
                        modifier = Modifier.fillMaxWidth() // Respeta el padding del contenedor padre
                    )
                }

                // ==========================================
                // SECCION: JUEGOS DESTACADOS
                // ==========================================
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Header: "Juegos Destacados" + "Ver todos"
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Juegos Destacados",
                            color = AccentGold,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        TextButton(
                            onClick = {
                                // TODO: Navegar a pantalla de todos los juegos
                            }
                        ) {
                            Text(
                                text = "Ver todos",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 13.sp
                            )
                            Text(
                                text = " >",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 13.sp
                            )
                        }
                    }

                    // Grid de tarjetas de juegos (2 columnas)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Primera fila: Slot y Roulette
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            GameCard(
                                gameIcon = featuredGames[0].icon,
                                gameName = featuredGames[0].name,
                                gameCount = featuredGames[0].count,
                                backgroundImage = featuredGames[0].backgroundImage,
                                onPlayClick = {
                                    navController.navigate(Routes.SLOTS_GAME_SCREEN)
                                },
                                modifier = Modifier.weight(1f)
                            )
                            
                            GameCard(
                                gameIcon = featuredGames[1].icon,
                                gameName = featuredGames[1].name,
                                gameCount = featuredGames[1].count,
                                backgroundImage = featuredGames[1].backgroundImage,
                                onPlayClick = {
                                    navController.navigate(Routes.ROULETTE_GAME_SCREEN)
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Segunda fila: BlackJack y Poker
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            GameCard(
                                gameIcon = featuredGames[2].icon,
                                gameName = featuredGames[2].name,
                                gameCount = featuredGames[2].count,
                                backgroundImage = featuredGames[2].backgroundImage,
                                onPlayClick = {
                                    // TODO: Navegar a juego de blackjack
                                },
                                modifier = Modifier.weight(1f)
                            )
                            
                            GameCard(
                                gameIcon = featuredGames[3].icon,
                                gameName = featuredGames[3].name,
                                gameCount = featuredGames[3].count,
                                backgroundImage = featuredGames[3].backgroundImage,
                                onPlayClick = {
                                    // TODO: Navegar a juego de poker
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Espaciado adicional al final para asegurar que todo el contenido sea visible
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true)
@Composable
fun LobbyScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        LobbyScreen(
            navController = rememberNavController(),
            balanceViewModel = BalanceViewModel()
        )
    }
}
