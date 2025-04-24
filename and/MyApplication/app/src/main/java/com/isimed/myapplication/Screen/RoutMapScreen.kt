import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.isimed.myapplication.network.OrsDirectionsRequest
import com.isimed.myapplication.network.OrsRetrofitInstance

import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteMapScreen(
    navController: NavController,
    destination: GeoPoint
) {
    val context = LocalContext.current
    var location by remember { mutableStateOf<GeoPoint?>(null) }
    var hasLocationPermission by remember { mutableStateOf(false) }
    var routePoints by remember { mutableStateOf<List<GeoPoint>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val orsApi = remember { OrsRetrofitInstance.createApi() }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

    // Request location permissions
    LaunchedEffect(Unit) {
        permissionLauncher.launch(arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    // Fetch location when permission granted
    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            try {
                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    ?: locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                lastKnownLocation?.let {
                    location = GeoPoint(it.latitude, it.longitude)
                } ?: run {
                    error = "Impossible de récupérer votre position"
                }
            } catch (e: SecurityException) {
                error = "Permission de localisation refusée"
            } catch (e: Exception) {
                error = "Erreur de localisation: ${e.message}"
            }
        }
    }

    // Fetch route when we have both points
    LaunchedEffect(location) {
        location?.let { start ->
            loading = true
            error = null
            try {
                val req = OrsDirectionsRequest(
                    coordinates = listOf(
                        listOf(start.longitude, start.latitude),
                        listOf(destination.longitude, destination.latitude)
                    )
                )
                val resp = orsApi.getRoute(req)

                if (resp.isSuccessful) {
                    resp.body()?.features?.firstOrNull()?.geometry?.coordinates?.let { coords ->
                        routePoints = coords.map { GeoPoint(it[1], it[0]) }
                    } ?: run {
                        error = "Aucun itinéraire trouvé"
                    }
                } else {
                    error = "Erreur du serveur: ${resp.code()}"
                }
            } catch (e: Exception) {
                error = "Erreur réseau: ${e.message}"
            } finally {
                loading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Itinéraire vers la pharmacie") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when {
                error != null -> Text(error!!, Modifier.align(Alignment.Center))
                !hasLocationPermission -> Text("Activez la localisation…", Modifier.align(Alignment.Center))
                location == null || loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                else -> {
                    val mapView = remember {
                        MapView(context).apply {
                            Configuration.getInstance().userAgentValue = context.packageName
                            setTileSource(TileSourceFactory.MAPNIK)
                            controller.setZoom(14.0)
                            controller.setCenter(location!!)

                            // Start marker
                            overlays.add(Marker(this).apply {
                                position = location!!
                                title = "Votre position"
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            })

                            // End marker
                            overlays.add(Marker(this).apply {
                                position = destination
                                title = "Pharmacie"
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            })

                            // Route line
                            if (routePoints.isNotEmpty()) {
                                overlays.add(Polyline().apply {
                                    setPoints(routePoints)
                                    outlinePaint.color = Color.parseColor("#3F51B5")
                                    outlinePaint.strokeWidth = 8f
                                })
                            }
                        }
                    }

                    AndroidView(
                        factory = { mapView },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}