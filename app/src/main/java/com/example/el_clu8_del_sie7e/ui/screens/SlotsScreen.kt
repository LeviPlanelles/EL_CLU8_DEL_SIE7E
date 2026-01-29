package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme

/**
 * =====================================================================================
 * SLOTSSCREEN.KT - PANTALLA DE GALERIA DE SLOTS
 * =====================================================================================
 *
 * Esta pantalla muestra una galeria con todos los juegos de slots disponibles.
 * Incluye barra de busqueda, filtros por categoria y grid de slots.
 *
 * ESTRUCTURA:
 * -----------
 * - AppHeader: Logo y balance del usuario
 * - Titulo "SLOTS" con flecha de retorno y linea dorada
 * - Barra de busqueda
 * - Filtros: Todos, Nuevos, Jackpot, Clasico
 * - Grid de slots en 2 columnas con scroll vertical
 * - Cada slot tiene imagen, nombre, badge y boton JUGAR
 * - AppFooter: Navegacion inferior con "Mesas" seleccionado
 *
 * FONDO:
 * ------
 * - Fondo oscuro solido (DarkBackground #1E1E1E)
 *
 * NAVEGACION:
 * -----------
 * - Puede volver a la pantalla anterior
 * - Cada slot navega al juego correspondiente
 *
 * =====================================================================================
 */

// Enum para los tipos de badge de los slots
enum class SlotBadge {
    NONE,       // Sin badge
    HOT,        // Badge rojo "HOT"
    NUEVO,      // Badge verde "NUEVO"
    JACKPOT     // Badge dorado "JACKPOT"
}

// Data class para representar un slot
data class SlotItem(
    val name: String,
    val imageRes: Int,
    val badge: SlotBadge = SlotBadge.NONE
)

@Composable
fun SlotsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Estado para la busqueda
    var searchQuery by remember { mutableStateOf("") }
    
    // Estado para el filtro seleccionado
    var selectedFilter by remember { mutableStateOf("Todos") }
    
    // Estado para el item seleccionado en el footer
    var selectedFooterItem by remember { mutableStateOf("Mesas") }
    
    // Lista de filtros disponibles
    val filters = listOf("Todos", "Nuevos", "Jackpot", "Clásico")
    
    // Lista de slots disponibles
    val slots = listOf(
        SlotItem("Neon Fortune", R.drawable.slot_neon_fortune, SlotBadge.NONE),
        SlotItem("Golden Empire", R.drawable.slot_golden_empire, SlotBadge.NONE),
        SlotItem("Inferno Spin", R.drawable.slot_inferno_fortunes, SlotBadge.HOT),
        SlotItem("Zeus Slot", R.drawable.slot_zeus, SlotBadge.NUEVO),
        SlotItem("Bonus Slot 1", R.drawable.slot_additional_1, SlotBadge.NONE),
        SlotItem("Bonus Slot 2", R.drawable.slot_additional_2, SlotBadge.JACKPOT)
    )
    
    // Filtrar slots segun busqueda y filtro seleccionado
    val filteredSlots = slots.filter { slot ->
        val matchesSearch = slot.name.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "Todos" -> true
            "Nuevos" -> slot.badge == SlotBadge.NUEVO
            "Jackpot" -> slot.badge == SlotBadge.JACKPOT
            "Clásico" -> slot.badge == SlotBadge.NONE
            else -> true
        }
        matchesSearch && matchesFilter
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground) // Fondo oscuro solido
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ==================== HEADER ====================
            AppHeader(
                balance = "$5,000.00",
                navController = navController
            )

            // ==================== CONTENIDO PRINCIPAL ====================
            Column(
                modifier = Modifier
                    .weight(1f) // Ocupa el espacio disponible entre header y footer
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                
                // ==================== TITULO CON FLECHA ATRAS ====================
                SlotsHeader(
                    onBackClick = { navController.popBackStack() }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // ==================== BARRA DE BUSQUEDA ====================
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // ==================== FILTROS ====================
                FilterChips(
                    filters = filters,
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // ==================== GRID DE SLOTS ====================
                SlotsGrid(
                    slots = filteredSlots,
                    onSlotClick = { slotName ->
                        // Navegar al juego del slot
                        // navController.navigate("slot_game/$slotName")
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // ==================== FOOTER ====================
            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { selectedFooterItem = it },
                navController = navController
            )
        }
    }
}

/**
 * Header con titulo "SLOTS", flecha de retorno y linea dorada
 */
@Composable
fun SlotsHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Fila con flecha y titulo
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Flecha de retorno a la izquierda
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Volver",
                    tint = AccentGold,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            // Titulo centrado
            Text(
                text = "SLOTS",
                color = AccentGold,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        
        // Linea dorada debajo del titulo
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(3.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            AccentGold,
                            AccentGold,
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

/**
 * Barra de busqueda con estilo oscuro
 */
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = "Busca tu slot favorito...",
                color = Color.Gray,
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = Color.Gray
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF2A2A2A),
            unfocusedContainerColor = Color(0xFF2A2A2A),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = AccentGold,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        textStyle = TextStyle(
            fontSize = 14.sp
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    )
}

/**
 * Fila de chips de filtro (Todos, Nuevos, Jackpot, Clasico)
 */
@Composable
fun FilterChips(
    filters: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            SlotFilterChip(
                text = filter,
                isSelected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) }
            )
        }
    }
}

/**
 * Chip individual de filtro para slots
 */
@Composable
fun SlotFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) AccentGold else Color.Transparent
    val textColor = if (isSelected) Color.Black else Color.White
    val borderColor = if (isSelected) AccentGold else Color.Gray
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Icono de grid solo para "Todos"
            if (text == "Todos") {
                Icon(
                    painter = painterResource(id = R.drawable.ic_grid),
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            
            Text(
                text = text,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

/**
 * Grid de slots en 2 columnas
 */
@Composable
fun SlotsGrid(
    slots: List<SlotItem>,
    onSlotClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Crear filas de 2 slots cada una
        slots.chunked(2).forEach { rowSlots ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowSlots.forEach { slot ->
                    SlotCard(
                        slot = slot,
                        onClick = { onSlotClick(slot.name) },
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // Si la fila tiene solo 1 slot, agregar spacer para mantener el layout
                if (rowSlots.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * Card individual de slot con imagen, nombre, badge y boton JUGAR
 */
@Composable
fun SlotCard(
    slot: SlotItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1A1A1A))
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ==================== IMAGEN CON BADGE ====================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            // Imagen del slot
            Image(
                painter = painterResource(id = slot.imageRes),
                contentDescription = slot.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            
            // Badge en la esquina superior derecha
            if (slot.badge != SlotBadge.NONE) {
                SlotBadgeChip(
                    badge = slot.badge,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
        }
        
        // ==================== NOMBRE DEL SLOT ====================
        Text(
            text = slot.name,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
        
        // ==================== BOTON JUGAR ====================
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(AccentGold)
                .clickable { onClick() }
                .padding(horizontal = 24.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "JUGAR",
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Badge chip para mostrar HOT, NUEVO o JACKPOT
 */
@Composable
fun SlotBadgeChip(
    badge: SlotBadge,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, text) = when (badge) {
        SlotBadge.HOT -> Pair(Color(0xFFE53935), "HOT")
        SlotBadge.NUEVO -> Pair(Color(0xFF43A047), "NUEVO")
        SlotBadge.JACKPOT -> Pair(AccentGold, "JACKPOT")
        SlotBadge.NONE -> return
    }
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (badge == SlotBadge.JACKPOT) Color.Black else Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SlotsScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        SlotsScreen(navController = rememberNavController())
    }
}
