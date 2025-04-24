package com.isimed.myapplication.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.isimed.myapplication.R
import com.isimed.myapplication.ViewModel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.logo_annimation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.74f
    )
    val token by authViewModel.token
    // Observe token changes and navigate when a token is set
    LaunchedEffect(token) {
        if (token.isNotEmpty()) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true } // Clears login from backstack
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Animation
        LottieAnimation(
            modifier = Modifier.size(300.dp),
            composition = composition,
            progress = { progress }
        )

        Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            leadingIcon = { Icon(Icons.Rounded.AccountCircle, contentDescription = null) },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            isError = emailError.isNotEmpty(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = if (emailError.isNotEmpty()) Color.Red else Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        if (emailError.isNotEmpty()) {
            Text(text = emailError, color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Password Field
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) painterResource(id = R.drawable.baseline_visibility_24)
                else painterResource(id = R.drawable.baseline_visibility_off_24)
                Icon(painter = image, contentDescription = null, modifier = Modifier.clickable { passwordVisible = !passwordVisible })
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            isError = passwordError.isNotEmpty(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = if (passwordError.isNotEmpty()) Color.Red else Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        if (passwordError.isNotEmpty()) {
            Text(text = passwordError, color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                emailError = when {
                    email.isBlank() -> "Email is required"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
                    else -> ""
                }
                passwordError = if (password.isBlank()) "Password is required" else ""

                if (emailError.isEmpty() && passwordError.isEmpty()) {
                    authViewModel.login(email, password)
                }
            },
            enabled = email.isNotBlank() && password.isNotBlank() && !authViewModel.isLoading.value,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 90.dp)
        ) {
            Text(text = if (authViewModel.isLoading.value) "Loading..." else "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Forgot Password
        Text(
            text = "Forgot Password?",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { /* Handle forgot password logic */ }
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Register
        Row {
            Text(text = "Not a member? ")
            Text(
                text = "Sign up now!",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { navController.navigate("register") }
            )
        }

        // Error message
        if (authViewModel.error.value.isNotEmpty()) {
            Text(
                text = "Error: ${authViewModel.error.value}",
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        // Success message
        if (authViewModel.token.value.isNotEmpty()) {
            Text(
                text = "Login successful!",
                color = Color.Green,
                fontSize = 14.sp
            )
        }
    }
}
