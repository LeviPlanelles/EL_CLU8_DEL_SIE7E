package com.example.el_clu8_del_sie7e

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * =====================================================================================
 * CASINOAPPLICATION.KT - CLASE APPLICATION PERSONALIZADA
 * =====================================================================================
 *
 * Esta clase se ejecuta ANTES que cualquier Activity cuando la app se inicia.
 * Es el lugar ideal para inicializar librerías y configuraciones globales.
 *
 * POR QUÉ NECESITAMOS ESTA CLASE:
 * -------------------------------
 * Firebase necesita ser inicializado antes de usarlo. Aunque el plugin
 * google-services intenta hacerlo automáticamente, a veces falla y da el error
 * "configuration not found".
 *
 * Al crear esta clase Application e inicializar Firebase aquí, nos aseguramos
 * de que Firebase esté listo antes de que cualquier pantalla intente usarlo.
 *
 * CONFIGURACIÓN:
 * --------------
 * Esta clase está registrada en AndroidManifest.xml con el atributo:
 * android:name=".CasinoApplication"
 *
 * =====================================================================================
 */
class CasinoApplication : Application() {

    /**
     * onCreate() se ejecuta cuando la aplicación se inicia.
     * Este método se llama antes que cualquier Activity.
     */
    override fun onCreate() {
        super.onCreate()

        // ======================================================================
        // INICIALIZACIÓN DE FIREBASE
        // ======================================================================
        /**
         * Inicializamos Firebase manualmente para evitar el error
         * "configuration not found".
         *
         * FirebaseApp.initializeApp() lee el archivo google-services.json
         * y configura todos los servicios de Firebase (Auth, Firestore, etc.)
         */
        FirebaseApp.initializeApp(this)
    }
}
