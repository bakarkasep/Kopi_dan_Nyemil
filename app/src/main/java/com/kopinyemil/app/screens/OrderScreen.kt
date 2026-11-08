package com.kopinyemil.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kopinyemil.app.components.CustomButton
import com.kopinyemil.app.components.CustomTextField
import com.kopinyemil.app.components.OrderSummary
import com.kopinyemil.app.util.toast
import com.kopinyemil.app.viewmodel.AppViewModel

@Composable
fun OrderScreen(vm: AppViewModel, onOrderPlaced: () -> Unit) {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf(vm.displayName) }
    var address by rememberSaveable { mutableStateOf("") }
    var detail by rememberSaveable { mutableStateOf("") }
    var nameError by remember2()
    var addressError by remember2()

    Column(Modifier.fillMaxSize().imePadding()) {
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            Text("Alamat", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                CustomTextField(name, { name = it; nameError = null }, "Nama Lengkap", error = nameError)
                CustomTextField(address, { address = it; addressError = null }, "Alamat Lengkap", error = addressError, singleLine = false, minLines = 3)
                CustomTextField(detail, { detail = it }, "Patokan atau detail lengkap", singleLine = false, minLines = 2)
            }
            Spacer(Modifier.height(16.dp))
            Text("Ringkasan Pesanan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                if (vm.cart.isEmpty()) {
                    Text(
                        "Keranjang masih kosong. Pilih menu di halaman Home terlebih dahulu.",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                } else {
                    OrderSummary(
                        items = vm.cart.toList(),
                        total = vm.cartTotal,
                        modifier = Modifier.padding(16.dp),
                        onAdd = { vm.addToCart(it) },
                        onRemove = { vm.removeFromCart(it) }
                    )
                }
            }
        }
        CustomButton(
            "Order dan Kirim",
            {
                nameError = if (name.isBlank()) "Nama tidak boleh kosong" else null
                addressError = if (address.isBlank()) "Alamat tidak boleh kosong" else null
                when {
                    vm.cart.isEmpty() -> context.toast("Pesanan masih kosong. Tambahkan menu terlebih dahulu.")
                    nameError == null && addressError == null -> {
                        vm.checkout(name, address, detail)
                        onOrderPlaced()
                    }
                    else -> context.toast("Lengkapi data pengiriman terlebih dahulu.")
                }
            },
            Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        )
    }
}
