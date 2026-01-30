package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
    modifier: Modifier = Modifier
) {
    // Datos de ejemplo basados en la imagen
    val slots = listOf(
        SlotGame("Neon Fortune", R.drawable.slot_neon_fortune),
        SlotGame("Golden Empire", R.drawable.slot_golden_empire),
        SlotGame("Inferno Spin", R.drawable.slot_inferno_fortunes, SlotBadgeType.HOT),
        SlotGame("Zeus Slot", R.drawable.slot_zeus, SlotBadgeType.NUEVO),
        // Rellenos para simular más elementos
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
            // 1. HEADER (Reutilizando tu componente o simulándolo)
            AppHeader(
                balance = "$5,000.00",
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

                // Grid de Slots
                SlotGrid(slots = slots)

                Spacer(modifier = Modifier.height(24.dp))
            }

            // 3. FOOTER
            AppFooter(
                selectedItem = "Mesas", // Aunque estemos en Slots, mantenemos la selección activa o cambiamos a "Slots" si existe
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
            Text("Busca tu slot favorito...", color = Color.Gray, fontSize = 14.sp)
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

@Composable
fun SlotFiltersRow(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    val filters = listOf("Todos", "Nuevos", "Jackpot", "Clásico")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        filters.forEach { filter ->
            val isSelected = filter == selectedFilter
            // El botón "Todos" es rojo sólido en la imagen si está seleccionado
            // Los demás son oscuros con borde dorado.
            val isRedButton = filter == "Todos" && isSelected

            val bgColor = if (isRedButton) CasinoRed else Color(0xFF2C2C2C)
            val borderColor = if (isRedButton) Color.Transparent else if (isSelected) CasinoGold else Color(0xFF3E3E3E)
            val textColor = if (isSelected || isRedButton) Color.White else CasinoGold

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(bgColor)
                    .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                    .clickable { onFilterSelected(filter) },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (filter == "Todos") {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = filter,
                        color = textColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun SlotGrid(slots: List<SlotGame>) {
    // Implementación manual de grid de 2 columnas para control total
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        slots.chunked(2).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { slot ->
                    SlotCard(slot = slot, modifier = Modifier.weight(1f))
                }
                // Si la fila tiene un número impar, rellenamos con espacio vacío
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun SlotCard(slot: SlotGame, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .aspectRatio(0.75f) // Proporción vertical (aprox 3:4)
            .clickable { },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 1. Imagen de Fondo (Llena toda la tarjeta)
            Image(
                painter = painterResource(id = slot.imageRes),
                contentDescription = slot.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 2. Degradado Oscuro en la parte inferior para legibilidad del texto
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
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Botón "JUGAR" estilo píldora con borde dorado
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50)) // Píldora
                        .background(CardOverlayColor) // Fondo negro semitransparente
                        .border(1.dp, CasinoGold, RoundedCornerShape(50))
                        .padding(horizontal = 24.dp, vertical = 6.dp)
                        .clickable { /* Jugar */ }
                ) {
                    Text(
                        text = "JUGAR",
                        color = CasinoGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            // 4. Badges (Etiquetas en la esquina)
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
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SlotsPreview() {
    SlotsScreen(navController = rememberNavController())
}