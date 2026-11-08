package com.kopinyemil.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kopinyemil.app.components.CustomButton

@Composable
fun SignUpScreen(onRegister: () -> Unit, onLogin: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(28.dp)) {
        Spacer(Modifier.height(48.dp))
        Text("Sign Up", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(12.dp))
        Text(
            "Join as a member & experience more fun from various meals and drinks at Kopi dan Nyemil.",
            fontSize = 15.sp,
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
        )
        Spacer(Modifier.weight(1f))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            CustomButton("Register", onRegister)
            CustomButton("Login", onLogin, outlined = true)
        }
    }
}
