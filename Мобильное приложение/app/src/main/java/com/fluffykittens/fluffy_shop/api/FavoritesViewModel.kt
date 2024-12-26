package com.fluffykittens.fluffy_shop.api

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class FavoritesViewModel : ViewModel() {

    private val _favoriteProducts = MutableStateFlow<List<Product>>(emptyList())
    val favoriteProducts: StateFlow<List<Product>> get() = _favoriteProducts

    fun fetchFavoriteProducts(customerId: String) {
        viewModelScope.launch {
            try {
                // Получаем JSON-ответ с избранными продуктами
                val favoriteResponse = ApiService.getFavoritesByUserId(customerId)

                // Извлекаем список productIds из ответа
                val productIds = parseFavoriteIds(favoriteResponse)

                // Загружаем данные продуктов по их id
                val products = fetchProductDetails(productIds)
                _favoriteProducts.value = products
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", e.message.toString())
            }
        }
    }

    // Измененная функция для обработки ответа как JSONObject
    private fun parseFavoriteIds(jsonResponse: String): List<String> {
        val ids = mutableListOf<String>()
        val jsonObject = JSONObject(jsonResponse)  // Работаем с JSONObject, а не JSONArray
        val productIds = jsonObject.getJSONArray("productIds")  // Извлекаем массив productIds

        for (i in 0 until productIds.length()) {
            ids.add(productIds.getString(i))  // Добавляем каждый productId в список
        }

        return ids
    }

    private suspend fun fetchProductDetails(productIds: List<String>): List<Product> {
        val products = mutableListOf<Product>()
        productIds.forEach { id ->
            try {
                val productResponse = ApiService.fetchProductDetails(id)
                val product = parseProduct(JSONObject(productResponse))
                products.add(product)
            } catch (e: Exception) {
                Log.e("FETCH_PRODUCT_ERROR", "Error fetching product with ID $id: ${e.message}")
            }
        }
        return products
    }

    private fun parseProduct(json: JSONObject): Product {
        return Product(
            id = json.getString("id"),
            name = json.getString("name"),
            description = json.getString("description"),
            price = json.getDouble("price"),
            stock = json.getInt("stock")
        )
    }
}
