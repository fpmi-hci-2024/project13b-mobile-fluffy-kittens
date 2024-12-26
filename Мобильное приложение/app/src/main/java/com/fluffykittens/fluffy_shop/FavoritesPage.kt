package com.fluffykittens.fluffy_shop.ui.theme



import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fluffykittens.fluffy_shop.api.ApiService
import com.fluffykittens.fluffy_shop.api.FavoritesViewModel
import com.fluffykittens.fluffy_shop.api.Product
import com.fluffykittens.fluffy_shop.api.ProductDetailViewModel
import com.fluffykittens.fluffy_shop.api.ProductsViewModel
import com.fluffykittens.fluffy_shop.viewmodel.AuthViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
//
//@Composable
//fun FavoritesPage(navController: NavController, favoritesViewModel: FavoritesViewModel) {
//    val favorites by favoritesViewModel.favorites.observeAsState(emptyList()) // Обратите внимание на состояние
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Favorites",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        if (favorites.isEmpty()) {
//            Text("No favorite products found.")
//        } else {
//            LazyColumn {
//                items(favorites) { product ->
//                    ProductItem(product, favoritesViewModel)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ProductItem(product: Product, favoritesViewModel: FavoritesViewModel) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(text = product.name)
//        IconButton(
//            onClick = {
//                favoritesViewModel.removeProductFromFavorites(product)
//            }
//        ) {
//            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Remove from Favorites")
//        }
//    }
//}
