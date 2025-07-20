package com.copadevelops.productapp.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.copadevelops.productapp.data.model.Product
import com.copadevelops.productapp.ui.navigation.Screen
import com.copadevelops.productapp.viewmodel.HomeUiState
import com.copadevelops.productapp.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = remember { HomeViewModel() }) {
    val state = viewModel.uiState.collectAsState().value

    when (state) {
        is HomeUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is HomeUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: ${state.message}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.fetchProducts() }) {
                        Text("Retry")
                    }
                }
            }
        }

        is HomeUiState.Success -> {
            ProductList(products = state.products, onItemClick = {
                navController.navigate(Screen.Detail.createRoute(it.id))
            })
        }
    }
}

@Composable
fun ProductList(products: List<Product>, onItemClick: (Product) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(products) { product ->
            ProductItem(product = product, onClick = { onItemClick(product) })
            Divider()
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(product.thumbnail),
            contentDescription = product.title,
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(product.title, fontWeight = FontWeight.Bold)
            Text(product.brand)
            Text("$${product.price}", color = MaterialTheme.colorScheme.primary)
        }
    }
}
