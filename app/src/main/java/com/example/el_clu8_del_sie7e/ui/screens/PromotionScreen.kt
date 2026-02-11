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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.UnifiedFilterChip
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.Poppins
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel
import com.example.el_clu8_del_sie7e.viewmodel.PromotionModel
import com.example.el_clu8_del_sie7e.viewmodel.PromotionsViewModel

/**
 * =====================================================================================
 * PROMOTIONSCREEN.KT - PANTALLA DE PROMOCIONES
 * =====================================================================================
 *
 * Esta pantalla muestra todas las promociones y bonos disponibles para el usuario.
 *
 * ESTRUCTURA:
 * -----------
 * - AppHeader: Logo y balance del usuario (sincronizado con BalanceViewModel)
 * - Título "PROMOCIONES" con flecha de retorno y línea dorada
 * - Filtros: Todos, Casino Live, Slots, Roulette
 * - Tarjeta de promoción principal (grande)
 * - Sección "PARA TI" con tarjetas secundarias
 * - AppFooter: Navegación inferior
 *
 * BALANCE:
 * --------
 * El balance se obtiene del BalanceViewModel compartido que se pasa desde NavGraph.
 * Esto asegura que el balance esté sincronizado en toda la aplicación.
 *
 * NAVEGACION:
 * -----------
 * - Flecha atrás: Vuelve a la pantalla anterior
 * - Footer: Navegación a otras secciones de la app
 *
 * =====================================================================================
 */

// =====================================================================
// 1. PANTALLA PRINCIPAL (STATEFUL - CONECTADA A LOS VIEWMODELS)
// =====================================================================
@Composable
fun PromocionesScreen(
    navController: NavController,
    balanceViewModel: BalanceViewModel,  // ViewModel compartido para el balance
    promotionsViewModel: PromotionsViewModel = viewModel(),  // ViewModel local para promociones
    modifier: Modifier = Modifier
) {
    // Recolectamos el balance del ViewModel compartido (se actualiza automáticamente)
    val formattedBalance by balanceViewModel.formattedBalance.collectAsState()
    
    // Recolectamos los estados del PromotionsViewModel
    val promotions by promotionsViewModel.promotions.collectAsState()
    val selectedFilter by promotionsViewModel.selectedFilter.collectAsState()

    // Llamamos al contenido visual puro
    PromocionesContent(
        navController = navController,
        balance = formattedBalance,  // Usamos el balance del BalanceViewModel
        promotions = promotions,
        selectedFilter = selectedFilter,
        onFilterSelected = { promotionsViewModel.setFilter(it) },
        onBackClick = { navController.popBackStack() },
        modifier = modifier
    )
}

// =====================================================================
// 2. CONTENIDO VISUAL (STATELESS - SOLO UI)
// =====================================================================
@Composable
fun PromocionesContent(
    navController: NavController,
    balance: String,
    promotions: List<PromotionModel>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Fondo Gradiente Radial (Según guía de estilo)
    val radialGradient = Brush.radialGradient(
        colors = listOf(
            Color(0xFF2E2E2E), // Centro ligeramente más claro
            DarkBackground     // Borde oscuro (Theme)
        ),
        center = Offset.Unspecified,
        radius = 2000f
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = radialGradient)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // HEADER con balance sincronizado
            AppHeader(
                balance = balance,
                navController = navController
            )

            // COLUMNA CON SCROLL (EL CUERPO DE LA PANTALLA)
            Column(
                modifier = Modifier
                    .weight(1f) // Ocupa todo el espacio disponible menos header/footer
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título "PROMOCIONES"
                PromotionsHeader(onBackClick = onBackClick)

                Spacer(modifier = Modifier.height(16.dp))

                // Filtros (Chips)
                PromotionsFilters(
                    selectedFilter = selectedFilter,
                    onFilterSelected = onFilterSelected
                )

                // Espacio fijo entre filtros y tarjeta
                Spacer(modifier = Modifier.height(24.dp))

                // Lógica de visualización de tarjetas
                val mainPromo = promotions.find { it.isMain }
                val secondaryPromos = promotions.filter { !it.isMain }

                // -- Tarjeta Principal --
                if (mainPromo != null) {
                    MainPromotionCard(promotion = mainPromo)
                    Spacer(modifier = Modifier.height(24.dp))
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // -- Sección "PARA TI" --
                if (secondaryPromos.isNotEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        SectionDivider(title = "PARA TI")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tarjetas Secundarias
                    secondaryPromos.forEach { promo ->
                        SecondaryPromotionCard(promotion = promo)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Espacio final para evitar choque con el footer
                Spacer(modifier = Modifier.height(24.dp))
            }

            // FOOTER
            AppFooter(
                selectedItem = "",
                onItemSelected = { /* Navegación */ },
                navController = navController
            )
        }
    }
}

// =====================================================================
// 3. COMPONENTES UI AUXILIARES
// =====================================================================

@Composable
fun PromotionsHeader(onBackClick: () -> Unit) {
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
                    contentDescription = "Volver",
                    tint = AccentGold,
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = "PROMOCIONES",
                color = AccentGold,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        // Línea dorada decorativa
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(2.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, AccentGold, Color.Transparent)
                    )
                )
        )
    }
}

/**
 * Fila de filtros para la pantalla de Promociones.
 * Usa el componente UnifiedFilterChip para mantener consistencia visual.
 */
@Composable
fun PromotionsFilters(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    // Lista de filtros disponibles para promociones
    val filters = listOf("Todos", "Casino Live", "Slots", "Roulette")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            UnifiedFilterChip(
                text = filter,
                isSelected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) }
            )
        }
    }
}

@Composable
fun SectionDivider(title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(24.dp)
                .background(AccentGold)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins
        )
    }
}

@Composable
fun MainPromotionCard(promotion: PromotionModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Imagen de Fondo
            Image(
                painter = painterResource(id = promotion.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Overlay Oscuro (Gradiente vertical para leer texto blanco)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.3f),
                                Color.Black.copy(alpha = 0.9f)
                            )
                        )
                    )
            )

            // Borde gris/dorado sutil
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            )

            // Contenido de la tarjeta
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Badges Superiores
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    BadgeChip(text = promotion.badgeText, color = AccentGold)
                    promotion.expiryText?.let {
                        BadgeChip(text = it, color = AccentGold)
                    }
                }

                // Título y Descripción
                Column {
                    Text(
                        text = promotion.title,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = Poppins,
                        lineHeight = 28.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = promotion.description,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        fontFamily = Poppins,
                        lineHeight = 18.sp
                    )
                }

                // Botón
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(1.dp, AccentGold, RoundedCornerShape(8.dp))
                        .background(AccentGold.copy(alpha = 0.2f))
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.star_on),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "RECLAMAR AHORA",
                            color = AccentGold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Poppins
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SecondaryPromotionCard(promotion: PromotionModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = promotion.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradiente Horizontal (Izquierda oscura -> Derecha transparente)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.95f),
                                Color.Black.copy(alpha = 0.4f),
                                Color.Transparent
                            ),
                            startX = 0f,
                            endX = 700f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            )

            // Texto a la izquierda
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                val badgeColor = if(promotion.badgeText.equals("EXPRESS", ignoreCase = true)) Color(0xFFFFB300) else AccentGold

                BadgeChip(text = promotion.badgeText, color = badgeColor)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = promotion.title,
                    color = AccentGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = promotion.description,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp,
                    fontFamily = Poppins,
                    lineHeight = 16.sp,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }
        }
    }
}

@Composable
fun BadgeChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .border(1.dp, color, RoundedCornerShape(4.dp))
            .background(Color.Black.copy(alpha = 0.6f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins
        )
    }
}

// =====================================================================
// 4. PREVIEW
// =====================================================================
@Preview(
    showBackground = true,
    name = "Promociones Screen Preview"
)
@Composable
fun PromocionesScreenPreview() {
    // Datos Mock para el Preview
    val fakePromotions = listOf(
        PromotionModel(
            id = 1,
            title = "Bono de\nBienvenida Real",
            description = "Duplica tu primer depósito hasta $500.000 COP.",
            imageRes = R.drawable.promotion_banner_1,
            badgeText = "NUEVO",
            expiryText = "Expira en 24h",
            isMain = true
        ),
        PromotionModel(
            id = 2,
            title = "Giros dorados",
            description = "20 tiros gratis en tragamonedas.",
            imageRes = R.drawable.promotion_banner_2,
            badgeText = "NUEVO",
            isMain = false
        ),
        PromotionModel(
            id = 3,
            title = "Tragaperras perreando",
            description = "Gana premios increíbles al instante.",
            imageRes = R.drawable.promotion_banner_3,
            badgeText = "EXPRESS",
            isMain = false
        )
    )

    EL_CLU8_DEL_SIE7ETheme {
        PromocionesContent(
            navController = rememberNavController(),
            balance = "$5,000.00",
            promotions = fakePromotions,
            selectedFilter = "Todos",
            onFilterSelected = {},
            onBackClick = {}
        )
    }
}
