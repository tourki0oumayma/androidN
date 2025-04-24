package com.isimed.myapplication.Screen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.draw.clip
import com.isimed.myapplication.R

@Composable
fun DoctorProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFEDE7F6))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image et nom du docteur
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF7E57C2), shape = RoundedCornerShape(20.dp))
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.doctor_image), // Assurez-vous d'avoir l'image dans res/drawable
                    contentDescription = "Doctor Image",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Dr. Sarah Thompson",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = "Radiology Specialist",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

        // Informations du docteur
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-40).dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "📍 8502 Preston Rd, Inglewood, Maine 98380", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(label = "Patients", value = "500+")
                    StatItem(label = "Experience", value = "4 Years+")
                    StatItem(label = "Rating", value = "⭐ 4.5")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Biography",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "A board-certified doctor with 15+ years of experience specializing in heart conditions like coronary artery disease and arrhythmias.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Boutons d'actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionButton(icon = Icons.Default.Language, label = "Website")
                    ActionButton(icon = Icons.Default.Message, label = "Message")
                    ActionButton(icon = Icons.Default.Call, label = "Call")
                    ActionButton(icon = Icons.Default.Share, label = "Share")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Gérer la prise de rendez-vous */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Make Appointment", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = label, fontSize = 14.sp)
    }
}

@Composable
fun ActionButton(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, modifier = Modifier.size(24.dp))
        Text(text = label, fontSize = 12.sp)
    }
}


