package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
 * PRIMARYBUTTON.KT - BOTÓN PRINCIPAL DE LA APLICACIÓN
 * =====================================================================================
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    brush: Brush? = null, // Permite pasar un gradiente personalizado (ej: para la Cartera)
    textColor: Color = Color.Black // Permite personalizar el color del texto
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        contentPadding = if (brush != null) PaddingValues(0.dp) else ButtonDefaults.ContentPadding,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (brush != null) Color.Transparent else MaterialTheme.colorScheme.secondary,
            contentColor = textColor,
            disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
            disabledContentColor = textColor.copy(alpha = 0.5f)
        )
    ) {
        val content = @Composable {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                if (icon != null) {
                    Icon(imageVector = icon, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    color = textColor,
                    style = if (brush != null) {
                        MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp
                        )
                    } else {
                        MaterialTheme.typography.labelLarge
                    }
                )
            }
        }

        if (brush != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush, shape = RoundedCornerShape(8.dp))
                    .border(1.5.dp, AccentGold, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        } else {
            content()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun PrimaryButtonPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        PrimaryButton(
            text = "INICIAR SESIÓN",
            onClick = { }
        )
    }
}
