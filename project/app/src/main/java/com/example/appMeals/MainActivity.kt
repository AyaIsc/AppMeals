
package com.example.appMeals

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appMeals.model.Repository
import com.example.appMeals.ui.AboutScreen
import com.example.appMeals.ui.CategoryScreen
import com.example.appMeals.ui.DetailsScreen
import com.example.appMeals.ui.DiscoveryScreen
import com.example.appMeals.ui.FavoritesScreen
import com.example.appMeals.ui.NoConnectionScreen
import com.example.appMeals.ui.SearchScreen
import com.example.appMeals.ui.SplashScreen
import com.example.appMeals.ui.theme.He2bTheme
import com.example.appMeals.ui.theme.louisa
import com.example.appMeals.ui.view_model.ConnectivityViewModel

/**
 * Represents the main activity of the application.
 * Handles the initialization of the app and navigation between different screens.
 * Also provides lifecycle callbacks for activity events.
 */

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Repository.initDatabase(applicationContext)
        setContent {
            He2bTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                    var isAnimationComplete by remember { mutableStateOf(false) }


                    val onAnimationEnd = { isAnimationComplete = true }

                    if (isAnimationComplete) {

                        App(applicationContext)
                    } else {

                        SplashScreen(onAnimationEnd)
                    }
                }
            }
        }
    }


    /**
     * Makes the application visible on the screen, but no user interaction is possible yet.
     */
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }

    /**
     * Brings the app to the foreground and the user can now interact.
     */
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    /**
     * Called when the activity is about to be restarted after stopping.
     * This method is typically called after `onStop()` and before `onStart()`.
     */
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }

    /**
     * Called when the activity is no longer visible to the user
     * Used for cleanup or saving state
     */
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    /**
     * Called when the activity is no longer visible to the user and is not in the foreground.
     */
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    /**
     * Called when the activity is about to be destroyed. This is the final callback
     * that the activity will receive.
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }
}


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun App(
    context: Context,
    navController: NavHostController = rememberNavController()
) {
  

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.primary,
                title = { Text(text = "App Meals", fontFamily = louisa) },
                contentColor = Color.LightGray
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(80.dp),
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.LightGray
            ) {
                BottomBar(navController = navController)
            }
        },
    ) {
        Surface(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            NavHost(
                navController = navController,
                startDestination = "Discovery",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("Discovery") {
                    if (ConnectivityViewModel.NetworkManager.isWifiConnected(context)) {
                        DiscoveryScreen(
                            onImageClicked = { mealId ->
                                Log.d("passedM", "meal id arrived $mealId")
                                navController.navigate("Details/$mealId")
                            }
                        )
                    } else {
                        NoConnectionScreen()
                    }
                }
                composable("Details/{mealId}") { backStackEntry ->
                    val mealId = backStackEntry.arguments?.getString("mealId")?.toIntOrNull()
                    if (mealId != null) {
                        if (ConnectivityViewModel.NetworkManager.isWifiConnected(context)) {
                            DetailsScreen(mealId) {
                                navController.popBackStack()
                            }
                        } else {
                            NoConnectionScreen()
                        }
                    }
                }
                composable("Favorites") {
                    if (ConnectivityViewModel.NetworkManager.isWifiConnected(context)) {
                        FavoritesScreen { mealId ->
                            navController.navigate("Details/$mealId")
                        }
                        navController.previousBackStackEntry?.savedStateHandle?.set("updateFavorites", true)
                    } else {
                        NoConnectionScreen()
                    }
                }
                composable("Search") {
                    if (ConnectivityViewModel.NetworkManager.isWifiConnected(context)) {
                        SearchScreen(
                            onCategoryClick = { categoryId -> navController.navigate("Category/$categoryId") },
                            onMealClick = { mealId -> navController.navigate("Details/$mealId") }
                        )
                    } else {
                        NoConnectionScreen()
                    }
                }
                composable("Category/{categoryId}") { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getString("categoryId")
                    if (categoryId != null) {
                        if (ConnectivityViewModel.NetworkManager.isWifiConnected(context)) {
                            CategoryScreen(
                                categoryId = categoryId,
                                onMealClicked = { mealId ->
                                    navController.navigate("Details/$mealId")
                                },
                                onBackClicked = {
                                    navController.popBackStack()
                                }
                            )
                        } else {
                            NoConnectionScreen()
                        }
                    }
                }
                composable("About") {
                    if (ConnectivityViewModel.NetworkManager.isWifiConnected(context)) {
                        AboutScreen()
                    } else {
                        NoConnectionScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                navController.navigate("Discovery")
            }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "Discovery",
                    tint = Color.LightGray
                )
                Text(
                    text = "Discovery",
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    fontFamily = louisa
                )
            }
        }
        Button(
            onClick = {
                navController.navigate("Favorites")
            }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites", tint = Color.LightGray)
                Text(
                    text = "Favorites",
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    fontFamily = louisa
                )
            }
        }
        Button(
            onClick = {
                navController.navigate("Search")
            }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.LightGray)
                Text(
                    text = "Search",
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    fontFamily = louisa
                )
            }
        }
        Button(
            onClick = {
                navController.navigate("About")
            }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "Info", tint = Color.LightGray)
                Text(
                    text = "Info",
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    fontFamily = louisa
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.M)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    He2bTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            App(LocalContext.current)
        }
    }
}
