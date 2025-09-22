package com.example.nutritiveapp.ui.screens.product

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nutritiveapp.App.Companion.context
import com.example.nutritiveapp.R
import com.example.nutritiveapp.data.model.PieSegment
import com.example.nutritiveapp.data.remote.RetrofitInstance.productApi
import com.example.nutritiveapp.ui.model.ProductUi
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductCard(
    product: ProductUi,
    userAllergenNames: Set<String>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            ProductImage(product.imageUrl, product.title)

            if (product.categories.isNotEmpty()) {
                SectionTitle("Categories", Color(0xFF4CAF50))
                ChipRow(product.categories, Color(0xFFE8F5E9))
            }

            if (product.additives.isNotEmpty()) {
                SectionTitle("Additives", Color(0xFFF57C00))
                ChipRow(product.additives, Color(0xFFFFF3E0))
            }

            if (product.countries.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_globe),
                        contentDescription = "Countries",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Available in: ${product.countries.joinToString(", ")}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (product.allergenNames.isNotEmpty()) {
                SectionTitle("Allergens", Color(0xFFD32F2F))

                val matched = product.allergenNames.toSet().intersect(userAllergenNames)

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    product.allergenNames.forEach { allergen ->
                        AssistChip(
                            onClick = {},
                            label = { Text(allergen) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (matched.contains(allergen)) Color(0xFFFFCDD2) else Color(0xFFF5F5F5)
                            )
                        )
                    }
                }

                if (matched.isNotEmpty()) {
                    Surface(
                        color = Color(0xFFFFEBEE),
                        shape = RoundedCornerShape(12.dp),
                        tonalElevation = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_warning),
                                contentDescription = "Warning",
                                tint = Color(0xFFD32F2F),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "⚠ Contains allergens: ${matched.joinToString(", ")}",
                                color = Color(0xFFD32F2F),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            if (product.tagBadges.isNotEmpty()) {
                SectionTitle("Tags", Color(0xFF0288D1))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    product.tagBadges.forEach { tb ->
                        TagRow(tb.text, tb.color, tb.iconRes)
                    }
                }
            }
            val scope = rememberCoroutineScope()

            Button(
                onClick = {
                    scope.launch {
                        try {
                            val response = productApi.saveProduct(product.barcode)
                            if (response.isSuccessful) {
                                Toast.makeText(context, "✅ Product saved!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "❌ Failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "❌ Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("💾 Save for Later", fontWeight = FontWeight.SemiBold)
            }

        }
    }
}

@Composable
private fun SectionTitle(title: String, color: Color) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        color = color
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChipRow(items: List<String>, containerColor: Color) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items.forEach { item ->
            AssistChip(
                onClick = {},
                label = { Text(item) },
                colors = AssistChipDefaults.assistChipColors(containerColor = containerColor)
            )
        }
    }
}

fun DrawScope.drawPieChart(segments: List<PieSegment>, total: Float) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 2 * 0.8f
    var startAngle = -90f

    segments.forEach { segment ->
        val sweepAngle = (segment.value / total) * 360f
        drawArc(
            color = segment.color,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = true,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2)
        )
        startAngle += sweepAngle
    }

    drawCircle(
        color = Color.White,
        radius = radius * 0.4f,
        center = center
    )
}

fun String.extractNumber(): Float {
    return this.filter { it.isDigit() || it == '.' }
        .toFloatOrNull() ?: 0f
}