package com.example.nutritiveapp.ui.screens.product.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun <T> SelectionChipRow(
    items: List<T>,
    selectedIds: List<Long>,
    onToggle: (Long) -> Unit,
    getName: (T) -> String,
    getId: (T) -> Long
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(items) { item ->
            val itemId = getId(item)

            FilterChip(
                onClick = { onToggle(itemId) },
                label = { Text(getName(item)) },
                selected = selectedIds.contains(itemId)
            )
        }
    }
}