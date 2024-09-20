package com.example.appMeals.ui


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.appMeals.database.MealDetail
import com.example.appMeals.ui.theme.louisa
import com.example.appMeals.ui.view_model.DetailsViewModel



/**
 * Composable function that displays the details of a specific meal.
 * The screen includes the meal's image, name, recipe steps, and a favorite toggle button.
 *
 * @param mealId The ID of the meal to display.
 * @param onBackPressed A callback function invoked when the back button is clicked.
 *
 * @Composable
 * @DetailsScreen
 * Displays detailed information about a meal, including an image, recipe steps, and a favorite button.
 */

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailsScreen(mealId: Int?,onBackPressed: () -> Unit) {

    Log.d("DetailsScreen", "mealId: $mealId")
    val viewModel: DetailsViewModel = viewModel()

    var mealDetail by remember { mutableStateOf<MealDetail?>(null) }

    LaunchedEffect(mealId) {
        mealId?.let { id ->
            viewModel.getMealById(id) { loadedMealDetail ->
                mealDetail = loadedMealDetail
                loadedMealDetail?.let { viewModel.checkIfFavorite(it) }
            }
        }
    }
    Log.d("DetailsScreen", "mealId apres MV: $mealId")

    mealDetail?.let { meal ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),


        ) {
            item {
                IconButton(onClick = onBackPressed) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = meal.strMeal ,
                    style = TextStyle(fontSize = 35.sp, fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp)
                        .fillMaxWidth(),
                    fontFamily = louisa
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(data = meal.strMealThumb),
                        contentDescription = meal.strMeal,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )

                    IconButton(
                        onClick = {
                            viewModel.toggleFavorite(meal)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        if (viewModel.checkedState) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Checked",
                                tint = Color.Red
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Unchecked",
                                tint = Color.Gray
                            )
                        }
                    }
                }


                Text(
                    text = "Recipe Steps:",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontFamily = louisa
                )

                Text(
                    text = meal.strInstructions.split("\n").mapIndexed { index, step ->
                        "STEP ${index + 1}: $step"
                    }.joinToString("\n\n") ,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontFamily = louisa
                )

            }
        }

    }
}
