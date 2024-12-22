package com.fluffykittens.fluffy_shop.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject


class ProductDetailViewModel : ViewModel() {
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> get() = _product

    fun fetchProductDetails(productId: String) {
        viewModelScope.launch {
            try {
                val response = ApiService.fetchProductDetails(productId)
                val product = parseProduct(response)
                _product.value = product
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    private fun parseProduct(jsonResponse: String): Product {
        val jsonObject = JSONObject(jsonResponse)
        return Product(
            id = jsonObject.getString("id"),
            name = jsonObject.getString("name"),
            description = jsonObject.getString("description"),
            price = jsonObject.getDouble("price"),
            stock = jsonObject.getInt("stock")
        )
    }
}
