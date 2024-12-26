package com.fluffykittens.fluffy_shop.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object ApiService {
    suspend fun fetchProducts(): String {
        return withContext(Dispatchers.IO) {
            val url = URL("https://project13b-backend-fluffy-kittens.onrender.com/products")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                throw Exception("Response code: $responseCode")
            }
        }
    }

    suspend fun fetchProductDetails(productId: String): String {
        return withContext(Dispatchers.IO) {
            val url = URL("https://project13b-backend-fluffy-kittens.onrender.com/products/$productId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                throw Exception("Response code: $responseCode")
            }
        }
    }

    suspend fun createCustomer(userId: String?, name: String?, email: String?): String? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://project13b-backend-fluffy-kittens.onrender.com/customers")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val jsonInputString = """
                    {
                        "id": "$userId",
                        "name": "$name",
                        "email": "$email",
                        "phone": "N/A"
                    }
                """
                connection.outputStream.write(jsonInputString.toByteArray())

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    connection.inputStream.bufferedReader().use { it.readText() }
                } else {
                    throw Exception("Error response code: $responseCode")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun getFavoritesByUserId(customerId: String): String {
        return withContext(Dispatchers.IO) {
            val url = URL("https://project13b-backend-fluffy-kittens.onrender.com/favorites/$customerId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                throw Exception("Response code: $responseCode")
            }
        }
    }

    suspend fun addProductToFavorites(customerId: String, productId: String): String {
        return withContext(Dispatchers.IO) {
            val url = URL("https://project13b-backend-fluffy-kittens.onrender.com/favorites/$customerId/products/$productId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                throw Exception("Error response code: $responseCode")
            }
        }
    }

    suspend fun addProductToCart(customerId: String, productId: String) : String {
        return withContext(Dispatchers.IO) {
            val url = URL("https://project13b-backend-fluffy-kittens.onrender.com/cart/$customerId/products/$productId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                throw Exception("Error response code: $responseCode")
            }
        }
    }

    suspend fun getCartByUserId(customerId: String): String {
        return withContext(Dispatchers.IO) {
            val url = URL("https://project13b-backend-fluffy-kittens.onrender.com/cart/$customerId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                throw Exception("Response code: $responseCode")
            }
        }
    }

    suspend fun removeProductFromFavorites(customerId: String, productId: String): String {
        return withContext(Dispatchers.IO) {
            val url = URL("https://project13b-backend-fluffy-kittens.onrender.com/favorites/$customerId/products/$productId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "DELETE"
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                throw Exception("Error response code: $responseCode")
            }
        }
    }

    suspend fun removeProductFromCart(customerId: String, productId: String): String {
        return withContext(Dispatchers.IO) {
            val url = URL("https://project13b-backend-fluffy-kittens.onrender.com/cart/$customerId/products/$productId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "DELETE"
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                throw Exception("Error response code: $responseCode")
            }
        }
    }

    suspend fun placeOrder(customerId: String, productIds: List<String>): String {
        return withContext(Dispatchers.IO) {
            val orderId = java.util.UUID.randomUUID().toString()
            val url = URL("https://project13b-backend-fluffy-kittens.onrender.com/orders")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            val jsonInputString = JSONObject().apply {
                put("id", orderId) // Уникальный ID заказа
                put("customerId", customerId)
                put("total", calculateTotal(productIds)) // Метод для расчета общей стоимости
                put("status", "pending") // Статус заказа
            }.toString()
            // Отправляем запрос на создание заказа
            connection.outputStream.write(jsonInputString.toByteArray())

            val responseCode = connection.responseCode
            if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_CREATED) {
                throw Exception("Error response code: $responseCode")
            }
            val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
            println("Create Order Response: $responseMessage")  // Для отладки

            // Шаг 2: Добавление продуктов в заказ
            productIds.forEach { productId ->
                val productUrl = URL("https://project13b-backend-fluffy-kittens.onrender.com/orders/$orderId/products/$productId")
                val productConnection = productUrl.openConnection() as HttpURLConnection
                productConnection.requestMethod = "POST"
                productConnection.setRequestProperty("Content-Type", "application/json")
                productConnection.doOutput = true

                val productJsonInputString = JSONObject().apply {
                    put("productId", productId)
                }.toString()

                productConnection.outputStream.write(productJsonInputString.toByteArray())
                val productResponseCode = productConnection.responseCode
                val productResponseMessage = productConnection.inputStream.bufferedReader().use { it.readText() }
                println("Product Add Response: $productResponseMessage")

                if (productResponseCode != HttpURLConnection.HTTP_OK && productResponseCode != HttpURLConnection.HTTP_CREATED) {
                    throw Exception("Error response code when adding product: $productResponseCode")
                }

                removeProductFromCart(customerId, productId)
            }

            return@withContext orderId
        }
    }

    fun calculateTotal(productIds: List<String>): Double {
        return productIds.size * 10.0
    }
}
