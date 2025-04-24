package com.isimed.myapplication.Screen

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.isimed.myapplication.R
import com.isimed.myapplication.ViewModel.AuthViewModel

@Composable
fun RegistreScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var firstnameError by remember { mutableStateOf("") }
    var lastnameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var registrationStatus by remember { mutableStateOf("") }

    val isLoading = authViewModel.isLoading.value

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
                popUpTo("registre") { inclusive = true } // Clears login from backstack
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, top = 15.dp),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(10.dp))


        Text(
            text = "Please enter your details",
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(16.dp))

        // First Name Field
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(if (firstnameError.isEmpty()) "First Name" else firstnameError, color = if (firstnameError.isNotEmpty()) Red else Color.Unspecified) },
            leadingIcon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Last Name Field
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(if (lastnameError.isEmpty()) "Last Name" else lastnameError, color = if (lastnameError.isNotEmpty()) Red else Color.Unspecified) },
            leadingIcon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(if (emailError.isEmpty()) "Email" else emailError, color = if (emailError.isNotEmpty()) Red else Color.Unspecified) },
            leadingIcon = { Icon(imageVector = Icons.Rounded.AccountCircle, contentDescription = null) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(if (passwordError.isEmpty()) "Password" else passwordError, color = if (passwordError.isNotEmpty()) Red else Color.Unspecified) },
            leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(if (confirmPasswordError.isEmpty()) "Confirm Password" else confirmPasswordError, color = if (confirmPasswordError.isNotEmpty()) Red else Color.Unspecified) },
            leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(
            onClick = {
                firstnameError = if (firstName.isBlank()) "First name is required" else ""
                lastnameError = if (lastName.isBlank()) "Last name is required" else ""
                emailError = if (email.isBlank()) "Email is required" else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Enter a valid email address" else ""
                passwordError = if (password.isBlank()) "Password is required" else ""
                confirmPasswordError = if (confirmPassword.isBlank()) "Confirm password is required" else if (password != confirmPassword) "Passwords do not match" else ""

                if (firstnameError.isEmpty() && lastnameError.isEmpty() && emailError.isEmpty() && passwordError.isEmpty() && confirmPasswordError.isEmpty()) {
                    authViewModel.registre(firstName, lastName, email, password)
                    registrationStatus = "Registration successful!"
                } else {
                    registrationStatus = "Please fix the errors"
                }
            }
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading) {
            Text("Loading...", color = Color.Gray)
        } else {
            Text(
                text = registrationStatus,
                color = if (registrationStatus.contains("successful")) Color.Green else Color.Red,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
