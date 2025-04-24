package com.isimed.myapplication.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.isimed.myapplication.R
import com.isimed.myapplication.ViewModel.DoctorViewModel

data class Doctor(
    val id: Int,
    val specialite: String,
    val matricule: String,
    val imageName: String,
    val nbLikes: Int
)

@Composable
fun DoctorListScreen(
    viewModel: DoctorViewModel = viewModel(),
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.fetchDoctors()
    }
    val doctors by viewModel.doctors
    val isLoading by viewModel.isLoading
    val error by viewModel.error
    val token by viewModel.token

    Column(modifier = Modifier.padding(16.dp)) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (error.isNotEmpty()) {
            Text(text = error, color = MaterialTheme.colors.error)
        }

        LazyColumn {
            items(doctors) { doctor ->
                DoctorCard(doctor = doctor, navController = navController)
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: Doctor, navController: NavHostController) {
    val context = LocalContext.current
    val imageId = remember(doctor.imageName) {
        if (!doctor.imageName.isNullOrBlank()) {
            context.resources.getIdentifier(doctor.imageName, "drawable", context.packageName)
        } else {
            R.drawable.default_doctor
        }
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = doctor.specialite,
                    modifier = Modifier
                        .size(60.dp)
                        .border(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f), CircleShape)
                        .padding(4.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Spécialité : ${doctor.specialite}", style = MaterialTheme.typography.h6)
                    Text(text = "Matricule : ${doctor.matricule}")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(5) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Star",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                IconButton(onClick = { /* TODO: Ajouter logique de like */ }) {
                    Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "Favorite")
                }
            }

            Button(
                onClick = {
                    navController.navigate("details")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("plus de détaille")
            }
        }
    }
}