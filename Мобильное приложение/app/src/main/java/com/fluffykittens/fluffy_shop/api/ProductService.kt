package com.fluffykittens.fluffy_shop.api


data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int
)

