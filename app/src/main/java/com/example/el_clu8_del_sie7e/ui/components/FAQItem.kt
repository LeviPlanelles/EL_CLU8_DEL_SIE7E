package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme

/**
 * =====================================================================================
 * FAQITEM.KT - COMPONENTE DE PREGUNTA FRECUENTE EXPANDIBLE
 * =====================================================================================
 *
 * Este componente muestra una pregunta frecuente que se puede expandir/colapsar:
 * - Icono de categoría (izquierda)
 * - Pregunta (texto principal)
 * - Icono de flecha que rota (derecha)
 * - Respuesta expandible (oculta por defecto)
 *
 * EJEMPLO DE USO:
 * ```kotlin
 * FAQItem(
 *     icon = Icons.Default.AttachMoney,
 *     question = "¿Cómo retiro mis ganancias?",
 *     answer = "Para retirar tus ganancias, ve a la sección..."
 * )
 * ```
 *
 * =====================================================================================
 */

@Composable
fun FAQItem(
    icon: ImageVector,
    question: String,
    answer: String,
    modifier: Modifier = Modifier
) {
    // Estado para controlar si está expandido
    var isExpanded by remember { mutableStateOf(false) }

    // Animación para rotar la flecha
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "Arrow rotation"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF2A2A2A),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = Color.Gray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { isExpanded = !isExpanded }
            .padding(16.dp)
    ) {
        // ------------------------------------------------------------------
        // FILA PRINCIPAL: ICONO + PREGUNTA + FLECHA
        // ------------------------------------------------------------------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Icono de categoría
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AccentGold,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.size(12.dp))

                // Pregunta
                Text(
                    text = question,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            }

            // Flecha que rota
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Colapsar" else "Expandir",
                tint = AccentGold,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotationAngle)
            )
        }

        // ------------------------------------------------------------------
        // RESPUESTA EXPANDIBLE
        // ------------------------------------------------------------------
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = answer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun FAQItemPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FAQItem(
                icon = Icons.Default.KeyboardArrowDown,
                question = "¿Cómo retiro mis ganancias?",
                answer = "Para retirar tus ganancias, dirígete a la sección 'Cartera' en el menú inferior, luego selecciona 'Retirar fondos'. Ingresa el monto que deseas retirar y selecciona tu método de pago preferido. Las solicitudes de retiro se procesan en un plazo de 24-48 horas."
            )
        }
    }
}
