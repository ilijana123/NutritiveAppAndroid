package com.example.nutritiveapp.ui.screens.profile

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiveapp.viewmodels.NotificationViewModel
import com.example.nutritiveapp.viewmodels.SessionViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen(
    sessionViewModel: SessionViewModel,
    onBack: () -> Unit,
    onRequestNotificationPermission: () -> Unit = {},
    onEditProfile: () -> Unit,
    onHelpSupport: () -> Unit
) {
    val session = sessionViewModel.session.value
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val fabScale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF81C784),
                        Color(0xFFE8F5E9),
                        Color.White
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(600, easing = EaseOutCubic)
                ) + fadeIn(animationSpec = tween(600))
            ) {
                ProfileHeaderCard(
                    firstName = session.firstName,
                    lastName = session.lastName,
                    email = session.email
                )
            }

            Spacer(Modifier.height(32.dp))

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(600, delayMillis = 200, easing = EaseOutCubic)
                ) + fadeIn(animationSpec = tween(600, delayMillis = 200))
            ) {
                Column {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1B5E20),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    val notificationViewModel: NotificationViewModel = viewModel()
                    val enabled by notificationViewModel.notificationsEnabled.collectAsState()
                    val requestPermission = requestNotificationPermission(
                        onPermissionGranted = { notificationViewModel.toggleNotifications(true) },
                        onPermissionDenied = { notificationViewModel.toggleNotifications(false) }
                    )

                    NotificationSettingsCard(
                        notificationsEnabled = enabled,
                        onToggleNotifications = { checked ->
                            if (checked) {
                                if (notificationViewModel.requiresPermission()) {
                                    requestPermission()
                                } else {
                                    notificationViewModel.toggleNotifications(true)
                                }
                            } else {
                                notificationViewModel.disableNotifications()
                            }
                        }
                    )

                    Spacer(Modifier.height(16.dp))

                    SettingsCard(
                        icon = Icons.AutoMirrored.Filled.Help,
                        title = "Help & Support",
                        subtitle = "Get help or contact our support team",
                        onClick = onHelpSupport
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onEditProfile,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .scale(fabScale)
                .shadow(8.dp, CircleShape),
            containerColor = Color(0xFF2E7D32),
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Profile",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}