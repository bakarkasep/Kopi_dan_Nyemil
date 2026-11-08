package com.kopinyemil.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.kopinyemil.app.util.toast
import com.kopinyemil.app.viewmodel.AppViewModel

@Composable
fun LoginScreen(vm: AppViewModel, onSuccess: () -> Unit) {
    val context = LocalContext.current
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var usernameError by remember2()
    var passwordError by remember2()
    var formError by remember2()

    Column(
        Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(28.dp)
    ) {
        Spacer(Modifier.height(48.dp))
        Text("Login User", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(8.dp))
        Text(
            "Akun demo: kopi / nyemil123",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(24.dp))
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CustomTextField(username, { username = it; usernameError = null; formError = null }, "Username", error = usernameError)
            CustomTextField(password, { password = it; passwordError = null; formError = null }, "Password", error = passwordError, isPassword = true)
            if (formError != null) {
                Text(formError!!, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }
        }
        Spacer(Modifier.height(32.dp))
        CustomButton("Login", {
            usernameError = if (username.isBlank()) "Username tidak boleh kosong" else null
            passwordError = if (password.isEmpty()) "Password tidak boleh kosong" else null
            if (usernameError == null && passwordError == null) {
                val error = vm.login(username, password)
                if (error == null) {
                    context.toast("Login berhasil. Selamat datang, ${vm.displayName}!")
                    onSuccess()
                } else {
                    formError = error
                    context.toast(error)
                }
            }
        })
    }
}
