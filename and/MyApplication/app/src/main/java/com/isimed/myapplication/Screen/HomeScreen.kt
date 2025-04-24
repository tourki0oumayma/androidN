package com.isimed.myapplication.Screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.isimed.myapplication.R
import com.isimed.myapplication.ViewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()

    ) { val token by homeViewModel.token.collectAsState()
    // Redirect to login if the token is cleared (user logged out)
    LaunchedEffect(token) {
        if (token.isEmpty()) {
            navController.navigate("registre") {
                popUpTo("home") { inclusive = true } // Clear home from backstack
            }
        }

    }
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchRow()
                Banner()
                Categories()
                PopularCourses()
                ItemList()
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { homeViewModel.logout() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("Hospital") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go to Fake Data")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("maps") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go to Maps")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("currentLocation") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show My Location")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("fileManager") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go File manager")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("Doctor") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go to Doctor")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Example coordinates near downtown San Francisco
                        val lat = 37.7749
                        val lon = -122.4194
                        navController.navigate("route/$lat/$lon")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show Route to San Francisco")
                }

            }
        }
    }
    data class Items(
        val title: String,
        val name: String,
        val price: Int,
        val score: Double,
        val picUrl: Int
    )

    @Composable
    fun ItemList() {
        val people: List<Items> = listOf(
            Items("Quick Learn C# Language", "Jammie Young", 128, 4.6, R.drawable.pic1),
            Items("Full Course Android Kotlin", "Alex Alba", 368, 4.2, R.drawable.pic2),
            Items("Quick Learn C# Language", "Jammie Young", 128, 4.6, R.drawable.pic1)
        )

        LazyRow(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(people.size) { index ->
                val item = people[index]
                Column(
                    modifier = Modifier
                        .height(250.dp)
                        .width(250.dp)
                        .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                        .clickable { println("Clicked on: ${item.title}") }
                        .padding(8.dp)
                ) {
                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val (topImg, title) = createRefs()

                        Image(
                            painter = painterResource(id = item.picUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .constrainAs(topImg) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = item.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(android.graphics.Color.parseColor("#90000000")))
                                .padding(6.dp)
                                .constrainAs(title) {
                                    bottom.linkTo(topImg.bottom)
                                    start.linkTo(parent.start)
                                },
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 14.sp

                        )



                    }
                }
            }
        }
    }
    @Composable
    fun PopularCourses() {
        Row(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {
            Text(
                text = "Popular Courses",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "See all",
                fontWeight = FontWeight.SemiBold,
                color = Color(android.graphics.Color.parseColor("#521c98")),
                fontSize = 16.sp
            )
        }

    }

    @Composable
    fun Categories() {
        Row(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {
            Text(
                text = "Category",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "See all",
                fontWeight = FontWeight.SemiBold,
                color = Color(android.graphics.Color.parseColor("#521c98")),
                fontSize = 16.sp
            )
        }

        // Add some space between each category by wrapping each Column in a Spacer
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            // First Category
            Column(
                modifier = Modifier
                    .weight(0.25f)
                    .padding(end = 8.dp),  // Add space between columns
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat1),
                    contentDescription = null,
                    Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )
                Text(
                    text = "Business",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp),
                    color=Color(android.graphics.Color.parseColor("#521c98")),
                )
            }

            // Second Category
            Column(
                modifier = Modifier
                    .weight(0.25f)
                    .padding(horizontal = 8.dp), // Add space between columns
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat2),
                    contentDescription = null,
                    Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )
                Text(
                    text = "Creative",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp),
                    color=Color(android.graphics.Color.parseColor("#521c98")),
                )
            }

            // Third Category
            Column(
                modifier = Modifier
                    .weight(0.25f)
                    .padding(horizontal = 8.dp),  // Add space between columns
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat3),
                    contentDescription = null,
                    Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )
                Text(
                    text = "Coding",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp),
                    color=Color(android.graphics.Color.parseColor("#521c98")),
                )
            }

            // Fourth Category
            Column(
                modifier = Modifier
                    .weight(0.25f)
                    .padding(start = 8.dp),  // Add space between columns
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat4),
                    contentDescription = null,
                    Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )
                Text(
                    text = "Gaming",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp),
                    color=Color(android.graphics.Color.parseColor("#521c98")),
                )
            }
        }
    }

    @Composable
    fun Banner() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .height(160.dp)
                .background(
                    color = Color(0xFF521C98),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            val (img, text, button) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.girl2),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(img) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = "Advanced certification\nProgram in AI",
                style = TextStyle(fontSize = 18.sp, color = Color.White),
                modifier = Modifier.constrainAs(text) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top, margin = 16.dp)
                }
            )

            Button(
                onClick = { /* Action for button */ },
                modifier = Modifier.constrainAs(button) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(text.start)
                },
                shape = RoundedCornerShape(50)
            ) {
                Text(text = "Learn More", color = Color.White)
            }
        }
    }

    @Composable
    fun SearchRow() {
        var text by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "Searching...", fontStyle = FontStyle.Italic) },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = null,
                        modifier = Modifier.size(23.dp)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    unfocusedIndicatorColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    focusedTextColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    unfocusedTextColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
                ),
                modifier = Modifier
                    .weight(1f)
                    .border(
                        1.dp,
                        Color(android.graphics.Color.parseColor("#521098")),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.White, RoundedCornerShape(8.dp))
            )

            // L'icône de la cloche doit être en dehors du TextField
            Image(
                painter = painterResource(id = R.drawable.bell),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(30.dp)
            )
        }
    }


