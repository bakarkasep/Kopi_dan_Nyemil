package com.kopinyemil.app.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
internal fun remember2(): MutableState<String?> = rememberSaveable { mutableStateOf<String?>(null) }
