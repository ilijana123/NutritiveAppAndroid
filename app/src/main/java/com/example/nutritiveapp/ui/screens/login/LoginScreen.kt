package com.example.nutritiveapp.ui.screens.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiveapp.LoginTextField
import com.example.nutritiveapp.R
import com.example.nutritiveapp.data.model.request.DeviceTokenRequest
import com.example.nutritiveapp.data.model.request.LoginRequest
import com.example.nutritiveapp.data.model.response.AuthResponse
import com.example.nutritiveapp.data.remote.RetrofitInstance
import com.example.nutritiveapp.ui.theme.DarkGreen
import com.example.nutritiveapp.ui.theme.LightGreenWhite
import com.example.nutritiveapp.ui.theme.MediumGreen
import com.example.nutritiveapp.ui.theme.SoftGreenGray
import com.example.nutritiveapp.ui.theme.dimens
import com.example.nutritiveapp.viewmodels.LoginViewModel
import com.example.nutritiveapp.viewmodels.SessionViewModel
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(
    onCreateAccountClick: () -> Unit,
    viewModel: LoginViewModel = viewModel(),
    sessionViewModel: SessionViewModel = viewModel(),
    onLoginSuccess: (AuthResponse) -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(LightGreenWhite, Color.White)
                    )
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopSection()
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(24.dp),
                tonalElevation = 6.dp,
                shadowElevation = 6.dp,
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen
                        )
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))
                    LoginSection(
                        viewModel = viewModel,
                        sessionViewModel = sessionViewModel,
                        onLoginSuccess = onLoginSuccess
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))
            CreateAccountSection(onCreateAccountClick = onCreateAccountClick)
        }
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
private fun TopSection() {
    val uiColor = if (isSystemInDarkTheme()) Color.White else MediumGreen
    val screenHeight = LocalConfiguration.current.screenHeightDp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = (screenHeight / 12).dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(id = R.string.app_logo),
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    text = stringResource(id = R.string.scan_healthy),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = uiColor
                )
                Text(
                    text = stringResource(id = R.string.description),
                    style = MaterialTheme.typography.titleMedium,
                    color = SoftGreenGray
                )
            }
        }
    }
}

@Composable
private fun LoginSection(
    viewModel: LoginViewModel,
    sessionViewModel: SessionViewModel,
    onLoginSuccess: (AuthResponse) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var backendError by remember { mutableStateOf<String?>(null) }

    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

    LoginTextField(
        label = "Email",
        modifier = Modifier.fillMaxWidth(),
        value = email,
        onValueChange = {
            email = it
            emailError = null
        }
    )
    emailError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

    LoginTextField(
        label = "Password",
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = {
            password = it
            passwordError = null
        },
        isPassword = true
    )
    passwordError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))

    backendError?.let {
        Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.medium3),
        onClick = {
            emailError = null
            passwordError = null
            backendError = null

            var valid = true
            if (email.isBlank()) {
                emailError = "Email is required"
                valid = false
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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

            val request = LoginRequest(email, password)
            viewModel.login(
                request,
                onSuccess = { authResponse ->
                    sessionViewModel.login(authResponse)

                    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val fcmToken = task.result
                            Log.d("FCM", "Login flow: current token $fcmToken")

                            RetrofitInstance.deviceApi.registerDevice(DeviceTokenRequest(fcmToken))
                                .enqueue(object : Callback<Unit> {
                                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                                        if (response.isSuccessful) {
                                            Log.d("FCM", "Token registered with backend after login")
                                        } else {
                                            Log.e("FCM", "Failed token registration: ${response.code()}")
                                        }
                                    }

                                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                                        Log.e("FCM", "Error: ${t.message}")
                                    }
                                })
                        }
                    }
                    onLoginSuccess(authResponse)
                },
                onError = { error ->
                    backendError = error
                }
            )
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) SoftGreenGray else DarkGreen,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 4.dp)
    ) {
        Text(
            text = "Log in",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
        )
    }
}

@Composable
private fun ColumnScope.CreateAccountSection(
    onCreateAccountClick: () -> Unit
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else MediumGreen

    Row(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Don't have an account?",
            color = uiColor,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.width(4.dp))
        TextButton(onClick = onCreateAccountClick) {
            Text(
                "Create now",
                color = uiColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
