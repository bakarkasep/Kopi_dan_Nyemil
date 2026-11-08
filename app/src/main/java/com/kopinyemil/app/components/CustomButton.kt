package com.kopinyemil.app.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    outlined: Boolean = false
) {
    val shape = RoundedCornerShape(16.dp)
    val base = modifier.fillMaxWidth().height(54.dp)
    if (outlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = base,
            shape = shape,
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
        ) { Text(text, fontWeight = FontWeight.SemiBold) }
    } else {
        Button(
            onClick = onClick,
            modifier = base,
            shape = shape,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) { Text(text, fontWeight = FontWeight.SemiBold) }
    }
}
