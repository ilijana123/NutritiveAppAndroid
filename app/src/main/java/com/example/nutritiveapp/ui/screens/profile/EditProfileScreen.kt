package com.example.nutritiveapp.ui.screens.profile

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nutritiveapp.App.Companion.context
import com.example.nutritiveapp.data.model.request.UpdateUserRequest
import com.example.nutritiveapp.viewmodels.SessionViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditProfileScreen(
    sessionViewModel: SessionViewModel,
) {
    val session = sessionViewModel.session.value

    var username by remember { mutableStateOf(session.username ?: "") }
    var firstName by remember { mutableStateOf(session.firstName ?: "") }
    var lastName by remember { mutableStateOf(session.lastName ?: "") }
    var email by remember { mutableStateOf(session.email ?: "") }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        val request = UpdateUserRequest(
                            username = username,
                            firstName = firstName,
                            lastName = lastName,
                            email = email
                        )
                        sessionViewModel.updateUser(request) { success ->
                            if (success) {
                                Toast.makeText(context, "Profile updated!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text("Save Changes", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
