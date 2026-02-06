package com.example.el_clu8_del_sie7e.ui.components.blackjack

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Palos de las cartas
 */
enum class CardSuit(val symbol: String, val color: Color) {
    HEARTS("♥", Color.Red),
    DIAMONDS("♦", Color.Red),
    CLUBS("♣", Color.Black),
    SPADES("♠", Color.Black)
}

/**
 * Valores de las cartas
 */
enum class CardValue(val display: String, val points: Int) {
    ACE("A", 11),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 10),
    QUEEN("Q", 10),
    KING("K", 10)
}

/**
 * Modelo de una carta
 */
data class Card(
    val value: CardValue,
    val suit: CardSuit,
    val isFaceUp: Boolean = true
)

/**
 * Componente de carta de poker
 * 
 * @param card La carta a mostrar
 * @param modifier Modificador
 * @param width Ancho de la carta
 * @param rotation Rotación de la carta en grados
 */
@Composable
fun PlayingCard(
    card: Card,
    modifier: Modifier = Modifier,
    width: Dp = 70.dp,
    rotation: Float = 0f
) {
    val height = width * 1.4f
    val cornerRadius = 8.dp
    
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .rotate(rotation)
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color.White)
            .border(1.dp, Color.LightGray, RoundedCornerShape(cornerRadius))
    ) {
        if (card.isFaceUp) {
            // Carta boca arriba
            Column(
                modifier = Modifier.fillMaxSize().padding(6.dp)
            ) {
                // Valor y palo arriba izquierda
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = card.value.display,
                        color = card.suit.color,
                        fontWeight = FontWeight.Bold,
                        fontSize = (width.value * 0.28f).sp
                    )
                    Text(
                        text = card.suit.symbol,
                        color = card.suit.color,
                        fontSize = (width.value * 0.22f).sp,
                        lineHeight = (width.value * 0.22f).sp
                    )
                }
                
                // Símbolo grande en el centro
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = card.suit.symbol,
                        color = card.suit.color,
                        fontSize = (width.value * 0.5f).sp
                    )
                }
            }
            
            // Valor y palo abajo derecha (invertido)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.rotate(180f)
                ) {
                    Text(
                        text = card.value.display,
                        color = card.suit.color,
                        fontWeight = FontWeight.Bold,
                        fontSize = (width.value * 0.28f).sp
                    )
                    Text(
                        text = card.suit.symbol,
                        color = card.suit.color,
                        fontSize = (width.value * 0.22f).sp,
                        lineHeight = (width.value * 0.22f).sp
                    )
                }
            }
        } else {
            // Carta boca abajo - patrón del reverso
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF8B0000))
                    .border(2.dp, Color(0xFFFFD700), RoundedCornerShape(4.dp))
            )
        }
    }
}

/**
 * Grupo de cartas apiladas con rotación
 */
@Composable
fun CardStack(
    cards: List<Card>,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 70.dp,
    overlapOffset: Dp = 35.dp,
    rotations: List<Float> = listOf(-8f, 8f)
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        cards.forEachIndexed { index, card ->
            val rotation = rotations.getOrElse(index) { 0f }
            val offsetX = if (index == 0) -overlapOffset / 2 else overlapOffset / 2
            
            PlayingCard(
                card = card,
                width = cardWidth,
                rotation = rotation,
                modifier = Modifier.offset(x = offsetX)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun PlayingCardPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        PlayingCard(
            card = Card(CardValue.EIGHT, CardSuit.DIAMONDS)
        )
        PlayingCard(
            card = Card(CardValue.JACK, CardSuit.CLUBS)
        )
        PlayingCard(
            card = Card(CardValue.ACE, CardSuit.SPADES),
            width = 50.dp
        )
    }
}
