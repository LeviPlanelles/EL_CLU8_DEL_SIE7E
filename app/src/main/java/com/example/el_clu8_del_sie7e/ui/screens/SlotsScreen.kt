package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.UnifiedFilterChip
import com.example.el_clu8_del_sie7e.ui.navigation.Routes
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.Poppins
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel

/**
 * =====================================================================================
 * SLOTSSCREEN.KT - PANTALLA DE GALERÍA DE SLOTS
 * =====================================================================================
 *
 * Esta pantalla muestra una galería con todos los juegos de slots disponibles.
 *
 * ESTRUCTURA:
 * -----------
 * - AppHeader: Logo y balance del usuario (sincronizado con BalanceViewModel)
 * - Título "SLOTS" con flecha de retorno y línea dorada
 * - Barra de búsqueda
 * - Filtros: Todos, Nuevos, Jackpot, Clásico
 * - Grid de slots en 2 columnas con scroll vertical
 * - AppFooter: Navegación inferior
 *
 * BALANCE:
 * --------
 * El balance se obtiene del BalanceViewModel compartido que se pasa desde NavGraph.
 * Esto asegura que el balance esté sincronizado en toda la aplicación.
 *
 * =====================================================================================
 */

// Colores extraídos de la imagen
val CasinoGold = Color(0xFFC6A966) // Dorado suave
val CasinoRed = Color(0xFFA80F25)  // Rojo oscuro para botón "Todos"
val CasinoDarkBg = Color(0xFF1E1E1E) // Fondo principal
val CardOverlayColor = Color(0xB3000000) // Negro semitransparente para botón jugar
val BadgeHotRed = Color(0xFFD32F2F)
val BadgeNewGold = Color(0xFFFFB300)

enum class SlotBadgeType { NONE, HOT, NUEVO, JACKPOT }

data class SlotGame(
    val name: String,
    val imageRes: Int,
    val badge: SlotBadgeType = SlotBadgeType.NONE
)

@Composable
fun SlotsScreen(
    navController: NavController,
    balanceViewModel: BalanceViewModel,  // ViewModel compartido para el balance
    modifier: Modifier = Modifier
) {
    // Obtener balance formateado del ViewModel (se actualiza automáticamente)
    val formattedBalance by balanceViewModel.formattedBalance.collectAsState()

    // Datos de ejemplo basados en la imagen
    val slots = listOf(
        SlotGame("Neon Fortune", R.drawable.slot_neon_fortune),
        SlotGame("Golden Empire", R.drawable.slot_golden_empire),
        SlotGame("Inferno Spin", R.drawable.slot_inferno_fortunes, SlotBadgeType.HOT),
        SlotGame("Zeus Slot", R.drawable.slot_zeus, SlotBadgeType.NUEVO),
        SlotGame("Classic 777", R.drawable.slot_additional_1, SlotBadgeType.JACKPOT),
        SlotGame("Lucky Diamond", R.drawable.slot_additional_2)
    )

    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Todos") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CasinoDarkBg)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 1. HEADER con balance sincronizado
            AppHeader(
                balance = formattedBalance,
                navController = navController
            )

            // 2. CONTENIDO SCROLLABLE
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                // Título y Flecha atrás
                SlotScreenTitle(onBackClick = { navController.popBackStack() })

                Spacer(modifier = Modifier.height(16.dp))

                // Barra de Búsqueda
                SlotSearchBar(
                    value = searchQuery,
                    onValueChange = { searchQuery = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Filtros (Chips)
                SlotFiltersRow(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Grid de Slots (con navegación al juego)
                SlotGrid(
                    slots = slots,
                    onSlotPlay = { slotName ->
                        // Navegar a la pantalla de juego del slot
                        // Usamos URL encoding por si el nombre tiene espacios
                        val encodedName = java.net.URLEncoder.encode(slotName, "UTF-8")
                        navController.navigate("slot_game_play/$encodedName")
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            // 3. FOOTER
            AppFooter(
                selectedItem = "Mesas",
                onItemSelected = { /* Navegación footer */ },
                navController = navController
            )
        }
    }
}

// ----------------------------------------------------------------
// COMPONENTES UI ESPECÍFICOS DE ESTA PANTALLA
// ----------------------------------------------------------------

@Composable
fun SlotScreenTitle(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Atrás",
                    tint = CasinoGold,
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = "SLOTS",
                color = CasinoGold,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        // Efecto de brillo/línea debajo del título
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(2.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, CasinoGold, Color.Transparent)
                    )
                )
        )
    }
}

@Composable
fun SlotSearchBar(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text("Busca tu slot favorito...", color = Color.Gray, fontSize = 14.sp, fontFamily = Poppins)
        },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = CasinoGold)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF2C2C2C),
            unfocusedContainerColor = Color(0xFF2C2C2C),
            focusedBorderColor = CasinoGold,
            unfocusedBorderColor = Color(0xFF3E3E3E),
            cursorColor = CasinoGold,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        singleLine = true
    )
}

/**
 * Fila de filtros para la pantalla de Slots.
 * Usa el componente UnifiedFilterChip para mantener consistencia visual.
 */
@Composable
fun SlotFiltersRow(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    // Lista de filtros disponibles
    val filters = listOf("Todos", "Nuevos", "Jackpot", "Clásico")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            UnifiedFilterChip(
                text = filter,
                isSelected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) },
                // Solo "Todos" tiene icono
                icon = if (filter == "Todos") Icons.Default.GridView else null,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SlotGrid(
    slots: List<SlotGame>,
    onSlotPlay: (String) -> Unit = {}
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        slots.chunked(2).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { slot ->
                    SlotCard(
                        slot = slot,
                        onPlayClick = { onSlotPlay(slot.name) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun SlotCard(
    slot: SlotGame,
    onPlayClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(0.75f)
            .clickable { onPlayClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 1. Imagen de Fondo
            Image(
                painter = painterResource(id = slot.imageRes),
                contentDescription = slot.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 2. Degradado Oscuro
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Transparent, Color.Black),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // 3. Contenido (Nombre y Botón Jugar)
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = slot.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontFamily = Poppins,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(CardOverlayColor)
                        .border(1.dp, CasinoGold, RoundedCornerShape(50))
                        .padding(horizontal = 24.dp, vertical = 6.dp)
                        .clickable { onPlayClick() }
                ) {
                    Text(
                        text = "JUGAR",
                        color = CasinoGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        letterSpacing = 1.sp
                    )
                }
            }

            // 4. Badges
            if (slot.badge != SlotBadgeType.NONE) {
                SlotBadgeComponent(
                    type = slot.badge,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun SlotBadgeComponent(type: SlotBadgeType, modifier: Modifier = Modifier) {
    val (color, text) = when (type) {
        SlotBadgeType.HOT -> BadgeHotRed to "HOT"
        SlotBadgeType.NUEVO -> BadgeNewGold to "NUEVO"
        SlotBadgeType.JACKPOT -> BadgeNewGold to "JACKPOT"
        else -> Color.Transparent to ""
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = Poppins
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SlotsPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        SlotsScreen(
            navController = rememberNavController(),
            balanceViewModel = BalanceViewModel()
        )
    }
}
