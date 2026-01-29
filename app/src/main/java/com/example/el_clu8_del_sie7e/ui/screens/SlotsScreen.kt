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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.GradientCenter
import com.example.el_clu8_del_sie7e.ui.theme.GradientEdge

/**
 * =====================================================================================
 * SLOTSSCREEN.KT - PANTALLA DE GALERIA DE SLOTS
 * =====================================================================================
 *
 * Esta pantalla muestra una galeria con todos los juegos de slots disponibles.
 *
 * ESTRUCTURA:
 * -----------
 * - AppHeader: Logo y balance del usuario
 * - Título "Galería de Slots"
 * - Grid de slots en 2 columnas con scroll vertical
 * - Cada slot es clickeable para iniciar el juego
 *
 * FONDO:
 * ------
 * - Fondo con gradiente radial (GradientCenter -> GradientEdge)
 *
 * NAVEGACION:
 * -----------
 * - Puede volver a la pantalla anterior
 * - Cada slot navega al juego correspondiente
 *
 * =====================================================================================
 */

@Composable
fun SlotsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(GradientCenter, GradientEdge),
                    radius = 900f
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppHeader(
                balance = "$5,000.00",
                navController = navController
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Galería de Slots",
                    color = AccentGold,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                SlotGrid(
                    onSlotClick = { slotName ->
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SlotGrid(
    onSlotClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SlotCard(
                slotName = "Neon Fortune",
                slotImage = R.drawable.slot_neon_fortune,
                onClick = { onSlotClick("Neon Fortune") },
                modifier = Modifier.weight(1f)
            )
            SlotCard(
                slotName = "Golden Empire",
                slotImage = R.drawable.slot_golden_empire,
                onClick = { onSlotClick("Golden Empire") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SlotCard(
                slotName = "Inferno Fortunes",
                slotImage = R.drawable.slot_inferno_fortunes,
                onClick = { onSlotClick("Inferno Fortunes") },
                modifier = Modifier.weight(1f)
            )
            SlotCard(
                slotName = "Zeus",
                slotImage = R.drawable.slot_zeus,
                onClick = { onSlotClick("Zeus") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SlotCard(
                slotName = "Bonus Slot 1",
                slotImage = R.drawable.slot_additional_1,
                onClick = { onSlotClick("Bonus Slot 1") },
                modifier = Modifier.weight(1f)
            )
            SlotCard(
                slotName = "Bonus Slot 2",
                slotImage = R.drawable.slot_additional_2,
                onClick = { onSlotClick("Bonus Slot 2") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SlotCard(
    slotName: String,
    slotImage: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF2E2E2E))
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = slotImage),
                contentDescription = slotName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = slotName,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
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
