package com.example.el_clu8_del_sie7e.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * =====================================================================================
 * SLOTSVIEWMODEL.KT - VIEWMODEL PARA LA PANTALLA DE SLOTS
 * =====================================================================================
 *
 * Este ViewModel maneja la logica de negocio de la pantalla de Slots.
 *
 * RESPONSABILIDADES:
 * ------------------
 * 1. Gestionar la lista de slots disponibles
 * 2. Manejar los filtros (Todos, Nuevos, Jackpot, Clasico)
 * 3. Gestionar la busqueda de slots
 * 4. Mantener el estado de la UI
 *
 * ESTADOS:
 * --------
 * - searchQuery: Texto de busqueda del usuario
 * - selectedFilter: Filtro actualmente seleccionado
 * - filteredSlots: Lista de slots filtrados segun el filtro y busqueda
 *
 * =====================================================================================
 */
class SlotsViewModel : ViewModel() {

    // ==================================================================================
    // ESTADOS DE LA UI
    // ==================================================================================

    /**
     * Texto de busqueda ingresado por el usuario
     * Se actualiza en tiempo real mientras el usuario escribe
     */
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    /**
     * Filtro seleccionado actualmente
     * Valores posibles: "Todos", "Nuevos", "Jackpot", "Clasico"
     */
    private val _selectedFilter = MutableStateFlow("Todos")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    // ==================================================================================
    // FUNCIONES PUBLICAS
    // ==================================================================================

    /**
     * Actualiza el texto de busqueda
     *
     * @param query Nuevo texto de busqueda
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Actualiza el filtro seleccionado
     *
     * @param filter Nuevo filtro ("Todos", "Nuevos", "Jackpot", "Clasico")
     */
    fun updateSelectedFilter(filter: String) {
        _selectedFilter.value = filter
    }
}
