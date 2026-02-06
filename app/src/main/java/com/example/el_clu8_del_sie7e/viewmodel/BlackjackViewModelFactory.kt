package com.example.el_clu8_del_sie7e.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * =====================================================================================
 * BLACKJACKVIEWMODELFACTORY.KT - FACTORY PARA CREAR BLACKJACKVIEWMODEL
 * =====================================================================================
 *
 * Esta factory es necesaria porque BlackjackViewModel requiere BalanceViewModel
 * en su constructor, y el sistema de ViewModels de Android no puede crearlo
 * automáticamente sin esta factory.
 *
 * USO:
 * ```kotlin
 * val blackjackViewModel: BlackjackViewModel = viewModel(
 *     factory = BlackjackViewModelFactory(balanceViewModel)
 * )
 * ```
 * =====================================================================================
 */
class BlackjackViewModelFactory(
    private val balanceViewModel: BalanceViewModel
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlackjackViewModel::class.java)) {
            return BlackjackViewModel(balanceViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
