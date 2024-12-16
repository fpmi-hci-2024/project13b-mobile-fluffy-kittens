package com.fluffykittens.fluffy_shop.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    var isUserLoggedIn = mutableStateOf(false)
}