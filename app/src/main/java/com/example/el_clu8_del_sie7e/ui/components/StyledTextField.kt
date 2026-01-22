package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.SurfaceDark

/**
 * =====================================================================================
 * STYLEDTEXTFIELD.KT - CAMPO DE TEXTO PERSONALIZADO
 * =====================================================================================
 *
 * Este es nuestro campo de texto personalizado con el estilo de la app.
 * Se usa para todos los inputs de texto: usuario, contrasena, email, etc.
 *
 * ESTILO:
 * -------
 * - Fondo: Gris oscuro (SurfaceDark)
 * - Texto: Blanco
 * - Borde: Sin borde visible (indicadores transparentes)
 * - Forma: Bordes redondeados (8dp)
 * - Iconos: Dorado
 *
 * PARAMETROS:
 * -----------
 * @param value El valor actual del texto
 * @param onValueChange Funcion que se llama cuando el usuario escribe
 * @param label Texto de la etiqueta (ej: "Usuario", "Contrasena")
 * @param leadingIcon Icono que aparece al inicio del campo
 * @param trailingIcon Composable opcional al final (ej: boton de mostrar contrasena)
 * @param visualTransformation Transforma como se muestra el texto (ej: ocultar contrasena)
 * @param modifier Modificador opcional
 * @param enabled Si es false, el campo esta deshabilitado
 * @param isError Si es true, muestra el campo en estado de error
 * @param supportingText Texto de ayuda o error debajo del campo
 *
 * CONCEPTO: STATE HOISTING
 * ------------------------
 * Este componente NO maneja su propio estado (el texto).
 * En cambio, recibe el valor (value) y una funcion para actualizarlo (onValueChange).
 *
 * Esto se llama "State Hoisting" (elevacion de estado) y tiene beneficios:
 * 1. El componente es mas reutilizable
 * 2. El estado se puede controlar desde afuera
 * 3. Es mas facil de testear
 *
 * =====================================================================================
 */

/**
 * Campo de texto personalizado con estilo oscuro.
 *
 * EJEMPLO DE USO BASICO:
 * ```kotlin
 * var texto by remember { mutableStateOf("") }
 *
 * StyledTextField(
 *     value = texto,
 *     onValueChange = { texto = it },
 *     label = "Usuario",
 *     leadingIcon = Icons.Filled.Person
 * )
 * ```
 *
 * EJEMPLO CON CONTRASENA:
 * ```kotlin
 * var password by remember { mutableStateOf("") }
 * var visible by remember { mutableStateOf(false) }
 *
 * StyledTextField(
 *     value = password,
 *     onValueChange = { password = it },
 *     label = "Contrasena",
 *     leadingIcon = Icons.Filled.Lock,
 *     trailingIcon = {
 *         IconButton(onClick = { visible = !visible }) {
 *             Icon(
 *                 if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
 *                 null
 *             )
 *         }
 *     },
 *     visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation()
 * )
 * ```
 */
@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null
) {
    TextField(
        // El valor actual del campo
        value = value,
        // Funcion que se llama cada vez que el usuario escribe
        onValueChange = onValueChange,
        // Etiqueta que aparece dentro del campo y sube cuando hay texto
        label = { Text(label) },
        // Icono al inicio del campo
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,  // La etiqueta ya describe el campo
                tint = MaterialTheme.colorScheme.secondary  // Color dorado
            )
        },
        // Icono opcional al final (ej: boton de mostrar contrasena)
        trailingIcon = trailingIcon,
        // Transforma como se ve el texto (sin cambiar el valor real)
        visualTransformation = visualTransformation,
        // Modificadores del layout
        modifier = modifier.fillMaxWidth(),
        // Bordes redondeados
        shape = RoundedCornerShape(8.dp),
        // Si el campo esta habilitado o no
        enabled = enabled,
        // Si hay un error de validacion
        isError = isError,
        // Texto de ayuda o error
        supportingText = supportingText,
        // Solo una linea (no multilInea)
        singleLine = true,
        // Configuracion de colores personalizada
        colors = TextFieldDefaults.colors(
            // ======== COLORES DEL CONTENEDOR (FONDO) ========
            // Fondo cuando esta enfocado (el usuario esta escribiendo)
            focusedContainerColor = SurfaceDark,
            // Fondo cuando no esta enfocado
            unfocusedContainerColor = SurfaceDark,
            // Fondo cuando esta deshabilitado
            disabledContainerColor = SurfaceDark.copy(alpha = 0.5f),
            // Fondo cuando hay error
            errorContainerColor = SurfaceDark,

            // ======== COLORES DEL TEXTO ========
            // Texto cuando esta enfocado
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            // Texto cuando no esta enfocado
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

            // ======== COLORES DEL CURSOR ========
            cursorColor = MaterialTheme.colorScheme.secondary,  // Cursor dorado

            // ======== COLORES DEL INDICADOR (LINEA INFERIOR) ========
            // Los hacemos transparentes porque no queremos linea inferior
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            // La linea de error si la mostramos (rojo)
            errorIndicatorColor = Color.Red,

            // ======== COLORES DE LA ETIQUETA ========
            focusedLabelColor = MaterialTheme.colorScheme.secondary,  // Dorado cuando enfocado
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    )
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun StyledTextFieldPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        StyledTextField(
            value = "",
            onValueChange = { },
            label = "Usuario",
            leadingIcon = Icons.Filled.Person
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun StyledTextFieldWithTextPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        StyledTextField(
            value = "usuario123",
            onValueChange = { },
            label = "Usuario",
            leadingIcon = Icons.Filled.Person
        )
    }
}
