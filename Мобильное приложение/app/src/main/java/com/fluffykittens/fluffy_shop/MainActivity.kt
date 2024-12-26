package com.fluffykittens.fluffy_shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.jwt.JWT
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.fluffykittens.fluffy_shop.api.ApiService
import com.fluffykittens.fluffy_shop.api.ProductsViewModel
import com.fluffykittens.fluffy_shop.ui.theme.Fluffy_shopTheme
import com.fluffykittens.fluffy_shop.ui.theme.ProductDetailPage
import com.fluffykittens.fluffy_shop.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : ComponentActivity() {
    private val productsViewModel: ProductsViewModel by viewModels()
    val url = URL("https://project13b-backend-fluffy-kittens.onrender.com/customers")
    private lateinit var account: Auth0
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        account = Auth0(
            "aCQw2anlpSj6sDinDe76Bt6fiYs4WWHo",
            "dev-03u3jiv05cfvetyf.us.auth0.com"
        )

        val savedToken = getToken()
        authViewModel.isUserLoggedIn.value = savedToken != null // Проверка сохраненного токена

        setContent {
            Fluffy_shopTheme {
                FluffyShopScreen(authViewModel)
            }
        }
    }

    private fun loginWithBrowser(authViewModel: AuthViewModel) {
        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            .start(this, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                }

                override fun onSuccess(credentials: Credentials) {
                    val idToken = credentials.idToken

                    val jwt = JWT(idToken)
                    val userId = jwt.getClaim("sub").asString()
                    val email = jwt.getClaim("email").asString()
                    val name = jwt.getClaim("name").asString()

                    saveUserInfo(userId, name, email)

                    mapOf("user_id" to userId, "name" to name, "email" to email).let {
                        authViewModel.setUserLoggedIn(true,
                            it
                        )
                    }
                    println("Успешная авторизация")
                    sendCustomerData(userId, name, email)
                }
            })
    }

    private fun saveUserInfo(userId: String?, name: String?, email: String?) {
        val sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("user_id", userId)
            putString("name", name)
            putString("email", email)
            apply()
        }
    }


    private fun sendCustomerData(userId: String?, name: String?, email: String?) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = ApiService.createCustomer(userId, name, email)
                if (response != null) {
                    // Успешно добавили пользователя в базу
                    println("Customer added successfully: $response")
                }
            } catch (e: Exception) {
                // Обработка ошибки
                e.printStackTrace()
            }
        }
    }

    private fun logout(authViewModel: AuthViewModel) {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(this, object : Callback<Void?, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    // Обработка ошибки
                }

                override fun onSuccess(payload: Void?) {
                    authViewModel.isUserLoggedIn.value = false
                    authViewModel.currentUser.value = emptyMap()
                    clearUserInfo()
                }
            })
    }

    private fun clearUserInfo() {
        val sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }

    private fun getToken(): String? {
        val sharedPref = getSharedPreferences("auth", MODE_PRIVATE)
        return sharedPref.getString("access_token", null)
    }

    @Composable
    fun TopBar(navController: NavController, authViewModel: AuthViewModel) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Логотип
            Image(
                painter = painterResource(id = R.drawable.paw),
                contentDescription = "Paw Icon",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.navigate("startPage") {
                            popUpTo("startPage") { inclusive = true }
                        }
                    }
            )

            // Название приложения
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Fluffy Shop",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Распределение элементов вправо
            Spacer(modifier = Modifier.weight(1f))

            // Элементы авторизации
            if (authViewModel.isUserLoggedIn.value) {
                // Иконка "Избранное"
                IconButton(onClick = {
                    navController.navigate("favoritesPage")
                }) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Favorites")
                }

                // Иконка "Корзина"
                IconButton(onClick = {
                    // Действие для перехода в корзину
                }) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                }

                // Иконка "Выход"
                IconButton(onClick = {
                    logout(authViewModel)
                }) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Logout")
                }
            } else {
                // Иконка авторизации
                IconButton(onClick = {
                    loginWithBrowser(authViewModel)
                }) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Login")
                }
            }
        }
    }


    @Composable
    fun FluffyShopScreen(authViewModel: AuthViewModel) {
        val navController = rememberNavController()

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                TopBar(navController, authViewModel)
                NavHost(
                    navController = navController,
                    startDestination = "startPage",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("startPage") {
                        MainContent(navController)
                    }
                    composable("catalogPage") {
                        CatalogPage(navController)
                    }
                    composable("productDetailPage/{productId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId") ?: ""
                        ProductDetailPage(productId, navController, authViewModel)
                    }
                    composable("aboutUsPage") {
                        AboutUsPage()
                    }
                    composable("contactsPage") {
                        ContactsPage()
                    }
                }
            }
        }
    }


    val caveatFontFamily = FontFamily(
        Font(R.font.caveat_regular)
    )

    @Composable
    fun MainContent(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationMenu(navController)
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.cat_on_laptop),
                contentDescription = "Cat typing on a laptop",
                modifier = Modifier.size(400.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Fluffy Shop - Technology with a Purr-fect Quality!",
                style = TextStyle(
                    fontFamily = caveatFontFamily,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }
    }

    @Composable
    fun NavigationMenu(navController: NavController) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            NavigationButton("CATALOG", Modifier.weight(1f), navController)
            NavigationButton("DISCOUNTS", Modifier.weight(1f), navController)
            NavigationButton("ABOUT US", Modifier.weight(1f), navController)
            NavigationButton("CONTACTS", Modifier.weight(1f), navController)
        }
    }

    @Composable
    fun NavigationButton(text: String, modifier: Modifier = Modifier, navController: NavController) {
        OutlinedButton(
            onClick = {
                when (text) {
                    "CATALOG" -> navController.navigate("catalogPage")
                    "ABOUT US" -> navController.navigate("aboutUsPage")
                    "CONTACTS" -> navController.navigate("contactsPage")
                }
            },
            modifier = modifier.padding(horizontal = 4.dp),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }


}






