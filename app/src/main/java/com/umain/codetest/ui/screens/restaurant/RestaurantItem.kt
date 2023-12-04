package com.umain.codetest.ui.screens.restaurant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.umain.codetest.ui.theme.AppTheme
import com.umain.codetest.ui.theme.RedColor
import com.umain.codetest.ui.theme.YellowColor
import com.umain.codetest.R
import com.umain.domain.entities.Restaurant

@Composable
fun RestaurantItem(restaurant: Restaurant, onClick: (Restaurant) -> Unit) {
    Card(
        elevation = cardElevation(4.dp),
        shape = RoundedCornerShape(
            topEnd = 12.dp,
            topStart = 12.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick(restaurant)
            }

    ) {
        Column {
            AsyncImage(
                model = restaurant.imageUrl,
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = stringResource(R.string.cd_restaurant_icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Row {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = stringResource(R.string.cd_rating_star),
                        tint = YellowColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = restaurant.rating.toString(),
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.padding(start = 3.dp)
                    )
                }

            }

            Text(
                text = restaurant.populateFilterNames(),
                Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.titleSmall
            )
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.clock),
                    tint = RedColor,
                    contentDescription = stringResource(R.string.cd_rating_star),
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = restaurant.deliveryTimeMinutes.toString() + " mins",
                    modifier = Modifier.padding(start = 5.dp),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}

@Preview
@Composable
fun RestaurantItemPreview() {
    AppTheme {
        RestaurantItem(Restaurant(), onClick = {})
    }
}