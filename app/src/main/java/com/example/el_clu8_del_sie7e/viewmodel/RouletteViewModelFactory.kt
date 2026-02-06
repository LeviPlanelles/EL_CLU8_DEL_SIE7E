package com.example.el_clu8_del_sie7e.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * =====================================================================================
 * ROULETTEVIEWMODELFACTORY.KT - FACTORY PARA CREAR ROULETTEVIEWMODEL
 * =====================================================================================
 *
 * Esta factory es necesaria porque RouletteViewModel requiere BalanceViewModel
 * en su constructor, y el sistema de ViewModels de Android no puede crearlo
 * automáticamente sin esta factory.
 *
 * USO:
 * ```kotlin
 * val rouletteViewModel: RouletteViewModel = viewModel(
 *     factory = RouletteViewModelFactory(balanceViewModel)
 * )
 * ```
 * =====================================================================================
 */
class RouletteViewModelFactory(
    private val balanceViewModel: BalanceViewModel
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RouletteViewModel::class.java)) {
            return RouletteViewModel(balanceViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
