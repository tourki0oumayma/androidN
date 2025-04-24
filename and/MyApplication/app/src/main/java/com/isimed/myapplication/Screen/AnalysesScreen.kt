package com.isimed.myapplication.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.isimed.myapplication.ViewModel.AnalysesViewModel
import java.util.Date

data class Analyse(
    val id: Long,
    val nomAnalyse: String,
    val prix: Float,
    val dateResultat: Date
)

@Composable
fun AnalysesScreen(viewModel: AnalysesViewModel = viewModel()) {
    val analyses by viewModel.analyses.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAnalyses()
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(analyses) { analyse ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Analyse : ${analyse.nomAnalyse}")
                    Text(text = "Résultat : ${analyse.prix}")
                    Text(text = "Date : ${analyse.dateResultat}")
                }
            }
        }
    }
}
