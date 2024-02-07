package com.example.test_moviedb.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.test_moviedb.db.entities.ReviewEnt
import com.example.test_moviedb.ui.theme.Test_moviedbTheme

@Composable
fun ReviewItem(review: ReviewEnt, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (review.photo.isNotEmpty())
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(review.photo)
                            .crossfade(true)
                            .build(),
                        contentDescription = review.author,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape),
                    )
                else
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = review.author,
                        modifier = Modifier.size(20.dp)
                    )

                Text(
                    review.author,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Text(review.createdAt, style = MaterialTheme.typography.labelSmall)

            Text(review.content)
        }
    }
}

@Preview
@Composable
fun PrevReviewItem() {
    Test_moviedbTheme {
        ReviewItem(
            review = ReviewEnt(
                author = "Author Name",
                createdAt = "02-02-2014",
                content = "Content of reviews"
            )
        )
    }
}