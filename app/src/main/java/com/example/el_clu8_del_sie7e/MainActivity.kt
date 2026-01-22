package com.example.el_clu8_del_sie7e

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.el_clu8_del_sie7e.ui.navigation.NavGraph
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme

/**
 * =====================================================================================
 * MAINACTIVITY.KT - PUNTO DE ENTRADA DE LA APLICACION
 * =====================================================================================
 *
 * Esta es la Activity principal (y unica) de nuestra app.
 * En Jetpack Compose, normalmente solo necesitamos UNA Activity.
 *
 * QUE ES UNA ACTIVITY?
 * --------------------
 * Una Activity es un componente de Android que representa una pantalla.
 * En apps tradicionales con XML, cada pantalla era una Activity.
 * En Compose, usamos una Activity y navegamos entre Composables.
 *
 * FLUJO DE INICIO DE LA APP:
 * --------------------------
 * 1. Android crea MainActivity
 * 2. Se ejecuta onCreate()
 * 3. enableEdgeToEdge() hace que la app use toda la pantalla
 * 4. setContent{} define el contenido de Compose
 * 5. EL_CLU8_DEL_SIE7ETheme aplica nuestro tema (colores, tipografia)
 * 6. NavGraph() inicia el sistema de navegacion
 * 7. NavGraph muestra SplashScreen (la startDestination)
 *
 * HERENCIA: ComponentActivity
 * ---------------------------
 * ComponentActivity es la clase base moderna para Activities que usan Compose.
 * Proporciona las funciones necesarias para usar setContent{}.
 *
 * =====================================================================================
 */

/**
 * Activity principal de la aplicacion.
 *
 * Esta clase:
 * 1. Inicializa el sistema de UI de Compose
 * 2. Aplica el tema de la app
 * 3. Configura la navegacion
 *
 * NOTA: En el AndroidManifest.xml, esta Activity esta marcada como la principal
 * con el intent-filter MAIN/LAUNCHER, lo que hace que sea la que se abre
 * cuando el usuario toca el icono de la app.
 */
class MainActivity : ComponentActivity() {

    /**
     * onCreate() se ejecuta cuando la Activity se crea por primera vez.
     *
     * Es el lugar para:
     * - Configurar la UI
     * - Inicializar componentes
     *
     * IMPORTANTE: No pongas logica pesada aqui (llamadas a red, bases de datos)
     * porque bloquea la UI y hace que la app parezca lenta al abrir.
     *
     * @param savedInstanceState Estado guardado de una instancia anterior (si existe)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // Siempre llama a super.onCreate() primero
        super.onCreate(savedInstanceState)

        // ======================================================================
        // enableEdgeToEdge()
        // ======================================================================
        /**
         * Esta funcion hace que la app use toda la pantalla, incluyendo
         * las areas detras de la barra de estado y la barra de navegacion.
         *
         * Beneficios:
         * - La app se ve mas moderna e inmersiva
         * - Tenemos mas espacio para nuestra UI
         *
         * IMPORTANTE: Debes manejar los "insets" (margenes del sistema) en tu UI
         * para que el contenido no quede debajo de las barras del sistema.
         * Esto se hace con Modifier.windowInsetsPadding() o similares.
         */
        enableEdgeToEdge()

        // ======================================================================
        // setContent {}
        // ======================================================================
        /**
         * setContent{} es la funcion que conecta Compose con la Activity.
         * Todo lo que pongas dentro sera la UI de tu app.
         *
         * ESTRUCTURA:
         * setContent {
         *     TuTema {
         *         TuContenido()
         *     }
         * }
         */
        setContent {
            // Aplicamos el tema de la app
            // Esto hace que todos los composables internos tengan acceso a:
            // - MaterialTheme.colorScheme (colores)
            // - MaterialTheme.typography (estilos de texto)
            EL_CLU8_DEL_SIE7ETheme {
                // NavGraph es el punto de entrada de la navegacion
                // Contiene todas las pantallas y sabe como navegar entre ellas
                NavGraph()
            }
        }
    }
}

/**
 * =====================================================================================
 * CICLO DE VIDA DE UNA ACTIVITY (para referencia)
 * =====================================================================================
 *
 * onCreate()  -> La Activity se crea (aqui configuramos la UI)
 *      |
 *      v
 * onStart()   -> La Activity se vuelve visible
 *      |
 *      v
 * onResume()  -> La Activity esta en primer plano e interactiva
 *      |
 *      v
 * [Usuario interactua con la app]
 *      |
 *      v
 * onPause()   -> Otra Activity viene al frente (parcialmente visible)
 *      |
 *      v
 * onStop()    -> La Activity ya no es visible
 *      |
 *      v
 * onDestroy() -> La Activity se destruye (liberar recursos)
 *
 * NOTA: Con Compose y ViewModels, raramente necesitas sobrescribir estos metodos
 * porque Compose maneja el estado de forma automatica.
 *
 * =====================================================================================
 */
