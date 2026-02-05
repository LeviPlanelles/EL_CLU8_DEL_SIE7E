package com.example.el_clu8_del_sie7e.util

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.content.ContextCompat
import com.example.el_clu8_del_sie7e.R

/**
 * =====================================================================================
 * SOUNDMANAGER.KT - GESTOR DE SONIDOS PARA EL CASINO
 * =====================================================================================
 *
 * Este objeto singleton maneja todos los sonidos del casino:
 * - Sonidos de slots (giro, victoria, derrota)
 * - Sonidos de monedas
 * - Efectos especiales
 * - Vibración
 *
 * USO:
 * ```kotlin
 * // Inicializar en Application o Activity
 * SoundManager.initialize(context)
 * 
 * // Reproducir sonidos
 * SoundManager.playSpin()
 * SoundManager.playWin(bigWin = true)
 * SoundManager.playLose()
 * ```
 * =====================================================================================
 */
object SoundManager {

    private var soundPool: SoundPool? = null
    private var mediaPlayer: MediaPlayer? = null
    private var context: Context? = null
    
    // IDs de sonidos cargados
    private var soundSpin: Int = 0
    private var soundCoin: Int = 0
    private var soundWin: Int = 0
    private var soundBigWin: Int = 0
    private var soundJackpot: Int = 0
    private var soundLose: Int = 0
    private var soundClick: Int = 0
    private var soundReelStop: Int = 0
    
    // Control de vibración
    private var vibrator: Vibrator? = null
    
    // Volumen (0.0 - 1.0)
    var volume: Float = 0.8f
        set(value) {
            field = value.coerceIn(0f, 1f)
        }
    
    var isSoundEnabled: Boolean = true
    var isVibrationEnabled: Boolean = true

    /**
     * Inicializa el SoundManager con el contexto de la aplicación
     * Debe llamarse una vez, preferiblemente en onCreate de MainActivity
     */
    fun initialize(appContext: Context) {
        if (context != null) return // Ya inicializado
        
        context = appContext.applicationContext
        
        // Inicializar SoundPool
        soundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .build()
        
        // Cargar sonidos
        loadSounds()
        
        // Inicializar vibrador
        initVibrator()
    }
    
    /**
     * Carga todos los sonidos en memoria
     */
    private fun loadSounds() {
        val ctx = context ?: return
        
        try {
            soundPool?.let { pool ->
                // Sonidos de slots
                soundSpin = pool.load(ctx, R.raw.slot_spin, 1)
                soundCoin = pool.load(ctx, R.raw.coin_drop, 1)
                soundWin = pool.load(ctx, R.raw.win_normal, 1)
                soundBigWin = pool.load(ctx, R.raw.win_big, 1)
                soundJackpot = pool.load(ctx, R.raw.jackpot, 1)
                soundLose = pool.load(ctx, R.raw.lose, 1)
                soundClick = pool.load(ctx, R.raw.ui_click, 1)
                soundReelStop = pool.load(ctx, R.raw.reel_stop, 1)
            }
        } catch (e: Exception) {
            // Si falla la carga de sonidos, continuar sin ellos
        }
    }
    
    /**
     * Inicializa el servicio de vibración
     */
    private fun initVibrator() {
        val ctx = context ?: return
        
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = ctx.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            ctx.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    // ==================================================================================
    // SONIDOS DE SLOTS
    // ==================================================================================
    
    /**
     * Sonido de giro de rodillos
     */
    fun playSpin() {
        if (!isSoundEnabled) return
        try {
            soundPool?.play(soundSpin, volume, volume, 1, 0, 1.0f)
        } catch (e: Exception) {
            // Ignorar si falla la reproducción
        }
    }
    
    /**
     * Sonido cuando un rodillo se detiene
     */
    fun playReelStop() {
        if (!isSoundEnabled) return
        try {
            soundPool?.play(soundReelStop, volume * 0.6f, volume * 0.6f, 1, 0, 1.0f)
        } catch (e: Exception) {
            // Ignorar si falla la reproducción
        }
    }
    
    /**
     * Sonido de victoria
     * @param bigWin true si es victoria grande (>50€)
     * @param jackpot true si es jackpot (>100€)
     */
    fun playWin(bigWin: Boolean = false, jackpot: Boolean = false) {
        if (!isSoundEnabled) return
        
        try {
            when {
                jackpot -> {
                    soundPool?.play(soundJackpot, volume, volume, 1, 0, 1.0f)
                    vibrateJackpot()
                }
                bigWin -> {
                    soundPool?.play(soundBigWin, volume, volume, 1, 0, 1.0f)
                    vibrateWin()
                }
                else -> {
                    soundPool?.play(soundWin, volume * 0.8f, volume * 0.8f, 1, 0, 1.0f)
                    vibrateSmallWin()
                }
            }
        } catch (e: Exception) {
            // Ignorar si falla la reproducción
        }
    }
    
    /**
     * Sonido de victoria basado en el monto ganado
     * @param amount monto ganado
     */
    fun playWin(amount: Double) {
        val isJackpot = amount >= 100
        val isBigWin = amount >= 50
        playWin(bigWin = isBigWin, jackpot = isJackpot)
    }
    
    /**
     * Sonido de derrota
     */
    fun playLose() {
        if (!isSoundEnabled) return
        try {
            soundPool?.play(soundLose, volume * 0.7f, volume * 0.7f, 1, 0, 1.0f)
            vibrateLose()
        } catch (e: Exception) {
            // Ignorar si falla la reproducción
        }
    }
    
    /**
     * Sonido de monedas cayendo
     */
    fun playCoinDrop() {
        if (!isSoundEnabled) return
        try {
            soundPool?.play(soundCoin, volume, volume, 1, 0, 1.2f)
        } catch (e: Exception) {
            // Ignorar si falla la reproducción
        }
    }
    
    /**
     * Sonido de click en UI
     */
    fun playClick() {
        if (!isSoundEnabled) return
        try {
            soundPool?.play(soundClick, volume * 0.5f, volume * 0.5f, 1, 0, 1.0f)
        } catch (e: Exception) {
            // Ignorar si falla la reproducción
        }
    }

    // ==================================================================================
    // VIBRACIÓN
    // ==================================================================================
    
    /**
     * Vibración corta para victoria pequeña
     */
    private fun vibrateSmallWin() {
        if (!isVibrationEnabled) return
        vibrate(100)
    }
    
    /**
     * Vibración media para victoria normal
     */
    private fun vibrateWin() {
        if (!isVibrationEnabled) return
        vibratePattern(longArrayOf(0, 200, 100, 200))
    }
    
    /**
     * Vibración larga para jackpot
     */
    private fun vibrateJackpot() {
        if (!isVibrationEnabled) return
        vibratePattern(longArrayOf(0, 300, 100, 300, 100, 500))
    }
    
    /**
     * Vibración para derrota
     */
    private fun vibrateLose() {
        if (!isVibrationEnabled) return
        vibrate(50)
    }
    
    /**
     * Vibración simple
     */
    private fun vibrate(milliseconds: Long) {
        val vibratorService = vibrator ?: return
        
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibratorService.vibrate(
                    VibrationEffect.createOneShot(
                        milliseconds,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                @Suppress("DEPRECATION")
                vibratorService.vibrate(milliseconds)
            }
        } catch (e: Exception) {
            // Ignorar si falla la vibración
        }
    }
    
    /**
     * Vibración con patrón
     */
    private fun vibratePattern(pattern: LongArray) {
        val vibratorService = vibrator ?: return
        
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibratorService.vibrate(
                    VibrationEffect.createWaveform(pattern, -1)
                )
            } else {
                @Suppress("DEPRECATION")
                vibratorService.vibrate(pattern, -1)
            }
        } catch (e: Exception) {
            // Ignorar si falla la vibración
        }
    }

    // ==================================================================================
    // CONTROL GENERAL
    // ==================================================================================
    
    /**
     * Libera recursos
     * Llamar en onDestroy de la Activity
     */
    fun release() {
        soundPool?.release()
        soundPool = null
        mediaPlayer?.release()
        mediaPlayer = null
        context = null
    }
}
