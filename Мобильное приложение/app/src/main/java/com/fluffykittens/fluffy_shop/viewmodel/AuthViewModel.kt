package com.fluffykittens.fluffy_shop.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    val isUserLoggedIn = mutableStateOf(false)
    val currentUser = mutableStateOf<Map<String, String>>(emptyMap())

    fun setUserLoggedIn(loggedIn: Boolean, userInfo: Map<String, String?>) {
        isUserLoggedIn.value = loggedIn
        currentUser.value = userInfo as Map<String, String>
    }

}