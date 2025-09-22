package com.example.nutritiveapp.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiveapp.LoginTextField
import com.example.nutritiveapp.data.model.request.SignUpRequest
import com.example.nutritiveapp.ui.theme.DarkGreen
import com.example.nutritiveapp.ui.theme.LightGreenWhite
import com.example.nutritiveapp.ui.theme.MediumGreen
import com.example.nutritiveapp.ui.theme.dimens
import com.example.nutritiveapp.viewmodels.SignUpViewModel
import android.util.Patterns

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    onSignUpSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstNameError by remember { mutableStateOf<String?>(null) }
    var lastNameError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var backendError by remember { mutableStateOf<String?>(null) }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(LightGreenWhite, Color.White)))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(MaterialTheme.dimens.large))
            Spacer(Modifier.height(MaterialTheme.dimens.large))
            Spacer(Modifier.height(MaterialTheme.dimens.large))

            Surface(
                shape = RoundedCornerShape(24.dp),
                tonalElevation = 6.dp,
                shadowElevation = 6.dp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sign up",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen
                        )
                    )

                    Spacer(Modifier.height(MaterialTheme.dimens.medium1))

                    LoginTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "First name",
                        value = firstName,
                        onValueChange = {
                            firstName = it
                            firstNameError = null
                        }
                    )
                    firstNameError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

                    Spacer(Modifier.height(MaterialTheme.dimens.small2))

                    LoginTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Last name",
                        value = lastName,
                        onValueChange = {
                            lastName = it
                            lastNameError = null
                        }
                    )
                    lastNameError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

                    Spacer(Modifier.height(MaterialTheme.dimens.small2))

                    LoginTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Username",
                        value = username,
                        onValueChange = {
                            username = it
                            usernameError = null
                        }
                    )
                    usernameError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

                    Spacer(Modifier.height(MaterialTheme.dimens.small2))

                    LoginTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Email",
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = null
                        }
                    )
                    emailError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

                    Spacer(Modifier.height(MaterialTheme.dimens.small2))

                    LoginTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Password",
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = null
                        },
                        isPassword = true
                    )
                    passwordError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

                    Spacer(Modifier.height(MaterialTheme.dimens.medium1))

                    backendError?.let {
                        Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(8.dp))
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.dimens.buttonHeight),
                        onClick = {
                            firstNameError = null
                            lastNameError = null
                            usernameError = null
                            emailError = null
                            passwordError = null
                            backendError = null

                            var valid = true
                            if (firstName.isBlank()) {
                                firstNameError = "First name is required"
                                valid = false
                            }
                            if (lastName.isBlank()) {
                                lastNameError = "Last name is required"
                                valid = false
                            }
                            if (username.isBlank()) {
                                usernameError = "Username is required"
                                valid = false
                            }
                            if (email.isBlank()) {
                                emailError = "Email is required"
                                valid = false
                            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                emailError = "Invalid email format"
                                valid = false
                            }
                            if (password.isBlank()) {
                                passwordError = "Password is required"
                                valid = false
                            } else if (password.length < 6) {
                                passwordError = "Password must be at least 6 characters"
                                valid = false
                            }

                            if (!valid) return@Button

                            val request = SignUpRequest(firstName, lastName, username, email, password)
                            viewModel.signUp(
                                request,
                                onSuccess = { onSignUpSuccess() },
                                onError = { error -> backendError = error }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MediumGreen,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text("Sign up", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }

            Spacer(Modifier.height(MaterialTheme.dimens.medium1))

            Text(
                text = "Already have an account?",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.width(4.dp))

            TextButton(onClick = onBackToLogin) {
                Text(
                    text = "Sign in",
                    color = MediumGreen,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
