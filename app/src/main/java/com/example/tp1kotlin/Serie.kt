package com.example.tp1kotlin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.rememberAsyncImagePainter

@Composable
fun SerieScreen(navController: NavHostController, viewModel: SerieViewModel) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    val series by viewModel.series.collectAsState()
    val searchedSeries by viewModel.Searched_series.collectAsState()

    var searchText by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(true) }

    var selectedSerie by remember { mutableStateOf<Serie?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getSeries()
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
                    val displaySeries = if (searchedSeries.isNotEmpty()) searchedSeries else series
                    if (displaySeries.isNotEmpty()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(displaySeries.size) { index ->
                                SerieCard(
                                    serie = displaySeries[index],
                                    onClick = {
                                        selectedSerie = displaySeries[index]
                                        isDialogVisible = true
                                    }
                                )
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
                        val displaySeries =
                            if (searchedSeries.isNotEmpty()) searchedSeries else series

                        if (displaySeries.isNotEmpty()) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(8.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(displaySeries.size) { index ->
                                    SerieCard(serie = displaySeries[index], onClick = {})
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
    if (isDialogVisible && selectedSerie != null) {
        SerieDetailsDialog(
            serie = selectedSerie!!,
            serieViewModel = viewModel,
            onDismiss = { isDialogVisible = false }
        )
    }
}

@Composable
fun SerieCard(serie: Serie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(220.dp)
            .clickable { onClick() },
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
                painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${serie.poster_path}"),
                contentDescription = serie.name,
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
                Text(text = serie.name, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = serie.first_air_date, color = Color.White)
            }
        }
    }
}


@Composable
fun SerieDetailsDialog(serie: Serie, serieViewModel: SerieViewModel, onDismiss: () -> Unit) {
    val genreNames = serieViewModel.getGenreNames(serie.genre_ids)
    val cast by serieViewModel.serieCast.collectAsState()


    LaunchedEffect(serie.id) {
        serieViewModel.getSerieCast(serie.id)
    }

    androidx.compose.ui.window.Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = serie.name, style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${serie.backdrop_path}"),
                        contentDescription = "${serie.name} Poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Date de sortie: ${serie.first_air_date}")
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Genres: ${genreNames.joinToString(", ")}",
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Synopsis:")
                    Text(
                        text = serie.overview,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Le Casting:")
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(cast) { actor ->
                            CastCard(actor = actor)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    FloatingActionButton(
                        onClick = { onDismiss() },
                        containerColor = Color.Blue,
                        modifier = Modifier
                            .width(120.dp)
                            .height(33.dp)
                    ) {
                        Text(text = "Fermer", color = Color.White)
                    }
                }
            }
        }
    }
}
