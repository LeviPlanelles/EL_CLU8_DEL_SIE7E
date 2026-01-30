package com.example.el_clu8_del_sie7e.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.el_clu8_del_sie7e.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Modelo de datos para una promoción
data class PromotionModel(
    val id: Int,
    val title: String,
    val description: String,
    val imageRes: Int,
    val badgeText: String,
    val isMain: Boolean = false, // Define si es la tarjeta grande principal
    val expiryText: String? = null,
    val category: String = "General"
)

class PromotionsViewModel : ViewModel() {

    // Estado del balance del usuario (Simulado)
    private val _userBalance = MutableStateFlow("$5,000.00")
    val userBalance: StateFlow<String> = _userBalance.asStateFlow()

    // Lista de promociones
    private val _promotions = MutableStateFlow<List<PromotionModel>>(emptyList())
    val promotions: StateFlow<List<PromotionModel>> = _promotions.asStateFlow()

    // Filtro seleccionado
    private val _selectedFilter = MutableStateFlow("Todos")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    init {
        loadPromotions()
    }

    fun setFilter(filter: String) {
        _selectedFilter.value = filter
        // Aquí podrías filtrar la lista _promotions real
    }

    private fun loadPromotions() {
        _promotions.value = listOf(
            PromotionModel(
                id = 1,
                title = "Bono de\nBienvenida Real",
                description = "Duplica tu primer depósito hasta\n$500.000 COP. Empieza a jugar\ncomo un rey hoy mismo",
                imageRes = R.drawable.promotion_banner_1, // Rectangle 57.png renombrado
                badgeText = "NUEVO",
                expiryText = "Expira en 24h",
                isMain = true,
                category = "Todos"
            ),
            PromotionModel(
                id = 2,
                title = "Giros dorados",
                description = "20 tiros gratis en tragamonedas,\nseleccionados todos los viernes.",
                imageRes = R.drawable.promotion_banner_2, // Rectangle 134.png renombrado
                badgeText = "NUEVO",
                isMain = false,
                category = "Slots"
            ),
            PromotionModel(
                id = 3,
                title = "Tragaperras perreando",
                description = "Disfruta de los ritmos latinos y gana\npremios increíbles al instante.",
                imageRes = R.drawable.promotion_banner_3, // Rectangle 137.png renombrado
                badgeText = "EXPRESS",
                isMain = false,
                category = "Slots"
            )
        )
    }
}