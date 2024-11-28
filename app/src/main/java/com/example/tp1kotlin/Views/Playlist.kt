package com.example.tp1kotlin.Views

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.tp1kotlin.Service.MovieViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlaylistScreen(navController: NavHostController, viewModel: MovieViewModel) {
    Scaffold(
        bottomBar = { Navbar(navController, isVertical = false) }
    ) { paddingValues ->
        Text(text = "playlist screen ")
    }
}

