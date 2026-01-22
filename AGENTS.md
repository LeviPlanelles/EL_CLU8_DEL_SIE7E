
# Guía para el Asistente de IA: App de Casino "EL CLU8 DEL SIE7E"

Este documento sirve como guía para la IA que asiste en el desarrollo de la aplicación de casino "EL CLU8 DEL SIE7E". El objetivo es asegurar un desarrollo consistente, mantenible y que siga las mejores prácticas de Jetpack Compose, adaptado a un equipo con poca experiencia.

## 1. Resumen del Proyecto

- **Nombre de la App:** EL CLU8 DEL SIE7E
- **Plataforma:** Android Nativo
- **UI Framework:** Jetpack Compose
- **Objetivo:** Crear una aplicación de casino con una estética lujosa y moderna.
- **Número de Pantallas:** 15

## 2. Estética y Tema (`ui/theme/`)

La aplicación debe seguir una paleta de colores y un estilo visual consistentes.

- **Paleta de Colores:**
  - **Primario (Rojo Oscuro):** `0xFF6D0000`
  - **Primario Variante (Rojo más Oscuro):** `0xFF4B0000`
  - **Acento (Dorado/Amarillo):** `Color.Yellow` o un dorado más específico como `0xFFFFD700`.
  - **Fondo Oscuro (Gris):** `0xFF1E1E1E`
  - **Texto Primario:** `Color.White` o un gris claro.
  - **Texto Secundario:** `Color.Gray`

- **Tipografía:**
  - **Títulos:** Fuente en negrita (Bold), elegante.
  - **Cuerpo:** Fuente regular.
  - Se definirá en el fichero `ui/theme/Type.kt`.

- **Estilo General:**
  - Componentes con bordes redondeados.
  - Iconografía clara y minimalista.
  - Uso de gradientes sutiles en los fondos, como en la `SplashScreen`.

**Instrucción para la IA:** Al crear nuevos componentes, utiliza siempre los colores y estilos definidos en `ui/theme/Theme.kt` y `ui/theme/Type.kt`.

## 3. Estructura del Proyecto

Para mantener el código organizado, seguiremos la siguiente estructura de paquetes:

```
com.example.el_clu8_del_sie7e/
├── ui/
│   ├── screens/         # Cada pantalla será un fichero aquí.
│   │   ├── SplashScreen.kt
│   │   ├── LoginScreen.kt
│   │   └── ...
│   ├── components/      # Componentes reutilizables (botones, text fields).
│   ├── theme/           # Ficheros de tema (Theme.kt, Color.kt, Type.kt).
│   └── navigation/      # Lógica de navegación (NavGraph, Routes).
├── viewmodel/           # ViewModels para cada pantalla.
└── data/                # Clases de datos, repositorios (si aplica).
```

**Instrucción para la IA:** Crea los nuevos ficheros en los paquetes correspondientes según esta estructura.

## 4. Componentes Reutilizables (`ui/components/`)

Identificamos varios componentes que se repetirán. Debemos crearlos como Composables genéricos.

- `StyledTextField`: Un campo de texto con ícono, fondo oscuro y bordes redondeados.
- `PrimaryButton`: El botón principal de acción (ej. "INICIAR SESIÓN", "CREAR CUENTA").
- `SecondaryButton`: El botón secundario (ej. "REGISTRARSE").
- `AppLogo`: El logo de la corona y el texto "EL CLU8 DEL SIE7E".
- `TopAppBar`: Barra superior con título y, opcionalmente, un botón de retroceso.

**Instrucción para la IA:** Cuando pida crear un componente que se parezca a uno de la lista, crea un Composable reutilizable en la carpeta `ui/components/`.

## 5. Navegación (`ui/navigation/`)

Usaremos `androidx.navigation.compose` para gestionar el flujo entre pantallas.

1.  **Routes.kt:** Un fichero para definir las rutas de todas las pantallas como constantes de tipo `String`.
2.  **NavGraph.kt:** Un Composable que contendrá el `NavHost` y definirá el grafo de navegación, asociando cada ruta a su Composable de pantalla.

**Instrucción para la IA:** Al crear una nueva pantalla, añádela siempre al `NavGraph.kt` y crea su ruta en `Routes.kt`.

## 6. Lista de Pantallas (15)

Aquí está la lista actualizada de las 15 pantallas que se desarrollarán, basada en la imagen proporcionada:

1.  `SplashScreen` (Pantalla de bienvenida) - **¡Hecha!**
2.  `LoginScreen` (Inicio de sesión)
3.  `RegisterScreen` (Registro de usuario)
4.  `LobbyScreen` (Lobby principal con categorías de juegos)
5.  `BlackjackGameScreen` (Juego de Blackjack)
6.  `SlotsGameScreen` (Juego de Tragaperras/Slots)
7.  `RouletteGameScreen` (Juego de la Ruleta)
8.  `DepositScreen` (Pantalla para depositar fondos)
9.  `WithdrawScreen` (Pantalla para retirar fondos)
10. `TransactionHistoryScreen` (Historial de transacciones)
11. `ProfileScreen` (Perfil del usuario)
12. `BonusesScreen` (Pantalla de bonos y promociones)
13. `TournamentsScreen` (Pantalla de torneos)
14. `SupportScreen` (Ayuda y soporte)
15. `SettingsScreen` (Ajustes de la aplicación)

## 7. Gestión de Estado (`viewmodel/`)

- Cada pantalla (`Screen`) tendrá su propio `ViewModel`.
- El `ViewModel` se encargará de la lógica de negocio y de exponer el estado a la UI usando `StateFlow` o `MutableState`.
- Los Composables deben ser lo más "tontos" posible (stateless), recibiendo el estado y los eventos como parámetros (State Hoisting).

**Instrucción para la IA:** Para cada nueva pantalla, crea su `ViewModel` correspondiente. El Composable de la pantalla deberá recibir el estado y las funciones de evento desde su `ViewModel`.

---

**Ejemplo de Petición del Usuario:**
_“Crea la pantalla de Login”_

**Proceso que debe seguir la IA:**
1.  Crear el fichero `LoginScreen.kt` en `ui/screens/`.
2.  Crear el fichero `LoginViewModel.kt` en `viewmodel/`.
3.  Implementar la UI en `LoginScreen.kt` usando los componentes de `ui/components/` y el tema de `ui/theme/`.
4.  Implementar la lógica y el estado en `LoginViewModel.kt`.
5.  Añadir la ruta `login` en `ui/navigation/Routes.kt`.
6.  Añadir la pantalla al `NavHost` en `ui/navigation/NavGraph.kt`.
7.  Asegurarse de que el código esté bien comentado para facilitar su comprensión.
