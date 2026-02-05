package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedCenter
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedEnd
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedStart
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel

/**
 * =====================================================================================
 * ROULETTEGAMESCREEN.KT - PANTALLA DE RULETA DE CASINO
 * =====================================================================================
 *
 * Esta pantalla implementa una mesa de ruleta de casino completa con:
 * - Header con imagen de mesera (fondo de casino)
 * - Tabla de ruleta con números del 0 al 36
 * - Fichas de apuesta seleccionables ($1, $10, $100, $200) - STICKY
 * - Botones de acción: Deshacer, Apostar, Repetir - STICKY
 * - Temporizador de ronda y últimos números ganadores
 * - Integración con BalanceViewModel para el saldo
 *
 * COLORES DE LA RULETA:
 * - Verde: Número 0
 * - Rojo: Números impares (1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36)
 * - Negro: Números pares restantes
 *
 * ESTRUCTURA:
 * - Fondo: Gris oscuro (DarkBackground) como otras pantallas
 * - Controles sticky: Fichas y botones siempre visibles en la parte inferior
 * =====================================================================================
 */

// Colores específicos para la mesa de ruleta
private val RouletteGreen = Color(0xFF1B5E20)
private val RouletteRed = Color(0xFFB71C1C)
private val RouletteBlack = Color(0xFF212121)
private val ChipSilver = Color(0xFFB0BEC5)
private val ChipBlue = Color(0xFF1565C0)
private val ChipGreen = Color(0xFF2E7D32)
private val ChipRedDark = Color(0xFFC62828)

@Composable
fun RouletteGameScreen(
    navController: NavController,
    balanceViewModel: BalanceViewModel = viewModel()
) {
    // =================================================================================
    // ESTADO
    // =================================================================================
    val balance by balanceViewModel.formattedBalance.collectAsStateWithLifecycle()
    var selectedChip by remember { mutableIntStateOf(10) } // Ficha seleccionada por defecto ($10)
    val scrollState = rememberScrollState()

    // =================================================================================
    // ESTRUCTURA PRINCIPAL
    // =================================================================================
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground) // Fondo gris oscuro como otras pantallas
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // -------------------------------------------------------------------------
            // HEADER CON LOGO Y BALANCE
            // -------------------------------------------------------------------------
            AppHeader(
                balance = balance,
                navController = navController
            )

            // -------------------------------------------------------------------------
            // CONTENIDO SCROLLEABLE (HEADER + TABLA)
            // -------------------------------------------------------------------------
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                // ---------------------------------------------------------------------
                // SECCIÓN DE IMAGEN DE MESERA (HEADER DE LA RULETA)
                // ---------------------------------------------------------------------
                RouletteHeaderSection()

                // ---------------------------------------------------------------------
                // TABLA DE RULETA
                // ---------------------------------------------------------------------
                RouletteTableSection()

                // Espaciado para que no quede pegado a los controles sticky
                Spacer(modifier = Modifier.height(8.dp))
            }

            // -------------------------------------------------------------------------
            // CONTROLES STICKY (FICHAS + BOTONES) - SIEMPRE VISIBLES
            // -------------------------------------------------------------------------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBackground)
                    .padding(vertical = 8.dp)
            ) {
                // FICHAS DE APUESTA
                BettingChipsSection(
                    selectedChip = selectedChip,
                    onChipSelected = { selectedChip = it }
                )

                // BOTONES DE ACCIÓN
                ActionButtonsSection()
            }

            // -------------------------------------------------------------------------
            // FOOTER DE NAVEGACIÓN
            // -------------------------------------------------------------------------
            AppFooter(
                selectedItem = "Mesas",
                onItemSelected = { },
                navController = navController
            )
        }
    }
}

/**
 * =====================================================================================
 * ROULETTE HEADER SECTION
 * =====================================================================================
 *
 * Muestra la imagen de la mesera con overlay de información del juego.
 * Incluye: botón de regreso, título "LIVE ROULETTE", temporizador,
 * indicador "EN VIVO", y los últimos números ganadores.
 */
@Composable
private fun RouletteHeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        // Imagen de fondo (mesera)
        Image(
            painter = painterResource(id = R.drawable.mesera),
            contentDescription = "Mesera de ruleta",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Overlay oscuro para mejor legibilidad
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Fila superior: Botón de regreso (izquierda) y Temporizador (derecha)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de regreso con flecha dorada
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = AccentGold,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { /* Navegar atrás */ }
                )

                // Temporizador - Esquina superior derecha
                Text(
                    text = "00:15",
                    color = AccentGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Título "LIVE ROULETTE"
            Text(
                text = "LIVE ROULETTE",
                color = AccentGold,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Subtítulo
            Text(
                text = "Hagan sus apuestas",
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Fila inferior: EN VIVO (izquierda) y Últimos números (derecha)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // EN VIVO - Esquina inferior izquierda
                LiveIndicator()

                // Últimos números - Esquina inferior derecha
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ÚLTIMOS",
                        color = Color.Gray,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    LastNumberSquare(number = 32, color = RouletteRed)
                    Spacer(modifier = Modifier.width(4.dp))
                    LastNumberSquare(number = 5, color = RouletteRed)
                    Spacer(modifier = Modifier.width(4.dp))
                    LastNumberSquare(number = 11, color = RouletteBlack)
                }
            }
        }
    }
}

/**
 * Indicador de "EN VIVO" con punto rojo.
 * Posicionado en la esquina inferior izquierda como en el diseño.
 */
@Composable
private fun LiveIndicator() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(PrimaryRed, RoundedCornerShape(4.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(Color.White, CircleShape)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "EN VIVO",
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Cuadrado pequeño que muestra un número ganador reciente.
 */
@Composable
private fun LastNumberSquare(number: Int, color: Color) {
    Box(
        modifier = Modifier
            .size(22.dp)
            .background(color, RoundedCornerShape(2.dp))
            .border(0.5.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(2.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * =====================================================================================
 * ROULETTE TABLE SECTION
 * =====================================================================================
 *
 * Implementa la mesa de ruleta con el número 0 (verde) arriba y
 * los números 1-36 organizados en una cuadrícula de 3 columnas.
 */
@Composable
private fun RouletteTableSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Número 0 (Verde) - Ocupa todo el ancho
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(RouletteGreen, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .border(1.dp, AccentGold.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "0",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Grid de números 1-36
        // Los números rojos son: 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36
        val redNumbers = setOf(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36)

        // Dividimos los números en filas de 3
        val rows = (1..36).chunked(3)

        rows.forEachIndexed { rowIndex, rowNumbers ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                rowNumbers.forEachIndexed { index, number ->
                    val isRed = number in redNumbers
                    val backgroundColor = if (isRed) RouletteRed else RouletteBlack

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp)
                            .background(backgroundColor)
                            .border(1.dp, AccentGold.copy(alpha = 0.7f))
                            .clickable { /* Seleccionar número */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = number.toString(),
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Fila inferior con apuestas especiales (2to1)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .background(DarkBackground)
                        .border(1.dp, AccentGold.copy(alpha = 0.7f))
                        .clickable { /* Apuesta 2to1 */ },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "2to1",
                        color = AccentGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/**
 * =====================================================================================
 * BETTING CHIPS SECTION
 * =====================================================================================
 *
 * Muestra las fichas de apuesta disponibles: $1, $10, $100, $200.
 * La ficha seleccionada se destaca con un borde dorado.
 */
@Composable
private fun BettingChipsSection(
    selectedChip: Int,
    onChipSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ficha de $1 (Plateada)
        BettingChip(
            value = 1,
            color = ChipSilver,
            isSelected = selectedChip == 1,
            onClick = { onChipSelected(1) }
        )

        // Ficha de $10 (Azul)
        BettingChip(
            value = 10,
            color = ChipBlue,
            isSelected = selectedChip == 10,
            onClick = { onChipSelected(10) }
        )

        // Ficha de $100 (Verde)
        BettingChip(
            value = 100,
            color = ChipGreen,
            isSelected = selectedChip == 100,
            onClick = { onChipSelected(100) }
        )

        // Ficha de $200 (Roja)
        BettingChip(
            value = 200,
            color = ChipRedDark,
            isSelected = selectedChip == 200,
            onClick = { onChipSelected(200) }
        )
    }
}

/**
 * Ficha individual de apuesta.
 */
@Composable
private fun BettingChip(
    value: Int,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .then(
                if (isSelected) {
                    Modifier.border(3.dp, AccentGold, CircleShape)
                } else Modifier
            )
            .clip(CircleShape)
            .background(color)
            .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        // Círculos decorativos para simular el borde de una ficha real
        Box(
            modifier = Modifier
                .size(52.dp)
                .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
        )

        // Valor de la ficha
        Text(
            text = "$$value",
            color = Color.White,
            fontSize = if (value >= 100) 14.sp else 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * =====================================================================================
 * ACTION BUTTONS SECTION
 * =====================================================================================
 *
 * Botones de acción: Deshacer, Apostar (principal), Repetir.
 * Los botones están correctamente centrados con texto e iconos alineados.
 */
@Composable
private fun ActionButtonsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón DESHACER
        ActionButton(
            text = "DESHACER",
            icon = Icons.AutoMirrored.Filled.Undo,
            isPrimary = false,
            onClick = { /* Deshacer última apuesta */ }
        )

        // Botón APOSTAR (Principal)
        ActionButton(
            text = "APOSTAR",
            icon = null,
            isPrimary = true,
            onClick = { /* Realizar apuesta */ }
        )

        // Botón REPETIR
        ActionButton(
            text = "REPETIR",
            icon = Icons.AutoMirrored.Filled.Redo,
            isPrimary = false,
            onClick = { /* Repetir última apuesta */ }
        )
    }
}

/**
 * Botón de acción individual.
 * - Botón primario (APOSTAR): Fondo rojo con gradiente, borde dorado, texto blanco
 * - Botones secundarios (DESHACER, REPETIR): Fondo oscuro, borde dorado, texto blanco
 */
@Composable
private fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector?,
    isPrimary: Boolean,
    onClick: () -> Unit
) {
    val buttonShape = RoundedCornerShape(22.dp)
    val borderColor = AccentGold.copy(alpha = 0.8f)

    if (isPrimary) {
        // Botón APOSTAR - Rojo con gradiente, borde dorado
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(48.dp)
                .border(
                    width = 1.5.dp,
                    color = borderColor,
                    shape = buttonShape
                )
                .clip(buttonShape)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            ButtonRedStart,
                            ButtonRedCenter,
                            ButtonRedEnd
                        )
                    )
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        // Botones DESHACER y REPETIR - Fondo oscuro, borde dorado, texto blanco
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(48.dp)
                .border(
                    width = 1.5.dp,
                    color = borderColor,
                    shape = buttonShape
                )
                .clip(buttonShape)
                .background(Color(0xFF2E2E2E)) // Gris oscuro como otras pantallas
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// =====================================================================================
// PREVIEW
// =====================================================================================
@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun RouletteGameScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        val mockNavController = rememberNavController()

        RouletteGameScreen(
            navController = mockNavController
        )
    }
}