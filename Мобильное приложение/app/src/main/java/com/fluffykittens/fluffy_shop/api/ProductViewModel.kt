package com.fluffykittens.fluffy_shop.api

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class ProductsViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val response = ApiService.fetchProducts()
                val productsList = parseProducts(response)
                _products.value = productsList
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", e.message.toString())
            }
        }
    }

    private fun parseProducts(jsonResponse: String): List<Product> {
        val products = mutableListOf<Product>()
        val jsonObject = JSONObject(jsonResponse)

        jsonObject.keys().forEach { key ->
            val productJson = jsonObject.getJSONObject(key)
            products.add(
                Product(
                    id = productJson.getString("id"),
                    name = productJson.getString("name"),
                    description = productJson.getString("description"),
                    price = productJson.getDouble("price"),
                    stock = productJson.getInt("stock")
                )
            )
        }

        return products
    }
}
