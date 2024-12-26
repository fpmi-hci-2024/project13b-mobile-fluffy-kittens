package com.fluffykittens.fluffy_shop.ui.theme



import android.content.Context
import android.content.Context.MODE_PRIVATE
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fluffykittens.fluffy_shop.api.ApiService
import com.fluffykittens.fluffy_shop.api.ProductDetailViewModel
import com.fluffykittens.fluffy_shop.viewmodel.AuthViewModel
import androidx.compose.ui.platform.LocalContext
@Composable
fun ProductDetailPage(
    productId: String,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val viewModel: ProductDetailViewModel = viewModel()
    val product by viewModel.product.collectAsState()
    val isUserLoggedIn by authViewModel.isUserLoggedIn

    // Получение информации о пользователе из SharedPreferences
    val context = LocalContext.current
    val userInfo = getUserInfo(context)
    val customerId = userInfo["user_id"]

    LaunchedEffect(productId) {
        viewModel.fetchProductDetails(productId)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (product == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            product?.let {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Back"
                            )
                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = it.name,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        if (isUserLoggedIn) {
                            IconButton(
                                onClick = {
                                    customerId?.let { id ->
                                        viewModel.onAddToFavorites(id, productId)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite"
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it.description, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Price: $${it.price}", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Stock: ${it.stock}", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    if (isUserLoggedIn) {
                        Button(
                            onClick = { /* Add to cart action */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(1.dp, Color.Black),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Text("Add to Cart")
                        }
                    }
                }
            }
        }
    }
}

fun getUserInfo(context: Context): Map<String, String?> {
    val sharedPref = context.getSharedPreferences("user_data", MODE_PRIVATE)
    val userId = sharedPref.getString("user_id", null)
    val name = sharedPref.getString("name", null)
    val email = sharedPref.getString("email", null)

    // Возвращаем данные в виде Map
    return mapOf(
        "user_id" to userId,
        "name" to name,
        "email" to email
    )
}

