package com.kopinyemil.app.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.kopinyemil.app.model.CartItem
import com.kopinyemil.app.model.Order
import com.kopinyemil.app.model.Product
import com.kopinyemil.app.model.User
import com.kopinyemil.app.util.PreferencesManager

class AppViewModel(app: Application) : AndroidViewModel(app) {

    private val prefs = PreferencesManager(app)

    // Akun dummy
    private val demoUser = User("kopi", "Pelanggan Kopi")
    private val demoPassword = "nyemil123"

    var user by mutableStateOf(prefs.getSession())
        private set
    val isLoggedIn: Boolean get() = user != null
    val displayName: String get() = user?.name ?: "Teman"

    val cart = mutableStateListOf<CartItem>()
    val cartTotal: Int get() = cart.sumOf { it.subtotal }
    val cartCount: Int get() = cart.sumOf { it.quantity }

    var lastOrder by mutableStateOf<Order?>(null)
        private set

    // ---------- Auth ----------
    /** Mengembalikan pesan error, atau null jika berhasil. */
    fun login(username: String, password: String): String? {
        val u = username.trim()
        val found = if (u.equals(demoUser.username, ignoreCase = true) && password == demoPassword) {
            demoUser
        } else {
            prefs.authenticate(u, password)
        }
        if (found == null) {
            val known = u.equals(demoUser.username, ignoreCase = true) || prefs.accountExists(u)
            return if (known) "Password salah. Silakan coba lagi."
            else "Username \"$u\" belum terdaftar. Silakan Register terlebih dahulu."
        }
        prefs.saveSession(found)
        user = found
        return null
    }

    fun register(name: String, username: String, password: String): String? {
        val u = username.trim()
        if (u.equals(demoUser.username, ignoreCase = true) || prefs.accountExists(u)) {
            return "Username \"$u\" sudah digunakan. Pilih username lain."
        }
        prefs.registerAccount(u, password, name.trim())
        val newUser = User(u, name.trim())
        prefs.saveSession(newUser)
        user = newUser
        return null
    }

    fun updateName(newName: String) {
        val current = user ?: return
        val trimmed = newName.trim()
        prefs.updateName(current.username, trimmed)
        user = current.copy(name = trimmed)
    }

    fun logout() {
        prefs.clearSession()
        user = null
        cart.clear()
        lastOrder = null
    }

    // ---------- Keranjang ----------
    fun quantityOf(product: Product): Int =
        cart.firstOrNull { it.product.id == product.id }?.quantity ?: 0

    fun addToCart(product: Product) {
        val i = cart.indexOfFirst { it.product.id == product.id }
        if (i >= 0) cart[i] = cart[i].copy(quantity = cart[i].quantity + 1) else cart.add(CartItem(product, 1))
    }

    fun removeFromCart(product: Product) {
        val i = cart.indexOfFirst { it.product.id == product.id }
        if (i < 0) return
        if (cart[i].quantity <= 1) cart.removeAt(i) else cart[i] = cart[i].copy(quantity = cart[i].quantity - 1)
    }

    fun checkout(name: String, address: String, detail: String) {
        lastOrder = Order(name.trim(), address.trim(), detail.trim(), cart.toList())
        cart.clear()
    }
}
