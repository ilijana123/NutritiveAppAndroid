package com.example.nutritiveapp.ui.utils


import com.example.nutritiveapp.R

fun allergenIconFor(name: String): Int {
    return when (name.lowercase()) {
        "nuts" -> R.drawable.ic_nuts
        "milk", "lait", "skimmed" -> R.drawable.ic_milk
        "soybeans" -> R.drawable.ic_soybeans
        "gluten", "wheat gluten" -> R.drawable.ic_gluten
        "eggs" -> R.drawable.ic_eggs
        "peanuts" -> R.drawable.ic_peanuts
        "celery" -> R.drawable.ic_celery
        "mustard" -> R.drawable.ic_mustard
        "sesame-seeds" -> R.drawable.ic_sesame_seeds
        "gelatin" -> R.drawable.ic_gelatin
        "banana" -> R.drawable.ic_banana
        "apple" -> R.drawable.ic_apple
        "fish" -> R.drawable.ic_fish
        "crustaceans" -> R.drawable.ic_crustaceans
        "lupin" -> R.drawable.ic_lupin
        "shellfish" -> R.drawable.ic_shellfish
        "sulphur-dioxide-and-sulphites" -> R.drawable.ic_sulphites
        "pork" -> R.drawable.ic_pork
        "beef" -> R.drawable.ic_beef
        "orange" -> R.drawable.ic_orange
        "chicken" -> R.drawable.ic_chicken
        "molluscs" -> R.drawable.ic_molluscs
        "peach" -> R.drawable.ic_peach
        else -> R.drawable.ic_allergen_default
    }
}