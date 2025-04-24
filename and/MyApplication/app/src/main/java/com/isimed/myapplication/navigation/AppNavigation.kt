package com.isimed.myapplication.navigation

import RouteMapScreen
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.osmdroid.util.GeoPoint
import com.isimed.myapplication.Screen.AnalysesScreen
import com.isimed.myapplication.Screen.CurrentLocationScreen
import com.isimed.myapplication.Screen.DoctorListScreen

import com.isimed.myapplication.Screen.DoctorProfileScreen
import com.isimed.myapplication.Screen.FileManagerScreen
import com.isimed.myapplication.Screen.HomeScreen
import com.isimed.myapplication.Screen.HospitalScreen
import com.isimed.myapplication.Screen.LoginScreen
import com.isimed.myapplication.Screen.MapSelectionScreen
import com.isimed.myapplication.Screen.RegistreScreen

import com.isimed.myapplication.util.TokenManager


@Composable
fun AppNavigation(navController: NavHostController, application: Application) {
    val tokenManager = remember { TokenManager(application) }
    val startDestination = if (!tokenManager.getToken().isNullOrEmpty()) "home" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("registre") { RegistreScreen(navController) }
        composable("details") { DoctorProfileScreen() }
        composable("Doctor") { DoctorListScreen(navController = navController) }
        composable("maps") { MapSelectionScreen(navController) }
        composable("currentLocation") { CurrentLocationScreen(navController) }
        composable("fileManager") { FileManagerScreen(navController) }
        composable("analyses") {AnalysesScreen()}
        composable("Hospital") {
            HospitalScreen(navController = navController)
        }

        composable(
            route = "route/{destLat}/{destLon}",
            arguments = listOf(
                navArgument("destLat") { type = NavType.StringType },
                navArgument("destLon") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val lat = backStackEntry.arguments?.getString("destLat")?.toDoubleOrNull() ?: 0.0
            val lon = backStackEntry.arguments?.getString("destLon")?.toDoubleOrNull() ?: 0.0
            val destination = GeoPoint(lat, lon)
            RouteMapScreen(
                navController = navController,
                destination = destination
            )
        }


    }
}