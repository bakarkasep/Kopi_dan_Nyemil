package com.kopinyemil.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kopinyemil.app.navigation.AppNavigation
import com.kopinyemil.app.ui.theme.KopiDanNyemilTheme
import com.kopinyemil.app.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KopiDanNyemilTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val vm: AppViewModel = viewModel()
                    AppNavigation(vm)
                }
            }
        }
    }
}
