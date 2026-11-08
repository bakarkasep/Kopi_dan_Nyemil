package com.kopinyemil.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kopinyemil.app.components.ProductCard
import com.kopinyemil.app.data.DummyProducts
import com.kopinyemil.app.util.toRupiah
import com.kopinyemil.app.viewmodel.AppViewModel

@Composable
fun HomeScreen(vm: AppViewModel, onCheckout: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column {
                    Text("Halo, ${vm.displayName}", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                    Text(
                        "Mau ngopi atau nyemil apa hari ini?",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f)
                    )
                    Text(
                        "Makanan dan Minuman",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
            }
            items(DummyProducts.items, key = { it.id }) { product ->
                ProductCard(
                    product = product,
                    quantity = vm.quantityOf(product),
                    onAdd = { vm.addToCart(product) },
                    onRemove = { vm.removeFromCart(product) }
                )
            }
        }

        if (vm.cartCount > 0) {
            Card(
                onClick = onCheckout,
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Row(
                    Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.ShoppingCart, null, tint = MaterialTheme.colorScheme.onPrimary)
                    Text(
                        "  ${vm.cartCount} item",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    Text(vm.cartTotal.toRupiah(), color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                    Text("  Lihat Pesanan", color = MaterialTheme.colorScheme.onPrimary, fontSize = 13.sp)
                }
            }
        }
    }
}
