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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fluffykittens.fluffy_shop.api.ProductsViewModel
import com.fluffykittens.fluffy_shop.ui.theme.Fluffy_shopTheme




class MainActivity : ComponentActivity() {
    private val productsViewModel: ProductsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Fluffy_shopTheme {
                FluffyShopScreen()
            }
        }
    }
}

@Composable
fun FluffyShopScreen() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TopBar(navController)
            NavHost(
                navController = navController,
                startDestination = "startPage",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("startPage") {
                    MainContent(navController)
                }
                composable("catalogPage") {
                    CatalogPage()
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

@Composable
fun TopBar(navController: NavController) { // Передаем NavController как параметр
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.paw),
            contentDescription = "Paw Icon",
            modifier = Modifier
                .size(30.dp)
                .clickable { // Добавляем обработчик нажатия
                    navController.navigate("startPage") {
                        popUpTo("startPage") { inclusive = true } // Очищаем стек навигации, чтобы избежать возврата назад
                    }
                }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Fluffy Shop",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(Icons.Default.ShoppingCart, contentDescription = "Basket")
            Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorites")
            Icon(Icons.Default.Person, contentDescription = "Profile")
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
            style = androidx.compose.ui.text.TextStyle(
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