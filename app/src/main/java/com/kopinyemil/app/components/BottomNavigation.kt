package com.kopinyemil.app.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.kopinyemil.app.navigation.Routes

private data class NavTab(val route: String, val label: String, val icon: ImageVector)

private val tabs = listOf(
    NavTab(Routes.HOME, "Home", Icons.Filled.Home),
    NavTab(Routes.ORDER, "Order", Icons.Filled.ShoppingCart),
    NavTab(Routes.PROFILE, "Profile", Icons.Filled.Person)
)

@Composable
fun AppBottomNavigation(currentRoute: String?, cartCount: Int, onNavigate: (String) -> Unit) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route,
                onClick = { onNavigate(tab.route) },
                label = { Text(tab.label) },
                icon = {
                    if (tab.route == Routes.ORDER && cartCount > 0) {
                        BadgedBox(badge = { Badge { Text(cartCount.toString()) } }) {
                            Icon(tab.icon, contentDescription = tab.label)
                        }
                    } else {
                        Icon(tab.icon, contentDescription = tab.label)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
