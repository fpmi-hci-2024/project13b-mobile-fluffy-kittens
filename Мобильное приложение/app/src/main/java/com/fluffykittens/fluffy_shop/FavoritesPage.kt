package com.fluffykittens.fluffy_shop.ui.theme



import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedButton
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


@Composable
fun FavoritesPage(viewModel: FavoritesViewModel, customerId: String) {
    val favoriteProducts by viewModel.favoriteProducts.collectAsState()

    LaunchedEffect(customerId) {
        // Загружаем избранные продукты при изменении customerId
        viewModel.fetchFavoriteProducts(customerId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Your Favorite Products",
            modifier = Modifier.padding(16.dp)
        )

        if (favoriteProducts.isEmpty()) {
            Text(
                text = "No favorite products available.",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn {
                items(favoriteProducts) { product ->
                    // Отображаем данные каждого продукта прямо здесь
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = product.name,
                            modifier = Modifier.weight(1f),
                        )
                        Text(
                            text = "\$${product.price}"
                        )
                    }
                }
            }
        }
    }
}
