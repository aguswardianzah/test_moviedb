package com.example.test_moviedb.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.test_moviedb.db.entities.ReviewEnt
import com.example.test_moviedb.ui.component.BackButton
import com.example.test_moviedb.ui.component.ReviewItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navController: NavController,
    reviews: LazyPagingItems<ReviewEnt>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    navController.currentBackStackEntry?.let {
                        BackButton(navController::popBackStack)
                    }
                },
                title = { Text("Reviews") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(
                count = reviews.itemCount,
                key = reviews.itemKey { it.id },
                contentType = reviews.itemContentType { "reviewItem" }
            ) {
                reviews[it]?.let {
                    ReviewItem(review = it)
                }
            }

            // initial/refresh loading
            when (val refresh = reviews.loadState.refresh) {
                is LoadState.Error -> {
                    item {
                        Surface(
                            color = MaterialTheme.colorScheme.error,
                            shadowElevation = 2.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                "Failed to load - ${refresh.error}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                is LoadState.Loading -> {
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                "Refresh Loading",
                                modifier = Modifier.padding(8.dp),
                            )

                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                is LoadState.NotLoading -> {
                    // show message when movies empty
                    if (reviews.itemCount == 0) {
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    "Not Found",
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }

            // next page loading indicator
            if (reviews.loadState.append == LoadState.Loading) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            "Loading Next",
                            modifier = Modifier.padding(8.dp),
                        )

                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}