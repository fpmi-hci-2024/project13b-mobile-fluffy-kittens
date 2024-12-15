package com.fluffykittens.fluffy_shop.api


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
}
