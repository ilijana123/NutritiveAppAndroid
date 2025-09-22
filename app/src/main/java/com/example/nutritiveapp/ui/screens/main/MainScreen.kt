package com.example.nutritiveapp.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nutritiveapp.Destination
import com.example.nutritiveapp.viewmodels.HomeViewModel
import com.example.nutritiveapp.data.remote.RetrofitInstance
import com.example.nutritiveapp.ui.screens.AllergensScreen
import com.example.nutritiveapp.ui.screens.ContactSupportScreen
import com.example.nutritiveapp.ui.screens.home.HomeScreen
import com.example.nutritiveapp.ui.screens.product.ProductNotFoundScreen
import com.example.nutritiveapp.ui.screens.product.ProductDetailsScreen
import com.example.nutritiveapp.ui.screens.product.create.ProductCreationScreen
import com.example.nutritiveapp.ui.screens.products.ProductsScreen
import com.example.nutritiveapp.ui.screens.profile.EditProfileScreen
import com.example.nutritiveapp.ui.screens.profile.ProfileScreen
import com.example.nutritiveapp.viewmodels.SessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    startDestination: Destination,
    savedUserId: Long,
    username: String = "Guest",
    fullName: String = "",
    homeViewModel: HomeViewModel? = null,
    onLogout: () -> Unit = {}
) {
    val innerNavController = rememberNavController()
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    var showMenu by remember { mutableStateOf(false) }
    val sessionViewModel: SessionViewModel = viewModel()
    val currentBackStackEntry = innerNavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    // Add product creation routes to back arrow routes
    val backArrowRoutes = listOf(
        "profile",
        "editProfile",
        "productDetails/{barcode}",
        "productNotFound/{barcode}",
        "productCreation",
        "addProduct/{barcode}"
    )

    Scaffold(
        topBar = {
            when {
                backArrowRoutes.any { route -> currentRoute?.startsWith(route.substringBefore("/{")) == true } -> {
                    TopAppBar(
                        title = {
                            Text(
                                when {
                                    currentRoute?.startsWith("productDetails") == true -> "Product Details"
                                    currentRoute?.startsWith("productNotFound") == true -> "Product Not Found"
                                    currentRoute?.startsWith("editProfile") == true -> "Edit Profile"
                                    currentRoute?.startsWith("productCreation") == true -> "Add New Product"
                                    currentRoute?.startsWith("addProduct") == true -> "Add Product"
                                    else -> "Profile"
                                }
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { innerNavController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    )
                }
                else -> {
                    TopAppBar(
                        title = { Text("NutritiveApp") },
                        actions = {
                            IconButton(onClick = { showMenu = !showMenu }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Profile") },
                                    onClick = {
                                        showMenu = false
                                        innerNavController.navigate("profile")
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Logout") },
                                    onClick = {
                                        showMenu = false
                                        onLogout()
                                        sessionViewModel.logout()
                                    }
                                )
                            }
                        }
                    )
                }
            }
        },
        bottomBar = {
            if (!backArrowRoutes.any { route -> currentRoute?.startsWith(route.substringBefore("/{")) == true }) {
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                    Destination.entries.forEachIndexed { index, destination ->
                        NavigationBarItem(
                            selected = selectedDestination == index,
                            onClick = {
                                innerNavController.navigate(destination.route) {
                                    popUpTo(innerNavController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                selectedDestination = index
                            },
                            icon = { Icon(destination.icon, contentDescription = destination.contentDescription) },
                            label = { Text(destination.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = innerNavController,
            startDestination = startDestination.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            Destination.entries.forEach { destination ->
                composable(destination.route) {
                    when (destination) {
                        Destination.PRODUCTS -> ProductsScreen(
                            onProductClick = { barcode ->
                                innerNavController.navigate("productDetails/$barcode")
                            }
                        )
                        Destination.HOME -> {
                            val viewModel = homeViewModel ?: viewModel<HomeViewModel> {
                                HomeViewModel(
                                    RetrofitInstance.productApi,
                                    RetrofitInstance.allergenApi,
                                )
                            }
                            HomeScreen(
                                viewModel = viewModel,
                                onNavigateToDetails = { barcode ->
                                    innerNavController.navigate("productDetails/$barcode")
                                },
                                onNavigateToNotFound = { barcode ->
                                    innerNavController.navigate("productNotFound/$barcode")
                                },
                                username = username,
                                fullName = fullName,
                                // THIS IS THE KEY CHANGE - Connect the button to navigation
                                onAddProductClick = {
                                    innerNavController.navigate("productCreation")
                                }
                            )
                        }
                        Destination.ALLERGENS -> AllergensScreen(
                            userId = savedUserId,
                            onNext = {}
                        )
                    }
                }
            }

            composable("productDetails/{barcode}") { backStackEntry ->
                val barcode = backStackEntry.arguments?.getString("barcode") ?: return@composable
                ProductDetailsScreen(
                    barcode = barcode,
                    onBack = { innerNavController.popBackStack() }
                )
            }

            composable("productNotFound/{barcode}") { backStackEntry ->
                val barcode = backStackEntry.arguments?.getString("barcode") ?: return@composable
                ProductNotFoundScreen(
                    barcode = barcode,
                    onAddProduct = { bc ->
                        innerNavController.navigate("addProduct/$bc")
                    },
                    onBack = { innerNavController.popBackStack() }
                )
            }

            // ADD THESE NEW COMPOSABLES FOR PRODUCT CREATION
            composable("productCreation") {
                ProductCreationScreen(
                    onBack = {
                        innerNavController.popBackStack()
                    },
                    onProductCreated = {
                        innerNavController.popBackStack()
                        // Optional: Show success message
                    }
                )
            }

            composable("addProduct/{barcode}") { backStackEntry ->
                val barcode = backStackEntry.arguments?.getString("barcode") ?: ""
                ProductCreationScreen(
                    initialBarcode = barcode,
                    onBack = {
                        innerNavController.popBackStack()
                    },
                    onProductCreated = {
                        innerNavController.popBackStack()
                        // Optional: Navigate to the created product details
                        // innerNavController.navigate("productDetails/$barcode")
                    }
                )
            }

            composable("profile") {
                ProfileScreen(
                    sessionViewModel = sessionViewModel,
                    onBack = { innerNavController.popBackStack() },
                    onRequestNotificationPermission = { /* request permission */ },
                    onEditProfile = { innerNavController.navigate("editProfile") },
                    onHelpSupport = { innerNavController.navigate("contact_support") }
                )
            }

            composable("editProfile") {
                EditProfileScreen(
                    sessionViewModel = sessionViewModel,
                )
            }

            composable("products") {
                ProductsScreen(
                    onProductClick = { barcode ->
                        innerNavController.navigate("productDetails/$barcode")
                    }
                )
            }
            composable("contact_support") {
                ContactSupportScreen()
            }
        }
    }
}