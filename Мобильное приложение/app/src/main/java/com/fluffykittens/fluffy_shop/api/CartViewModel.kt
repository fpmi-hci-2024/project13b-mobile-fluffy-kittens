package com.fluffykittens.fluffy_shop.api

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class CartViewModel : ViewModel() {
    private val _cartProducts = MutableStateFlow<List<Product>>(emptyList())
    val cartProducts: StateFlow<List<Product>> get() = _cartProducts

    fun fetchCartProducts(customerId: String) {
        viewModelScope.launch {
            try {
                val cartResponse = ApiService.getCartByUserId(customerId)
                val productIds = parseCartIds(cartResponse)
                val products = fetchProductDetails(productIds)
                _cartProducts.value = products
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", e.message.toString())
            }
        }
    }


    private fun parseCartIds(jsonResponse: String): List<String> {
        val ids = mutableListOf<String>()
        val jsonObject = JSONObject(jsonResponse)
        val productIds = jsonObject.getJSONArray("productIds")

        for (i in 0 until productIds.length()) {
            ids.add(productIds.getString(i))
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

    fun removeProductFromCart(customerId: String, productId: String) {
        viewModelScope.launch {
            try {
                ApiService.removeProductFromCart(customerId, productId)
                _cartProducts.value = _cartProducts.value.filter { it.id != productId }
            } catch (e: Exception) {
                Log.e("REMOVE_PRODUCT_ERROR", "Error removing product: ${e.message}")
            }
        }
    }
}