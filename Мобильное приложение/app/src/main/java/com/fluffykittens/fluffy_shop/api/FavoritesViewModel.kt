package com.fluffykittens.fluffy_shop.api

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class FavoritesViewModel : ViewModel() {

    fun addProductToFavorites(customerId: String, productId: String) {
        viewModelScope.launch {
            try {
                ApiService.addProductToFavorites(customerId, productId)
                // Обновление локального состояния или уведомление пользователя
            } catch (e: Exception) {
                e.printStackTrace()
                // Обработка ошибки, например, показать уведомление
            }
        }
    }
}
