package com.food.delivery.ui.screens.restaurantDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.food.delivery.R
import com.food.delivery.ui.theme.AppTheme
import com.food.delivery.ui.theme.NegativeColor
import com.food.delivery.ui.theme.PositiveColor
import com.food.delivery.ui.theme.SubtitlesColor
import com.food.delivery.utils.UiState.*
import com.food.domain.entities.Restaurant

@Composable
fun RestaurantDetailScreen(
    restaurant: Restaurant,
    viewModel: RestaurantDetailViewModel,
    onBackPress: () -> Unit
) {
    val viewData = viewModel.viewData.collectAsState()

    LaunchedEffect(key1 = restaurant) {
        viewModel.getRestaurantOpenStatus(restaurant)
    }

    RestaurantDetailComponent(
        restaurantDetail = restaurant,
        viewData = viewData.value,
        onBackPress
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailComponent(
    restaurantDetail: Restaurant,
    viewData: RestaurantDetailViewModel.ViewData,
    onBackPress: () -> Unit
) {

    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            AsyncImage(
                model = restaurantDetail.imageUrl,
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = stringResource(R.string.cd_restaurant_icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            IconButton(
                modifier = Modifier.padding(top = 20.dp).testTag("backButton"),
                onClick = {
                    onBackPress()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "NavigateBack",
                    modifier = Modifier.size(50.dp)

                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 200.dp),
                elevation = cardElevation(4.dp)
            ) {

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = restaurantDetail.name,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(
                        text = restaurantDetail.populateFilterNames(),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = SubtitlesColor
                        )
                    )
                    when (viewData.state) {
                        LOADING -> {
                            CircularProgressIndicator(
                                modifier = Modifier.testTag("openStatusLoader")
                            )
                        }

                        LOADED -> {
                            Text(
                                modifier = Modifier.testTag("restaurantOpenStatus"),
                                text = stringResource(if (viewData.openStatus.isCurrentlyOpen) R.string.restaurant_status_open else R.string.restaurant_status_close),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = if (viewData.openStatus.isCurrentlyOpen) PositiveColor else NegativeColor
                                )
                            )
                        }

                        ERROR -> {
                            Text(
                                modifier =  Modifier.testTag("restaurantFailStatus"),
                                text = stringResource(R.string.failed_to_load_status),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = NegativeColor
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun RestaurantDetail() {
    AppTheme {
        RestaurantDetailComponent(Restaurant(), RestaurantDetailViewModel.ViewData(), onBackPress = {})
    }
}