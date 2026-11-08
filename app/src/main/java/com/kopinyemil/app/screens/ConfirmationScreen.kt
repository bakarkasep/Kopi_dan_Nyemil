package com.kopinyemil.app.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kopinyemil.app.components.CustomButton
import com.kopinyemil.app.components.OrderSummary
import com.kopinyemil.app.ui.theme.SuccessGreen
import com.kopinyemil.app.viewmodel.AppViewModel

@Composable
fun ConfirmationScreen(vm: AppViewModel, onDone: () -> Unit) {
    BackHandler(onBack = onDone)
    val order = vm.lastOrder

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            Box(
                Modifier.size(104.dp).background(SuccessGreen.copy(alpha = 0.18f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.CheckCircle, null, tint = SuccessGreen, modifier = Modifier.size(72.dp))
            }
            Spacer(Modifier.height(20.dp))
            Text("Pesanan Berhasil!", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            Text(
                "Halo ${order?.customerName ?: vm.displayName},",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "Terima kasih sudah memesan. Tunggu saja, sebentar lagi kurir kami akan segera mengantarkan pesananmu!",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
            )
            if (order != null) {
                Spacer(Modifier.height(20.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Ringkasan Pesanan", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(Modifier.height(10.dp))
                        OrderSummary(order.items, order.total)
                        Spacer(Modifier.height(14.dp))
                        Text("Dikirim ke", fontWeight = FontWeight.Bold)
                        Text(order.address)
                        if (order.addressDetail.isNotBlank()) {
                            Text(order.addressDetail, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f))
                        }
                    }
                }
            }
        }
        CustomButton("Oke", onDone, Modifier.padding(top = 12.dp))
    }
}
