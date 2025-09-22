Nutritive Android App

The Nutritive App is a mobile application that helps users scan, search, and save food products while checking for allergens and nutritional details.
It is built with Kotlin, uses Jetpack Compose for UI, and integrates with the Nutritive REST API backend deployed on AWS(https://github.com/ilijana123/NutritiveAppRestApi/blob/main/README.md).

Authentication

User login & registration with JWT tokens and secure API calls via Retrofit interceptors.

Products

Browsing product catalog, displaying nutritional info (addtives, nutriments, allergens, tags, etc.) and saving products to personal list.

 Allergen Alerts

Real-time push notifications via Firebase Cloud Messaging (FCM) for warning users when a saved/scanned product contains allergens they’re sensitive to.

 Modern UI built with Jetpack Compose, Material 3 design components.

 Networking

Uses Retrofit + OkHttp for API communication, Kotlin coroutines & Flow for async data.

 Architecture

The app follows MVVM with Clean Architecture:

Presentation Layer → Jetpack Compose UI + ViewModels

Domain Layer → Use cases (business logic)

Data Layer → Repositories + Retrofit API services

Dependency Injection → Hilt modules provide dependencies across layers
