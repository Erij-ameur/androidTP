package com.example.tp1kotlin

import android.annotation.SuppressLint
import com.example.tp1kotlin.ui.theme.TP1KotlinTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TP1KotlinTheme {
                val navController = rememberNavController()
                Scaffold {
                    NavHost(navController = navController, startDestination = "profile") {
                        composable("profile") {
                            ProfileScreen(navController)
                        }
                        composable("films") {
                            FilmScreen(navController, viewModel = viewModel())
                        }
                        composable("series") {
                            SerieScreen(navController, viewModel = viewModel())
                        }
                        composable("acteurs") {
                            ActeurScreen(navController, viewModel = viewModel() )
                        }
                    }
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navbar(navController: NavHostController, isVertical: Boolean) {
    if (isVertical) {
        // Barre de navigation verticale
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(100.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconButton(
                onClick = { navController.navigate("films") },
                modifier = Modifier.fillMaxWidth().height(70.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.Home, contentDescription = "Films")
                    Text("Films")
                }
            }

            IconButton(
                onClick = { navController.navigate("series") },
                modifier = Modifier.fillMaxWidth().height(70.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = "Series")
                    Text("Series")
                }
            }

            IconButton(
                onClick = { navController.navigate("acteurs") },
                modifier = Modifier.fillMaxWidth().height(70.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Acteurs")
                    Text("Acteurs")
                }
            }

        }
    }
    else {
        // Barre de navigation horizontale
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Films") },
                label = { Text("Films") },
                selected = navController.currentDestination?.route == "films",
                onClick = { navController.navigate("films") }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.DateRange, contentDescription = "Series") },
                label = { Text("Series") },
                selected = navController.currentDestination?.route == "series",
                onClick = { navController.navigate("series") }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Person, contentDescription = "Acteurs") },
                label = { Text("Acteurs") },
                selected = navController.currentDestination?.route == "acteurs",
                onClick = { navController.navigate("acteurs") }
            )
        }
    }
}

@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "FavApp",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )

        TextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = { Text("Cherchez ...") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
            trailingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            }
        )
    }
}



