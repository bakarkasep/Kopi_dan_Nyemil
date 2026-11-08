package com.kopinyemil.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kopinyemil.app.model.CartItem
import com.kopinyemil.app.model.Product
import com.kopinyemil.app.util.toRupiah

/** Ringkasan pesanan. Jika onAdd/onRemove diisi, jumlah bisa diubah. */
@Composable
fun OrderSummary(
    items: List<CartItem>,
    total: Int,
    modifier: Modifier = Modifier,
    onAdd: ((Product) -> Unit)? = null,
    onRemove: ((Product) -> Unit)? = null
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items.forEach { item ->
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(item.product.name, fontWeight = FontWeight.SemiBold)
                    Text(
                        if (onAdd == null) "${item.quantity} x ${item.product.price.toRupiah()}" else item.product.price.toRupiah(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                if (onAdd != null && onRemove != null) {
                    QuantityStepper(item.quantity, { onAdd(item.product) }, { onRemove(item.product) })
                    Text(
                        item.subtotal.toRupiah(),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                } else {
                    Text(item.subtotal.toRupiah(), fontWeight = FontWeight.SemiBold)
                }
            }
        }
        HorizontalDivider()
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total", fontWeight = FontWeight.Bold)
            Text(total.toRupiah(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
        }
    }
}
