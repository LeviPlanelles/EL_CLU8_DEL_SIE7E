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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.FAQItem
import com.example.el_clu8_del_sie7e.ui.components.SupportChannelCard
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.GradientCenter
import com.example.el_clu8_del_sie7e.ui.theme.GradientEdge
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed

/**
 * =====================================================================================
 * SUPPORTSCREEN.KT - PANTALLA DE AYUDA Y SOPORTE
 * =====================================================================================
 *
 * Esta pantalla proporciona asistencia al usuario con:
 * - AppHeader con balance
 * - Título "AYUDA Y SOPORTE"
 * - Sección "CANALES DE ATENCIÓN":
 *   * Chat en Vivo (con badge "Online")
 *   * Enviar Ticket por email
 * - Sección "FAQ's" con preguntas expandibles:
 *   * ¿Cómo retiro mis ganancias?
 *   * ¿Cómo verifico mi cuenta?
 *   * ¿Problemas con el depósito?
 *   * Beneficios del Club VIP
 * - Link de contacto telefónico
 * - AppFooter para navegación
 *
 * ESTRUCTURA:
 * -----------
 * - Box principal con fondo gradiente
 * - Column con scroll para todo el contenido
 * - AppHeader
 * - Header personalizado con botón back y título
 * - Sección de canales de atención
 * - Sección de FAQs
 * - Link de contacto telefónico
 * - AppFooter
 *
 * =====================================================================================
 */

@Composable
fun SupportScreen(
    navController: NavController
) {
    // Estado para el item seleccionado en el footer
    var selectedFooterItem by remember { mutableStateOf("Perfil") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ------------------------------------------------------------------
            // APP HEADER
            // ------------------------------------------------------------------
            AppHeader(
                balance = "$5,000.00",
                navController = navController
            )

            // ------------------------------------------------------------------
            // CONTENIDO CON SCROLL
            // ------------------------------------------------------------------
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // ------------------------------------------------------------------
                // HEADER PERSONALIZADO
                // ------------------------------------------------------------------
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón de retroceso
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Volver",
                            tint = AccentGold
                        )
                    }

                    // Título "AYUDA Y SOPORTE"
                    Text(
                        text = "AYUDA Y SOPORTE",
                        style = MaterialTheme.typography.headlineSmall,
                        color = AccentGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ------------------------------------------------------------------
                // SECCIÓN: CANALES DE ATENCIÓN
                // ------------------------------------------------------------------
                Text(
                    text = "CANALES DE ATENCIÓN",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Cards de canales en grid 2 columnas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Chat en Vivo
                    SupportChannelCard(
                        icon = Icons.Default.Chat,
                        title = "Chat en Vivo",
                        subtitle = "Espera: < 1 min",
                        isOnline = true,
                        backgroundColor = PrimaryRed,
                        onClick = {
                            // TODO: Abrir chat en vivo
                        },
                        modifier = Modifier.weight(1f)
                    )

                    // Enviar Ticket
                    SupportChannelCard(
                        icon = Icons.Default.Email,
                        title = "Enviar Ticket",
                        subtitle = "Respuesta en 24h",
                        isOnline = false,
                        backgroundColor = Color(0xFF3A3A3A),
                        onClick = {
                            // TODO: Abrir formulario de ticket
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ------------------------------------------------------------------
                // SECCIÓN: FAQ'S
                // ------------------------------------------------------------------
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "FAQ's",
                        style = MaterialTheme.typography.headlineSmall,
                        color = AccentGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Text(
                        text = "Ver todos",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de FAQs
                FAQItem(
                    icon = Icons.Default.AccountBalance,
                    question = "¿Cómo retiro mis ganancias?",
                    answer = "Para retirar tus ganancias, dirígete a la sección 'Cartera' en el menú inferior, luego selecciona 'Retirar fondos'. Ingresa el monto que deseas retirar y selecciona tu método de pago preferido. Las solicitudes de retiro se procesan en un plazo de 24-48 horas."
                )

                Spacer(modifier = Modifier.height(12.dp))

                FAQItem(
                    icon = Icons.Default.Lock,
                    question = "¿Cómo verifico mi cuenta?",
                    answer = "Para verificar tu cuenta, ve a 'Perfil' > 'Datos Personales' > 'Verificar Cuenta'. Deberás subir una foto de tu documento de identidad y un comprobante de domicilio. El proceso de verificación toma entre 24-48 horas."
                )

                Spacer(modifier = Modifier.height(12.dp))

                FAQItem(
                    icon = Icons.Default.Warning,
                    question = "¿Problemas con el depósito?",
                    answer = "Si tienes problemas con un depósito, primero verifica que la transacción se haya completado en tu banco. Los depósitos pueden tardar entre 5 minutos y 2 horas en reflejarse. Si después de este tiempo no ves el saldo, contacta a nuestro equipo de soporte con el comprobante de la transacción."
                )

                Spacer(modifier = Modifier.height(12.dp))

                FAQItem(
                    icon = Icons.Default.Star,
                    question = "Beneficios del Club VIP",
                    answer = "Como miembro VIP disfrutas de: retiros prioritarios (procesados en menos de 24h), límites de retiro más altos, bonos exclusivos mensuales, gerente de cuenta personal, acceso a torneos VIP, y cashback mejorado en todas tus apuestas."
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------------------------------------------------------------
                // LINK DE CONTACTO TELEFÓNICO
                // ------------------------------------------------------------------
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("¿Necesitas ayuda adicional? Llámanos al ")
                        }
                        withStyle(style = SpanStyle(color = AccentGold, fontWeight = FontWeight.Bold)) {
                            append("+34 675 303 030")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            // ------------------------------------------------------------------
            // FOOTER DE NAVEGACIÓN
            // ------------------------------------------------------------------
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
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SupportScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        SupportScreen(navController = rememberNavController())
    }
}
