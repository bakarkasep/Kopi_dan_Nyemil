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
fun RegisterScreen(vm: AppViewModel, onSuccess: () -> Unit) {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var nameError by remember2()
    var usernameError by remember2()
    var passwordError by remember2()

    Column(
        Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(28.dp)
    ) {
        Spacer(Modifier.height(48.dp))
        Text("Register", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(24.dp))
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CustomTextField(name, { name = it; nameError = null }, "Nama lengkap", error = nameError)
            CustomTextField(username, { username = it.replace(" ", ""); usernameError = null }, "Username", error = usernameError)
            CustomTextField(password, { password = it; passwordError = null }, "Password", error = passwordError, isPassword = true)
        }
        Spacer(Modifier.height(32.dp))
        CustomButton("Register", {
            nameError = if (name.isBlank()) "Nama lengkap tidak boleh kosong" else null
            usernameError = when {
                username.isBlank() -> "Username tidak boleh kosong"
                username.length < 3 -> "Username minimal 3 karakter"
                else -> null
            }
            passwordError = when {
                password.isEmpty() -> "Password tidak boleh kosong"
                password.length < 6 -> "Password minimal 6 karakter"
                else -> null
            }
            if (nameError == null && usernameError == null && passwordError == null) {
                val error = vm.register(name, username, password)
                if (error == null) {
                    context.toast("Registrasi berhasil. Selamat datang, ${vm.displayName}!")
                    onSuccess()
                } else {
                    usernameError = error
                    context.toast(error)
                }
            }
        })
    }
}
