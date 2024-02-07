package com.example.test_moviedb.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test_moviedb.db.entities.GenreEnt
import com.example.test_moviedb.ui.component.GenreItem
import com.example.test_moviedb.ui.theme.Test_moviedbTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreScreen(genres: List<GenreEnt>, onClickItem: (GenreEnt) -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Select Genre") },
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(items = genres, key = { it.id }) { genre ->
                GenreItem(genre = genre, onClick = { onClickItem(genre) })
            }
        }
    }
}

@Preview
@Composable
fun PrevGenreScreen() {
    Test_moviedbTheme {
        GenreScreen(
            genres = List(100) { GenreEnt(it + 1, "Genre Preview ${it + 1}") },
            onClickItem = {}
        )
    }
}