# EL CLU8 DEL SIE7E - App de Casino

Una aplicacion de casino para Android desarrollada con Kotlin y Jetpack Compose.

---

## Indice

1. [Introduccion para Principiantes](#1-introduccion-para-principiantes)
2. [Requisitos Previos](#2-requisitos-previos)
3. [Configuracion del Entorno](#3-configuracion-del-entorno)
4. [Estructura del Proyecto](#4-estructura-del-proyecto)
5. [Conceptos Basicos de Kotlin](#5-conceptos-basicos-de-kotlin)
6. [Conceptos Basicos de Jetpack Compose](#6-conceptos-basicos-de-jetpack-compose)
7. [Como Funciona Esta App](#7-como-funciona-esta-app)
8. [Guia de Git para Principiantes](#8-guia-de-git-para-principiantes)
9. [Como Contribuir al Proyecto](#9-como-contribuir-al-proyecto)
10. [Recursos de Aprendizaje](#10-recursos-de-aprendizaje)

---

## 1. Introduccion para Principiantes

### Que es esta app?

EL CLU8 DEL SIE7E es una aplicacion de casino que incluira juegos como Blackjack, Tragaperras y Ruleta. Esta desarrollada usando tecnologias modernas de Android.

### Tecnologias que usamos

| Tecnologia | Que es | Para que la usamos |
|------------|--------|-------------------|
| **Kotlin** | Lenguaje de programacion | Escribir todo el codigo de la app |
| **Jetpack Compose** | Framework de UI | Crear las pantallas y componentes visuales |
| **Material Design 3** | Sistema de diseno | Estilos, colores, tipografia consistentes |
| **Navigation Compose** | Libreria de navegacion | Movernos entre pantallas |
| **ViewModel** | Componente de arquitectura | Manejar el estado y la logica |

### Arquitectura MVVM

Usamos el patron MVVM (Model-View-ViewModel):

```
┌─────────────────┐      ┌─────────────────┐      ┌─────────────────┐
│      MODEL      │ <--> │   VIEWMODEL     │ <--> │      VIEW       │
│                 │      │                 │      │                 │
│  - Datos        │      │  - Estado       │      │  - Composables  │
│  - API          │      │  - Logica       │      │  - UI           │
│  - Repository   │      │  - Eventos      │      │  - Pantallas    │
└─────────────────┘      └─────────────────┘      └─────────────────┘
```

- **View**: Las pantallas (Composables). Solo muestran datos y capturan eventos.
- **ViewModel**: Procesa la logica y mantiene el estado.
- **Model**: Los datos (APIs, base de datos, etc.)

---

## 2. Requisitos Previos

### Software necesario

1. **Android Studio** (version Koala 2024.1.1 o superior)
   - Descarga: https://developer.android.com/studio

2. **JDK 17** (normalmente viene con Android Studio)

3. **Git** (para control de versiones)
   - Windows: https://git-scm.com/download/win
   - Mac: `brew install git`
   - Linux: `sudo apt install git`

### Conocimientos recomendados

- Programacion basica (variables, funciones, condicionales, bucles)
- Orientacion a objetos (clases, herencia, interfaces)
- Git basico (clone, commit, push, pull)

No te preocupes si no dominas todo, el codigo esta comentado para aprender!

---

## 3. Configuracion del Entorno

### Paso 1: Instalar Android Studio

1. Descarga Android Studio desde https://developer.android.com/studio
2. Ejecuta el instalador
3. Sigue el asistente de configuracion
4. Cuando pregunte, instala el Android SDK

### Paso 2: Clonar el proyecto

Abre una terminal y ejecuta:

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/EL_CLU8_DEL_SIE7E.git

# Entrar a la carpeta
cd EL_CLU8_DEL_SIE7E
```

### Paso 3: Abrir en Android Studio

1. Abre Android Studio
2. Click en "Open"
3. Navega a la carpeta del proyecto y seleccionala
4. Espera a que Gradle sincronice (puede tardar unos minutos)

### Paso 4: Ejecutar la app

1. Conecta un telefono Android (con depuracion USB activada) o usa el emulador
2. Click en el boton verde "Run" (triangulo verde) o presiona Shift+F10
3. La app se instalara y abrira automaticamente

### Solucionar problemas comunes

**Error: "SDK location not found"**
- Ve a File > Project Structure > SDK Location
- Configura la ruta del Android SDK

**Error: "Gradle sync failed"**
- File > Invalidate Caches and Restart
- Luego File > Sync Project with Gradle Files

---

## 4. Estructura del Proyecto

```
EL_CLU8_DEL_SIE7E/
│
├── app/                              # Modulo principal de la app
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/.../             # Codigo Kotlin
│   │   │   │   ├── MainActivity.kt   # Punto de entrada
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/      # Pantallas
│   │   │   │   │   ├── components/   # Componentes reutilizables
│   │   │   │   │   ├── navigation/   # Navegacion
│   │   │   │   │   └── theme/        # Tema (colores, tipografia)
│   │   │   │   └── viewmodel/        # ViewModels
│   │   │   ├── res/                  # Recursos (imagenes, strings)
│   │   │   └── AndroidManifest.xml   # Configuracion de la app
│   │   └── build.gradle.kts          # Dependencias del modulo
│   │
├── gradle/                           # Configuracion de Gradle
├── build.gradle.kts                  # Configuracion raiz
├── settings.gradle.kts               # Settings de Gradle
├── AGENTS.md                         # Guia para la IA
└── README.md                         # Este archivo
```

### Archivos importantes explicados

| Archivo | Que contiene |
|---------|-------------|
| `MainActivity.kt` | Punto de entrada de la app |
| `ui/theme/Color.kt` | Todos los colores de la app |
| `ui/theme/Theme.kt` | Configuracion del tema Material |
| `ui/theme/Type.kt` | Estilos de tipografia |
| `ui/navigation/Routes.kt` | Nombres de las rutas |
| `ui/navigation/NavGraph.kt` | Grafo de navegacion |
| `ui/screens/*.kt` | Cada pantalla de la app |
| `ui/components/*.kt` | Componentes reutilizables |
| `viewmodel/*.kt` | Logica y estado de las pantallas |

---

## 5. Conceptos Basicos de Kotlin

### Variables

```kotlin
// Variable que puede cambiar (mutable)
var nombre = "Juan"
nombre = "Pedro"  // OK

// Variable que NO puede cambiar (inmutable)
val edad = 25
// edad = 26  // ERROR!

// Con tipo explicito
val precio: Double = 19.99
```

### Funciones

```kotlin
// Funcion basica
fun saludar() {
    println("Hola!")
}

// Funcion con parametros
fun saludar(nombre: String) {
    println("Hola, $nombre!")  // String interpolation
}

// Funcion con retorno
fun sumar(a: Int, b: Int): Int {
    return a + b
}

// Funcion de una linea
fun multiplicar(a: Int, b: Int) = a * b
```

### Clases

```kotlin
// Clase basica
class Persona(
    val nombre: String,   // Propiedad inmutable
    var edad: Int         // Propiedad mutable
) {
    fun presentarse() {
        println("Soy $nombre y tengo $edad anios")
    }
}

// Usar la clase
val persona = Persona("Juan", 25)
persona.presentarse()  // "Soy Juan y tengo 25 anios"
```

### Null Safety (Seguridad de nulos)

```kotlin
// Variable que puede ser null
var nombre: String? = "Juan"
nombre = null  // OK

// Variable que NO puede ser null
var apellido: String = "Perez"
// apellido = null  // ERROR!

// Acceso seguro
val longitud = nombre?.length  // Si nombre es null, longitud sera null

// Operador Elvis (valor por defecto)
val longitud2 = nombre?.length ?: 0  // Si es null, usa 0
```

### Lambdas (funciones anonimas)

```kotlin
// Lambda basica
val sumar = { a: Int, b: Int -> a + b }
val resultado = sumar(2, 3)  // 5

// Lambda como parametro (muy comun en Compose)
Button(onClick = { println("Click!") }) {
    Text("Presioname")
}
```

---

## 6. Conceptos Basicos de Jetpack Compose

### Que es Compose?

Jetpack Compose es el framework moderno de Android para crear interfaces de usuario.
En lugar de usar archivos XML, creamos la UI con funciones de Kotlin.

### Composables

Un Composable es una funcion que describe parte de la UI:

```kotlin
@Composable
fun Saludo() {
    Text("Hola Mundo!")
}
```

La anotacion `@Composable` le dice a Compose que esta funcion puede dibujar UI.

### Componentes basicos

```kotlin
// Texto
Text(
    text = "Hola",
    color = Color.White,
    fontSize = 16.sp
)

// Boton
Button(onClick = { /* accion */ }) {
    Text("Click aqui")
}

// Campo de texto
TextField(
    value = texto,
    onValueChange = { texto = it },
    label = { Text("Escribe algo") }
)

// Imagen
Image(
    painter = painterResource(R.drawable.foto),
    contentDescription = "Mi foto"
)
```

### Layouts (organizadores)

```kotlin
// Column: Elementos verticales (uno debajo de otro)
Column {
    Text("Primero")
    Text("Segundo")
    Text("Tercero")
}

// Row: Elementos horizontales (uno al lado de otro)
Row {
    Text("Izquierda")
    Text("Derecha")
}

// Box: Elementos superpuestos (uno encima de otro)
Box {
    Image(...)        // Fondo
    Text("Texto")     // Encima de la imagen
}
```

### Modifiers (modificadores)

Los Modifiers personalizan como se ve y comporta un componente:

```kotlin
Text(
    text = "Hola",
    modifier = Modifier
        .fillMaxWidth()           // Ocupa todo el ancho
        .padding(16.dp)           // Margen interno
        .background(Color.Blue)   // Color de fondo
        .clickable { }            // Hace clickeable
)
```

### Estado (State)

El estado es informacion que puede cambiar y afecta la UI:

```kotlin
@Composable
fun Contador() {
    // remember: guarda el valor entre recomposiciones
    // mutableStateOf: crea un valor observable
    var contador by remember { mutableStateOf(0) }
    
    Column {
        Text("Contador: $contador")
        Button(onClick = { contador++ }) {
            Text("Incrementar")
        }
    }
}
```

Cuando `contador` cambia, Compose redibuja automaticamente los elementos afectados.

### Navegacion

```kotlin
// Definir rutas
object Routes {
    const val HOME = "home"
    const val DETALLE = "detalle"
}

// NavGraph
@Composable
fun MiNavGraph() {
    val navController = rememberNavController()
    
    NavHost(navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(navController)
        }
        composable(Routes.DETALLE) {
            DetalleScreen(navController)
        }
    }
}

// Navegar desde una pantalla
Button(onClick = { navController.navigate(Routes.DETALLE) }) {
    Text("Ir a detalle")
}
```

---

## 7. Como Funciona Esta App

### Flujo de la aplicacion

```
[Usuario abre la app]
        |
        v
┌─────────────────┐
│  MainActivity   │ -- Configura el tema y la navegacion
└────────┬────────┘
         |
         v
┌─────────────────┐
│  SplashScreen   │ -- Muestra el logo 3 segundos
└────────┬────────┘
         |
         v
┌─────────────────┐
│  LoginScreen    │ -- Usuario ingresa credenciales
└────────┬────────┘
         |
         v
┌─────────────────┐
│  [LobbyScreen]  │ -- (Proxima pantalla a implementar)
└─────────────────┘
```

### Tema y colores

Todos los colores estan definidos en `ui/theme/Color.kt`:

```kotlin
val PrimaryRed = Color(0xFF6D0000)      // Rojo principal
val AccentGold = Color(0xFFFFD700)      // Dorado de acento
val DarkBackground = Color(0xFF1E1E1E)  // Fondo oscuro
val GradientCenter = Color(0xFF851618)  // Centro del gradiente
val GradientEdge = Color(0xFF2B0C0D)    // Borde del gradiente
```

### Fondo con gradiente

Todas las pantallas usan el mismo fondo con gradiente radial:

```kotlin
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
    // Contenido de la pantalla
}
```

### Componentes reutilizables

En lugar de repetir codigo, usamos componentes:

```kotlin
// En lugar de esto (repetitivo):
Button(
    onClick = { },
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.secondary
    ),
    shape = RoundedCornerShape(8.dp)
) {
    Text("BOTON")
}

// Usamos esto (reutilizable):
PrimaryButton(
    text = "BOTON",
    onClick = { }
)
```

---

## 8. Guia de Git para Principiantes

### Que es Git?

Git es un sistema de control de versiones. Te permite:
- Guardar el historial de cambios de tu codigo
- Trabajar en equipo sin pisarse los cambios
- Volver a versiones anteriores si algo sale mal

### Configuracion inicial (solo una vez)

```bash
# Configura tu nombre
git config --global user.name "Tu Nombre"

# Configura tu email
git config --global user.email "tu@email.com"
```

### Comandos esenciales

```bash
# Ver el estado actual (archivos modificados, etc.)
git status

# Ver los cambios en detalle
git diff

# Agregar archivos al "staging area" (preparar para commit)
git add archivo.kt          # Un archivo especifico
git add .                   # Todos los archivos modificados

# Crear un commit (guardar los cambios)
git commit -m "Descripcion de los cambios"

# Subir los cambios al servidor
git push

# Descargar los cambios del servidor
git pull

# Ver el historial de commits
git log --oneline
```

### Flujo de trabajo tipico

```bash
# 1. Antes de empezar, descarga los ultimos cambios
git pull

# 2. Haz tus cambios en el codigo...

# 3. Revisa que cambiaste
git status
git diff

# 4. Agrega los cambios
git add .

# 5. Crea un commit con una descripcion clara
git commit -m "Agrega pantalla de registro"

# 6. Sube los cambios
git push
```

### Ramas (Branches)

Las ramas permiten trabajar en funcionalidades sin afectar el codigo principal:

```bash
# Ver ramas
git branch

# Crear una nueva rama
git checkout -b feature/nueva-funcionalidad

# Cambiar de rama
git checkout main

# Subir una rama nueva al servidor
git push -u origin feature/nueva-funcionalidad
```

### Mensajes de commit

Escribe mensajes claros y descriptivos:

```bash
# Mal
git commit -m "cambios"
git commit -m "fix"

# Bien
git commit -m "Agrega validacion de campos en LoginScreen"
git commit -m "Corrige error de navegacion en SplashScreen"
git commit -m "Actualiza colores del tema segun nuevo diseno"
```

### Resolver conflictos

Cuando dos personas modifican el mismo archivo:

1. Git te avisara del conflicto al hacer `pull`
2. Abre el archivo y veras marcas como:
```
<<<<<<< HEAD
Tu codigo
=======
Codigo del otro
>>>>>>> branch
```
3. Edita el archivo dejando el codigo correcto
4. Elimina las marcas (`<<<<`, `====`, `>>>>`)
5. Haz `git add` y `git commit`

---

## 9. Como Contribuir al Proyecto

### Paso 1: Clonar y configurar

```bash
git clone https://github.com/tu-usuario/EL_CLU8_DEL_SIE7E.git
cd EL_CLU8_DEL_SIE7E
```

### Paso 2: Crear una rama para tu tarea

```bash
git checkout -b feature/nombre-de-tu-tarea
```

### Paso 3: Hacer cambios siguiendo las guias

- Lee el archivo `AGENTS.md` para conocer las convenciones
- Comenta tu codigo pensando en otros desarrolladores
- Usa los componentes reutilizables que ya existen
- Sigue la estructura de carpetas establecida

### Paso 4: Probar tu codigo

- Ejecuta la app y prueba que funcione
- Revisa que no hay errores en el IDE (subrayados rojos)
- Comprueba que las previews se ven correctamente

### Paso 5: Subir los cambios

```bash
git add .
git commit -m "Descripcion clara de los cambios"
git push -u origin feature/nombre-de-tu-tarea
```

### Paso 6: Crear Pull Request

1. Ve a GitHub
2. Veras un mensaje para crear un Pull Request
3. Describe tus cambios
4. Espera la revision del equipo

---

## 10. Recursos de Aprendizaje

### Kotlin

- [Kotlin Koans](https://play.kotlinlang.org/koans) - Ejercicios interactivos
- [Kotlin Docs](https://kotlinlang.org/docs/home.html) - Documentacion oficial
- [Kotlin for Android](https://developer.android.com/kotlin) - Guia de Android

### Jetpack Compose

- [Compose Tutorial](https://developer.android.com/jetpack/compose/tutorial) - Tutorial oficial
- [Compose Pathway](https://developer.android.com/courses/pathways/compose) - Curso gratuito
- [Compose Samples](https://github.com/android/compose-samples) - Ejemplos de codigo

### Android en general

- [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course) - Curso completo
- [Android Developer Guides](https://developer.android.com/guide) - Guias oficiales

### Git

- [Git - La guia sencilla](https://rogerdudler.github.io/git-guide/index.es.html) - Guia basica
- [Learn Git Branching](https://learngitbranching.js.org/?locale=es_ES) - Juego interactivo

### Videos recomendados (YouTube)

- **Phillip Lackner** - Tutoriales de Compose en ingles
- **Coding With Mitch** - Android avanzado
- **Android Developers** - Canal oficial de Google

---

## Contacto y Ayuda

Si tienes dudas:

1. Revisa este README y el archivo `AGENTS.md`
2. Busca en los comentarios del codigo
3. Pregunta al equipo

---

Hecho con Kotlin y Jetpack Compose
