package com.example.el_clu8_del_sie7e.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * =====================================================================================
 * SLOTGAMEVIEWMODELFACTORY.KT - FACTORY PARA CREAR SLOTGAMEVIEWMODEL
 * =====================================================================================
 *
 * Esta factory es necesaria porque SlotGameViewModel requiere Application y BalanceViewModel
 * en su constructor, y el sistema de ViewModels de Android no puede crearlo
 * automáticamente sin esta factory.
 *
 * USO:
 * ```kotlin
 * val slotGameViewModel: SlotGameViewModel = viewModel(
 *     factory = SlotGameViewModelFactory(application, balanceViewModel)
 * )
 * ```
 * =====================================================================================
 */
class SlotGameViewModelFactory(
    private val application: Application,
    private val balanceViewModel: BalanceViewModel
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SlotGameViewModel::class.java)) {
            return SlotGameViewModel(application, balanceViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
