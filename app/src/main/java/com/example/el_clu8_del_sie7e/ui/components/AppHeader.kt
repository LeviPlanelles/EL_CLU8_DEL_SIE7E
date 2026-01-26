package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.theme.Gideon
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.navigation.Routes
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme


@Composable
fun AppHeader(
    balance: String,
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    Column(modifier = modifier.background(Color.Black)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp, vertical = 1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // -------------------------
            // LOGO (izquierda)
            // -------------------------
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_crown),
                    contentDescription = "Logo",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(49.dp)
                        .offset(y = (-4).dp)
                )

                Spacer(modifier = Modifier.width(9.dp))

                Column {
                    Text(
                        text = "EL CLU8",
                        style = TextStyle(
                            fontFamily = Gideon,
                            fontSize = 38.sp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFFCC00), // dorado claro (arriba)
                                    Color(0xFFFF9900)  // dorado oscuro (abajo)
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            )
                        ),
                        lineHeight = 14.sp,
                        fontFamily = Gideon,
                        fontSize = 21.sp,
                        color = AccentGold
                    )
                    Text(
                        text = "DEL SIE7E",
                        style = TextStyle(
                            fontFamily = Gideon,
                            fontSize = 38.sp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFFCC00), // dorado claro (arriba)
                                    Color(0xFFFF9900)  // dorado oscuro (abajo)
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            )
                        ),
                        lineHeight = 14.sp,
                        fontFamily = Gideon,
                        fontSize = 17.sp,
                        color = AccentGold
                    )
                }
            }

            // -------------------------
            // BALANCE (derecha)
            // -------------------------
            Surface(
                modifier = Modifier
                    .border(width = 0.5.dp,
                        color = AccentGold,
                        shape = MaterialTheme.shapes.medium)
                    .clickable {
                        // Navegar a la pantalla de cartera (WalletScreen)
                        navController?.navigate(Routes.WALLET_SCREEN)
                    },
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF3A3A3A),
                tonalElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 11.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wallet),
                        contentDescription = "Cartera",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = balance,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // -------------------------
        // LINEA DIVISORIA
        // -------------------------
        HorizontalDivider(
            color = AccentGold.copy(alpha = 0.4f),
            thickness = 2.dp
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun AppHeaderPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        AppHeader(
            balance = "$5.000.00",
            navController = rememberNavController()
        )
    }
}
