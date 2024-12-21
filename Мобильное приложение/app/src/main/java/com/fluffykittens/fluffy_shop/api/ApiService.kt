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
}
