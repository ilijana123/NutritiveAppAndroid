package com.example.nutritiveapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutritiveapp.ui.screens.login.LoginScreen
import com.example.nutritiveapp.ui.screens.main.MainScreen
import com.example.nutritiveapp.ui.screens.login.SignUpScreen
import com.example.nutritiveapp.ui.theme.NutritiveAppTheme
import com.example.nutritiveapp.viewmodels.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutritiveAppTheme {
                val navController = rememberNavController()
                val sessionViewModel: SessionViewModel = viewModel()
                val sessionState by sessionViewModel.session.collectAsStateWithLifecycle()

                LaunchedEffect(sessionState.id) {
                    if (sessionState.id != null) {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = if (sessionState.id != null) "main" else "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            onCreateAccountClick = { navController.navigate("signup") },
                            sessionViewModel = sessionViewModel,
                            onLoginSuccess = { authResponse ->
                                sessionViewModel.login(authResponse)
                                navController.navigate("main") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("signup") {
                        SignUpScreen(
                            onSignUpSuccess = { navController.popBackStack() },
                            onBackToLogin = { navController.popBackStack() }
                        )
                    }
                    composable("main") {
                        val session by sessionViewModel.session.collectAsStateWithLifecycle()

                        MainScreen(
                            startDestination = Destination.HOME,
                            savedUserId = session.id ?: -1L,
                            username = session.username ?: session.email ?: "Guest",
                            fullName = "${session.firstName.orEmpty()} ${session.lastName.orEmpty()}",
                            homeViewModel = null,
                            onLogout = {
                                sessionViewModel.logout()
                                navController.navigate("login") {
                                    popUpTo("main") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
