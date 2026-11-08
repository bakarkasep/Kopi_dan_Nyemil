package com.kopinyemil.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
fun ProfileScreen(vm: AppViewModel, onLogout: () -> Unit) {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf(vm.displayName) }
    var nameError by remember2()
    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(24.dp)) {
        Text("Halo, ${vm.displayName}", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(20.dp))

        Text("Profile Saya", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(56.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Filled.Person, null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(32.dp)) }
            Column(Modifier.padding(start = 14.dp)) {
                Text(vm.displayName, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text("@${vm.user?.username.orEmpty()}", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("Profile Setting", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        CustomTextField(name, { name = it; nameError = null }, "Nama", error = nameError)
        Spacer(Modifier.height(12.dp))
        CustomButton("Save", {
            if (name.isBlank()) {
                nameError = "Nama tidak boleh kosong"
            } else {
                vm.updateName(name)
                context.toast("Profil berhasil diperbarui")
            }
        })

        Spacer(Modifier.height(28.dp))
        CustomButton("Logout", { showLogoutDialog = true }, outlined = true)
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout") },
            text = { Text("Apakah kamu yakin ingin keluar dari akun? Isi keranjang akan dikosongkan.") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    vm.logout()
                    context.toast("Kamu telah logout")
                    onLogout()
                }) { Text("Ya, Logout") }
            },
            dismissButton = { TextButton(onClick = { showLogoutDialog = false }) { Text("Batal") } }
        )
    }
}
