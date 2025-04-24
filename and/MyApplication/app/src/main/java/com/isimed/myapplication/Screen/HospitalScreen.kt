package com.isimed.myapplication.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.isimed.myapplication.ViewModel.FakeDataViewModel


// Classe de données pour représenter un laboratoire
data class Laboratoire(
    val id: Int,
    val nom: String,
    val adresse: String,
    val telephone: String
)

@Composable
fun HospitalScreen(
    viewModel: FakeDataViewModel = viewModel(),
    navController: NavHostController
){

    LaunchedEffect(Unit) {
        viewModel.fetchLaboratories()
    }
    val laboratories = viewModel.Laboratories.value


    val isLoading by viewModel.isLoading
    val error by viewModel.error
    val token by viewModel.token

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Indicateur de chargement
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Affichage d'une erreur éventuelle
        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Affichage du token retourné (issu de l'appel API via le view model)
        if (token.isNotEmpty()) {
            Text(
                text = "Token: $token",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        // Bouton ou item de navigation vers les analyses
        ServiceItem(
            icon = Icons.Default.Assignment,
            title = "My Analys",
            onClick = { navController.navigate("analyses") }
        )


        // Affichage de la liste des laboratoires
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(laboratories) { lab ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Nom: ${lab.nom}", style = MaterialTheme.typography.h6)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Adresse: ${lab.adresse}")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Téléphone: ${lab.telephone}")
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.subtitle1)
            Icon(imageVector = icon, contentDescription = title)
        }
    }
}

