package com.example.nutritiveapp.ui.screens.product.create

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NutritionInputRow(
    label: String,
    value: String,
    field: String,
    onValueChange: (String, String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(field, it) },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}