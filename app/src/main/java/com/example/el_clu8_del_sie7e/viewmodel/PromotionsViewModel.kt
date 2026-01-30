package com.example.el_clu8_del_sie7e.viewmodel

import androidx.lifecycle.ViewModel
import com.example.el_clu8_del_sie7e.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * =====================================================================================
 * PROMOTIONSVIEWMODEL.KT - VIEWMODEL PARA PANTALLA DE PROMOCIONES
 * =====================================================================================
 *
 * Este ViewModel gestiona el estado de la pantalla de promociones.
 *
 * RESPONSABILIDADES:
 * ------------------
 * - Cargar y mantener la lista de promociones disponibles
 * - Gestionar el filtro seleccionado (Todos, Casino Live, Slots, Roulette)
 * - Filtrar promociones según la categoría seleccionada
 *
 * NOTA IMPORTANTE:
 * ----------------
 * El balance del usuario NO se gestiona aquí. Se usa el BalanceViewModel
 * compartido que se pasa desde NavGraph para mantener el balance sincronizado
 * en toda la aplicación.
 *
 * =====================================================================================
 */

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

    // Lista completa de promociones (sin filtrar)
    private val allPromotions = listOf(
        PromotionModel(
            id = 1,
            title = "Bono de\nBienvenida Real",
            description = "Duplica tu primer depósito hasta\n$500.000 COP. Empieza a jugar\ncomo un rey hoy mismo",
            imageRes = R.drawable.promotion_banner_1,
            badgeText = "NUEVO",
            expiryText = "Expira en 24h",
            isMain = true,
            category = "Todos"
        ),
        PromotionModel(
            id = 2,
            title = "Giros dorados",
            description = "20 tiros gratis en tragamonedas,\nseleccionados todos los viernes.",
            imageRes = R.drawable.promotion_banner_2,
            badgeText = "NUEVO",
            isMain = false,
            category = "Slots"
        ),
        PromotionModel(
            id = 3,
            title = "Tragaperras perreando",
            description = "Disfruta de los ritmos latinos y gana\npremios increíbles al instante.",
            imageRes = R.drawable.promotion_banner_3,
            badgeText = "EXPRESS",
            isMain = false,
            category = "Slots"
        )
    )

    // Lista de promociones filtradas (lo que se muestra en la UI)
    private val _promotions = MutableStateFlow<List<PromotionModel>>(allPromotions)
    val promotions: StateFlow<List<PromotionModel>> = _promotions.asStateFlow()

    // Filtro seleccionado actualmente
    private val _selectedFilter = MutableStateFlow("Todos")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    /**
     * Cambia el filtro seleccionado y actualiza la lista de promociones
     *
     * @param filter El nuevo filtro a aplicar (Todos, Casino Live, Slots, Roulette)
     */
    fun setFilter(filter: String) {
        _selectedFilter.value = filter
        
        // Filtrar promociones según la categoría seleccionada
        _promotions.value = when (filter) {
            "Todos" -> allPromotions
            "Slots" -> allPromotions.filter { it.category == "Slots" || it.isMain }
            "Casino Live" -> allPromotions.filter { it.category == "Casino Live" || it.isMain }
            "Roulette" -> allPromotions.filter { it.category == "Roulette" || it.isMain }
            else -> allPromotions
        }
    }
}
