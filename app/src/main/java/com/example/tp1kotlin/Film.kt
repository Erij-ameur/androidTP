package com.example.tp1kotlin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

@Composable
fun FilmScreen(navController: NavHostController, viewModel: MovieViewModel) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val movies by viewModel.movies.collectAsState()
    val searchedMovies by viewModel.Searched_movies.collectAsState()

    var searchText by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(true) }

    var selectedMovie by remember { mutableStateOf<Movie?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getMovies()
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
                    val displayMovies = if (searchedMovies.isNotEmpty()) searchedMovies else movies
                    if (displayMovies.isNotEmpty()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(displayMovies.size) { index ->
                                MovieCard(
                                    movie = displayMovies[index],
                                    onClick = {
                                        selectedMovie = displayMovies[index]
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
                    // Vertical navigation bar on the left
                    Navbar(navController = navController, isVertical = true)

                    // Movie content
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        val displayMovies = if (searchedMovies.isNotEmpty()) searchedMovies else movies

                        if (displayMovies.isNotEmpty()) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(8.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(displayMovies.size) { index ->
                                    MovieCard(movie = displayMovies[index], onClick = {
                                        selectedMovie = displayMovies[index]
                                        isDialogVisible = true
                                    })
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

    if (isDialogVisible && selectedMovie != null) {
        MovieDetailsDialog(
            movie = selectedMovie!!,
            movieViewModel = viewModel,
            onDismiss = { isDialogVisible = false }
        )
    }
}


@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(280.dp)
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
                painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.poster_path}"),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = movie.title, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = movie.release_date, color = Color.White)
            }
        }
    }
}

@Composable
fun MovieDetailsDialog(movie: Movie, movieViewModel: MovieViewModel, onDismiss: () -> Unit) {
    val genreNames = movieViewModel.getGenreNames(movie.genre_ids)
    val cast by movieViewModel.movieCast.collectAsState()

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

    LaunchedEffect(movie.id) {
        movieViewModel.getMovieCast(movie.id)
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
                    Text(text = movie.title, style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.backdrop_path}"),
                        contentDescription = "${movie.title} Poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Date de sortie: ${movie.release_date}")
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Genres: ${genreNames.joinToString(", ")}",
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Synopsis:")
                    Text(
                        text = movie.overview,
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


@Composable
fun CastCard(actor: Cast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w200${actor.profile_path}"),
            contentDescription = actor.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = actor.name,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}