package com.umain.codetest.ui.screens.restaurant

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.umain.domain.entities.FilterInfo

@Composable
fun FilterComponent(filters: List<FilterInfo>, onClick: (String) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 15.dp)
    ) {
        items(
            items = filters,
            key = { it.id }) { item ->
            FilterItem(
                filterInfo = item,
                onClick = onClick
            )
        }
    }
}