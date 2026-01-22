# Guia para el Asistente de IA: App de Casino "EL CLU8 DEL SIE7E"

Este documento sirve como guia para la IA que asiste en el desarrollo de la aplicacion de casino "EL CLU8 DEL SIE7E". El objetivo es asegurar un desarrollo consistente, mantenible y que siga las mejores practicas de Jetpack Compose.

---

## 1. Resumen del Proyecto

| Campo | Valor |
|-------|-------|
| **Nombre** | EL CLU8 DEL SIE7E |
| **Plataforma** | Android Nativo |
| **Lenguaje** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Arquitectura** | MVVM |
| **Min SDK** | 24 (Android 7.0) |
| **Target SDK** | 36 |

---

## 2. Estructura del Proyecto

```
com.example.el_clu8_del_sie7e/
│
├── MainActivity.kt              # Punto de entrada de la app
│
├── ui/
│   ├── screens/                 # Pantallas de la app
│   │   ├── SplashScreen.kt
│   │   ├── LoginScreen.kt
│   │   └── [FuturasScreens].kt
│   │
│   ├── components/              # Componentes reutilizables
│   │   ├── AppLogo.kt
│   │   ├── PrimaryButton.kt
│   │   ├── SecondaryButton.kt
│   │   └── StyledTextField.kt
│   │
│   ├── navigation/              # Sistema de navegacion
│   │   ├── Routes.kt            # Definicion de rutas
│   │   └── NavGraph.kt          # Grafo de navegacion
│   │
│   └── theme/                   # Configuracion del tema
│       ├── Color.kt             # Paleta de colores
│       ├── Theme.kt             # Configuracion del tema
│       └── Type.kt              # Tipografia
│
└── viewmodel/                   # ViewModels (logica de negocio)
    └── LoginViewModel.kt
```

---

## 3. Paleta de Colores

| Color | Hex | Uso |
|-------|-----|-----|
| **PrimaryRed** | `0xFF6D0000` | Elementos principales |
| **PrimaryVariantRed** | `0xFF4B0000` | Estados pressed, acentos |
| **AccentGold** | `0xFFFFD700` | Destacados, iconos, texto importante |
| **DarkBackground** | `0xFF1E1E1E` | Fondo de pantallas |
| **SurfaceDark** | `0xFF2E2E2E` | Fondo de campos de texto |
| **GradientCenter** | `0xFF851618` | Centro del gradiente |
| **GradientEdge** | `0xFF2B0C0D` | Borde del gradiente |
| **TextPrimary** | `White` | Texto principal |
| **TextSecondary** | `Gray` | Texto secundario |

---

## 4. Lista de Pantallas (15)

| # | Pantalla | Estado | Archivo |
|---|----------|--------|---------|
| 1 | SplashScreen | HECHA | `ui/screens/SplashScreen.kt` |
| 2 | LoginScreen | HECHA | `ui/screens/LoginScreen.kt` |
| 3 | RegisterScreen | Pendiente | - |
| 4 | LobbyScreen | Pendiente | - |
| 5 | BlackjackGameScreen | Pendiente | - |
| 6 | SlotsGameScreen | Pendiente | - |
| 7 | RouletteGameScreen | Pendiente | - |
| 8 | DepositScreen | Pendiente | - |
| 9 | WithdrawScreen | Pendiente | - |
| 10 | TransactionHistoryScreen | Pendiente | - |
| 11 | ProfileScreen | Pendiente | - |
| 12 | BonusesScreen | Pendiente | - |
| 13 | TournamentsScreen | Pendiente | - |
| 14 | SupportScreen | Pendiente | - |
| 15 | SettingsScreen | Pendiente | - |

---

## 5. Componentes Reutilizables

| Componente | Descripcion | Archivo |
|------------|-------------|---------|
| **AppLogo** | Logo con icono y nombre de la app | `ui/components/AppLogo.kt` |
| **PrimaryButton** | Boton principal (fondo dorado) | `ui/components/PrimaryButton.kt` |
| **SecondaryButton** | Boton secundario (borde dorado) | `ui/components/SecondaryButton.kt` |
| **StyledTextField** | Campo de texto personalizado | `ui/components/StyledTextField.kt` |
| **TopAppBar** | Barra superior (pendiente) | - |

---

## 6. Proceso para Crear una Nueva Pantalla

Cuando se pida crear una nueva pantalla, seguir estos pasos:

### Paso 1: Crear la ruta en Routes.kt
```kotlin
// En ui/navigation/Routes.kt
const val MI_PANTALLA = "mi_pantalla"
```

### Paso 2: Crear el ViewModel
```kotlin
// En viewmodel/MiPantallaViewModel.kt
class MiPantallaViewModel : ViewModel() {
    // Estado y logica aqui
}
```

### Paso 3: Crear la pantalla
```kotlin
// En ui/screens/MiPantallaScreen.kt
@Composable
fun MiPantallaScreen(navController: NavController) {
    // UI aqui, usando el fondo con gradiente
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(GradientCenter, GradientEdge),
                    radius = 900f
                )
            )
    ) {
        // Contenido
    }
}
```

### Paso 4: Agregar al NavGraph
```kotlin
// En ui/navigation/NavGraph.kt
composable(route = Routes.MI_PANTALLA) {
    MiPantallaScreen(navController = navController)
}
```

---

## 7. Reglas de Estilo de Codigo

1. **Colores**: SIEMPRE usar los colores definidos en `Color.kt`, NUNCA hardcodear
2. **Comentarios**: Todo el codigo debe estar bien comentado para juniors
3. **Componentes**: Usar los componentes reutilizables de `ui/components/`
4. **Fondo**: Todas las pantallas usan el gradiente radial (GradientCenter -> GradientEdge)
5. **Tema**: Acceder a colores via `MaterialTheme.colorScheme.xxx`
6. **Tipografia**: Usar `MaterialTheme.typography.xxx` para estilos de texto
7. **Estado**: Usar ViewModels para manejar el estado de las pantallas

---

## 8. Dependencias Principales

```kotlin
// Navegacion
implementation("androidx.navigation:navigation-compose:2.9.0-alpha01")

// Iconos extendidos
implementation("androidx.compose.material:material-icons-extended:1.7.8")

// Material 3
implementation("androidx.compose.material3:material3")
```

---

## 9. Instrucciones Especificas para la IA

1. **Al crear componentes**: Siempre agregar comentarios explicativos
2. **Al crear pantallas**: Usar el fondo con gradiente radial
3. **Al agregar navegacion**: Actualizar tanto Routes.kt como NavGraph.kt
4. **Al usar colores**: Importar desde `ui.theme.Color`
5. **Al crear ViewModels**: Documentar cada estado y funcion
6. **Codigo duplicado**: Evitar siempre, crear componentes reutilizables
