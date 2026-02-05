package com.example.el_clu8_del_sie7e.util

import android.content.Context
import android.media.SoundPool
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat
import com.example.el_clu8_del_sie7e.R

/**
 * =====================================================================================
 * SOUNDSLOTMANAGER.KT - GESTOR DE SONIDOS ESPECÍFICO PARA SLOTS
 * =====================================================================================
 *
 * Versión simplificada del SoundManager que maneja solo los sonidos del juego de slots.
 * Esta versión no requiere archivos de sonido externos - usa vibración y efectos visuales
 * como feedback principal.
 *
 * PARA AGREGAR SONIDOS REALES:
 * 1. Descarga sonidos de casino de freesound.org
 * 2. Colócalos en app/src/main/res/raw/
 * 3. Usa SoundManager.kt en lugar de esta clase
 * =====================================================================================
 */
class SoundSlotManager private constructor() {

    companion object {
        @Volatile
        private var instance: SoundSlotManager? = null
        
        fun getInstance(): SoundSlotManager {
            return instance ?: synchronized(this) {
                instance ?: SoundSlotManager().also { instance = it }
            }
        }
    }

    private var context: Context? = null
    private var vibrator: Vibrator? = null
    private var soundPool: SoundPool? = null
    
    var isSoundEnabled: Boolean = true
    var isVibrationEnabled: Boolean = true

    fun initialize(ctx: Context) {
        try {
            context = ctx.applicationContext
            vibrator = ContextCompat.getSystemService(ctx, Vibrator::class.java)
            
            // Intentar cargar sonidos si existen
            try {
                soundPool = SoundPool.Builder().setMaxStreams(5).build()
            } catch (e: Exception) {
                // SoundPool no disponible
                soundPool = null
            }
        } catch (e: Exception) {
            // Ignorar si falla la inicialización
        }
    }

    // ==================================================================================
    // SONIDOS (Simulados con vibración si no hay archivos)
    // ==================================================================================
    
    fun playSpin() {
        if (!isVibrationEnabled) return
        try {
            // Vibración sutil durante el giro
            vibrator?.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.EFFECT_TICK))
        } catch (e: Exception) {
            // Ignorar si falla la vibración
        }
    }
    
    fun playReelStop() {
        if (!isVibrationEnabled) return
        try {
            vibrator?.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.EFFECT_CLICK))
        } catch (e: Exception) {
            // Ignorar si falla la vibración
        }
    }
    
    fun playWin(amount: Double) {
        if (!isVibrationEnabled) return
        
        try {
            when {
                amount >= 100 -> {
                    // Jackpot - vibración larga y fuerte
                    vibrator?.vibrate(VibrationEffect.createWaveform(
                        longArrayOf(0, 300, 100, 300, 100, 500, 100, 200),
                        intArrayOf(0, 255, 0, 255, 0, 255, 0, 255),
                        -1
                    ))
                }
                amount >= 50 -> {
                    // Victoria grande
                    vibrator?.vibrate(VibrationEffect.createWaveform(
                        longArrayOf(0, 200, 100, 200, 100, 400),
                        intArrayOf(0, 255, 0, 255, 0, 255),
                        -1
                    ))
                }
                else -> {
                    // Victoria normal
                    vibrator?.vibrate(VibrationEffect.createOneShot(150, 200))
                }
            }
        } catch (e: Exception) {
            // Ignorar si falla la vibración
        }
    }
    
    fun playLose() {
        if (!isVibrationEnabled) return
        try {
            // Vibración corta y suave
            vibrator?.vibrate(VibrationEffect.createOneShot(80, 100))
        } catch (e: Exception) {
            // Ignorar si falla la vibración
        }
    }
    
    fun playCoinDrop() {
        if (!isVibrationEnabled) return
        try {
            vibrator?.vibrate(VibrationEffect.createOneShot(50, 150))
        } catch (e: Exception) {
            // Ignorar si falla la vibración
        }
    }
    
    fun playClick() {
        if (!isVibrationEnabled) return
        try {
            vibrator?.vibrate(VibrationEffect.createOneShot(20, 50))
        } catch (e: Exception) {
            // Ignorar si falla la vibración
        }
    }

    fun release() {
        try {
            soundPool?.release()
            soundPool = null
            context = null
        } catch (e: Exception) {
            // Ignorar si falla la liberación
        }
    }
}
