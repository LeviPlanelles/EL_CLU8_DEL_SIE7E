package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.UnifiedFilterChip
import com.example.el_clu8_del_sie7e.ui.components.SearchGameCard
import com.example.el_clu8_del_sie7e.ui.navigation.Routes
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.Poppins
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel

/**
 * =====================================================================================
 * GAMESEARCHSCREEN.KT - PANTALLA DE BÚSQUEDA DE JUEGOS
 * =====================================================================================
 *
 * Esta pantalla permite a los usuarios buscar y filtrar juegos del casino.
 *
 * FUNCIONALIDADES:
 * ----------------
 * - Barra de búsqueda para buscar juegos por nombre
 * - Filtros por categoría (Todos, Slots, Cartas, Otros)
 * - Lista de juegos populares con información y botón para jugar
 * - Botón de volver atrás
 * - Header con saldo del usuario
 * - Footer de navegación
 *
 * COMPONENTES USADOS:
 * -------------------
 * - AppHeader: Muestra el logo y saldo del usuario
 * - AppFooter: Barra de navegación inferior
 * - FilterButton: Botones de filtro por categoría
 * - SearchGameCard: Tarjetas de juegos con información
 *
 * =====================================================================================
 */

// Modelo de datos para los juegos del buscador
data class SearchGame(
    val id: Int,
    val name: String,
    val rating: Double,
    val category: String, // "Dados", "Cartas", "En Vivo", "Mesa VIP"
    val image: Int
)

@Composable
fun GameSearchScreen(
    navController: NavController,
    balanceViewModel: BalanceViewModel  // Se pasa desde NavGraph (compartido)
) {
    // ===================================================================
    // ESTADO DE LA PANTALLA
    // ===================================================================
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Todos") }
    var selectedFooterItem by remember { mutableStateOf("Mesas") }
    
    // Obtener balance formateado del ViewModel (se actualiza automáticamente)
    val formattedBalance by balanceViewModel.formattedBalance.collectAsState()

    // Lista de juegos populares (hardcoded para demo)
    val allGames = remember {
        listOf(
            SearchGame(
                id = 1,
                name = "ROYAL DICE VIP",
                rating = 4.9,
                category = "Dados",
                image = R.drawable.game_royal_dice_vip
            ),
            SearchGame(
                id = 2,
                name = "TEXAS HOLDEM POKER",
                rating = 4.8,
                category = "Cartas",
                image = R.drawable.game_texas_holdem_poker
            ),
            SearchGame(
                id = 3,
                name = "ROULETTE GOLD",
                rating = 4.7,
                category = "En Vivo",
                image = R.drawable.game_roulette_gold
            ),
            SearchGame(
                id = 4,
                name = "BLACKJACK PRO",
                rating = 4.8,
                category = "Mesa VIP",
                image = R.drawable.game_blackjack_pro
            )
        )
    }

    // Filtrar juegos según búsqueda y categoría seleccionada
    val filteredGames = allGames.filter { game ->
        val matchesSearch = game.name.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "Todos" -> true
            "Slots" -> game.category == "Slots"
            "Cartas" -> game.category == "Cartas"
            "Otros" -> game.category != "Slots" && game.category != "Cartas"
            else -> true
        }
        matchesSearch && matchesFilter
    }

    // ===================================================================
    // UI DE LA PANTALLA
    // ===================================================================
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground) // Fondo gris oscuro como otras pantallas
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ===================================================================
            // HEADER CON SALDO
            // ===================================================================
            AppHeader(
                balance = formattedBalance,
                navController = navController
            )

            // ===================================================================
            // CONTENIDO PRINCIPAL (scrollable)
            // ===================================================================
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 21.dp, vertical = 16.dp)
            ) {
                // ============================================================
                // SECCIÓN 1: TÍTULO Y BOTÓN VOLVER
                // ============================================================
                item {
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

                        // Título
                        Text(
                            text = "BUSCADOR DE JUEGOS",
                            color = AccentGold,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Poppins
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // ============================================================
                // SECCIÓN 2: BARRA DE BÚSQUEDA
                // ============================================================
                item {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        placeholder = {
                            Text(
                                text = "Busca tu juego favorito...",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontFamily = Poppins
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = AccentGold
                            )
                        },
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp
                        ),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF2E2E2E),
                            unfocusedContainerColor = Color(0xFF2E2E2E),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = AccentGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // ============================================================
                // SECCIÓN 3: FILTROS DE CATEGORÍA (horizontal scroll)
                // Usa UnifiedFilterChip para mantener consistencia visual
                // ============================================================
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            UnifiedFilterChip(
                                text = "Todos",
                                isSelected = selectedFilter == "Todos",
                                onClick = { selectedFilter = "Todos" }
                            )
                        }
                        item {
                            UnifiedFilterChip(
                                text = "Slots",
                                iconRes = R.drawable.game_slots,
                                isSelected = selectedFilter == "Slots",
                                onClick = { selectedFilter = "Slots" }
                            )
                        }
                        item {
                            UnifiedFilterChip(
                                text = "Cartas",
                                iconRes = R.drawable.ic_cards,
                                isSelected = selectedFilter == "Cartas",
                                onClick = { selectedFilter = "Cartas" }
                            )
                        }
                        item {
                            UnifiedFilterChip(
                                text = "Otros",
                                isSelected = selectedFilter == "Otros",
                                onClick = { selectedFilter = "Otros" }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // ============================================================
                // SECCIÓN 4: TÍTULO "POPULARES" CON "VER TODOS"
                // ============================================================
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Populares",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Poppins
                        )

                        Text(
                            text = "Ver todos",
                            color = AccentGold,
                            fontSize = 13.sp,
                            fontFamily = Poppins,
                            modifier = Modifier.clickable {
                                // TODO: Navegar a ver todos los juegos
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // ============================================================
                // SECCIÓN 5: LISTA DE JUEGOS POPULARES
                // ============================================================
                items(filteredGames) { game ->
                    SearchGameCard(
                        gameImage = game.image,
                        gameName = game.name,
                        rating = game.rating,
                        category = game.category,
                        onPlayClick = {
                            when (game.name) {
                                "ROULETTE GOLD" -> navController.navigate(Routes.ROULETTE_GAME_SCREEN)
                                // TODO: Agregar navegación para otros juegos
                                else -> {
                                    // Por defecto no hace nada o podría mostrar un mensaje
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // ===================================================================
            // FOOTER DE NAVEGACIÓN
            // ===================================================================
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
@Preview(showBackground = true)
@Composable
fun GameSearchScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        GameSearchScreen(
            navController = rememberNavController(),
            balanceViewModel = BalanceViewModel()
        )
    }
}
