package com.example.nutritiveapp.ui.mapper

import androidx.compose.ui.graphics.Color
import com.example.nutritiveapp.R
import com.example.nutritiveapp.data.dto.ProductDetailsDto
import com.example.nutritiveapp.ui.model.NutrimentsUi
import com.example.nutritiveapp.ui.model.ProductUi
import com.example.nutritiveapp.ui.model.TagBadgeUi

fun mapTagsToBadges(rawTags: List<String>): List<TagBadgeUi> {
    return rawTags.mapNotNull { raw ->
        val slug = raw.trim()
            .lowercase()
            .replace(" ", "-")
            .replace("_", "-")

        when (slug) {
            "palm-oil-free"             -> TagBadgeUi("Palm oil free", Color(0xFF2E7D32), R.drawable.ic_palm_free)
            "palm-oil"                  -> TagBadgeUi("Contains palm oil", Color.Red, R.drawable.ic_palm)
            "palm-oil-content-unknown"  -> TagBadgeUi("Palm oil content unknown", Color.Gray, R.drawable.ic_palm)
            "may-contain-palm-oil"      -> TagBadgeUi("May contain palm oil", Color(0xFFFF9800), R.drawable.ic_palm)
            "vegan"                     -> TagBadgeUi("Vegan", Color(0xFF2E7D32), R.drawable.ic_leaf)
            "non-vegan"                 -> TagBadgeUi("Non-vegan", Color.Red, R.drawable.ic_leaf)
            "maybe-vegan"               -> TagBadgeUi("Maybe vegan", Color(0xFFFF9800), R.drawable.ic_leaf)
            "vegan-status-unknown"      -> TagBadgeUi("Vegan status unknown", Color.Gray, R.drawable.ic_leaf)
            "vegetarian"                -> TagBadgeUi("Vegetarian", Color(0xFF2E7D32), R.drawable.ic_milk)
            "non-vegetarian"            -> TagBadgeUi("Non-vegetarian", Color.Red, R.drawable.ic_milk)
            "maybe-vegetarian"          -> TagBadgeUi("Maybe vegetarian", Color(0xFFFF9800), R.drawable.ic_milk)
            "vegetarian-status-unknown" -> TagBadgeUi("Vegetarian status unknown", Color.Gray, R.drawable.ic_milk)
            else -> null
        }
    }
}

fun ProductDetailsDto.toUi(): ProductUi {
    val nUi = nutriments?.let {
        NutrimentsUi(
            energy   = it.energy_kj_100g?.let { v -> "$v / 100g" },
            sugars   = it.sugars?.let { v -> "$v g" },
            fat      = it.fat?.let { v -> "$v g" },
            proteins = it.proteins?.let { v -> "$v g" },
            salt     = it.salt?.let { v -> "$v g" }
        )
    }

    return ProductUi(
        barcode = this.barcode ?: "",
        title = name ?: "Unknown Product",
        imageUrl = imageUrl,
        categories = categories.filterNot { it.isNullOrBlank() || it.equals("Null", ignoreCase = true) },
        additives = additives.filterNot { it.isNullOrBlank() || it.equals("Null", ignoreCase = true) },
        nutriments = nUi,
        countries = countries.filterNot { it.isNullOrBlank() || it.equals("Null", ignoreCase = true) },
        allergenNames = allergens.filterNot { it.isNullOrBlank() || it.equals("Null", ignoreCase = true) },
        tagBadges = mapTagsToBadges(tags.filterNot { it.isNullOrBlank() || it.equals("Null", ignoreCase = true) })
    )
}