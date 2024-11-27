package com.example.tp1kotlin.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.rememberAsyncImagePainter
import com.example.tp1kotlin.Service.ActorViewModel
import com.example.tp1kotlin.Data.Actor

@Composable
fun ActeurScreen(navController: NavHostController, viewModel: ActorViewModel) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    val actors by viewModel.actors.collectAsState()
    val searchedActors by viewModel.Searched_actors.collectAsState()

    var searchText by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.getActors()
        viewModel.observeSearchTextChanges()
    }

    fun updateSearchText(newText: String) {
        searchText = newText
        viewModel.searchText.value = newText
    }

    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            Scaffold(
                topBar = {
                    if (showSearchBar) {
                        SearchBar(text = searchText, onTextChange = { updateSearchText(it) })
                    }
                },
                bottomBar = { Navbar(navController, isVertical = false) }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    val displayActors = if (searchedActors.isNotEmpty()) searchedActors else actors
                    if (displayActors.isNotEmpty()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(displayActors.size) { index ->
                                ActorCard(actor = displayActors[index])
                            }
                        }
                    } else {
                        Text(
                            text = "Chargement des films...",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        else -> {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { showSearchBar = !showSearchBar },
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            ) { paddingValues ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Navbar(navController = navController, isVertical = true)

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        val displayActors =
                            if (searchedActors.isNotEmpty()) searchedActors else actors

                        if (displayActors.isNotEmpty()) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(8.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(displayActors.size) { index ->
                                    ActorCard(actor = displayActors[index])
                                }
                            }
                        } else {
                            Text(
                                text = "Chargement des films...",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ActorCard(actor: Actor) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(220.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Blue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${actor.profile_path}"),
                contentDescription = actor.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = actor.name, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}