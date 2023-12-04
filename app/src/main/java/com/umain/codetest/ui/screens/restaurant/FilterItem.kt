package com.umain.codetest.ui.screens.restaurant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.umain.codetest.ui.theme.SelectedColor
import com.umain.codetest.R
import com.umain.codetest.ui.theme.LightTextColor
import com.umain.codetest.ui.theme.TextColor
import com.umain.domain.entities.FilterInfo

@Composable
fun FilterItem(filterInfo: FilterInfo, onClick: (String) -> Unit) {

    Card(
        shape = RoundedCornerShape(70.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (filterInfo.isSelected) SelectedColor else MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .height(48.dp)
            .clickable {
                onClick(filterInfo.id)
            },
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = filterInfo.imageUrl,
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = stringResource(R.string.cd_filter_icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Text(
                text = filterInfo.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = if (filterInfo.isSelected) LightTextColor else TextColor
                ),
                modifier = Modifier.padding(
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    start = 5.dp
                )
            )
        }
    }
    Spacer(modifier = Modifier.width(18.dp))
}
