package com.example.appMeals.ui


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.appMeals.database.MealC
import com.example.appMeals.ui.theme.louisa
import com.example.appMeals.ui.view_model.CategoryViewModel


/**
 * Composable function that displays a screen for a specific meal category.
 * The screen includes a grid of meals within the category and a back button.
 *
 * @param categoryId The ID of the meal category to display.
 * @param onMealClicked A callback function invoked when a meal is clicked, passing the meal ID.
 * @param onBackClicked A callback function invoked when the back button is clicked.
 *
 * @Composable
 * @CategoryScreen
 * Displays a grid of meals in a specified category, with a back button.
 */



@OptIn(ExperimentalCoilApi::class)
@Composable
fun CategoryScreen(
    categoryId: String,
    onMealClicked: (Int) -> Unit,
    onBackClicked: () -> Unit
) {

    val viewModel: CategoryViewModel = viewModel()
    Log.d("id rec ", "id rec ici est : $categoryId")

    var meals by remember { mutableStateOf<List<MealC>?>(null) }

    LaunchedEffect(categoryId) {
        categoryId.let { id ->
            viewModel.getMealsByCategory(id) { loadedCategoryDetail ->
                meals = loadedCategoryDetail
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = categoryId,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            fontFamily = louisa,
            color= Color.DarkGray
        )
        meals?.let { mealList ->
            Log.d("CategoryScreen", "Meals out of launched effect : $mealList")


            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Button(
                    onClick = onBackClicked,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Back", fontFamily = louisa)
                }


                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(items = mealList) { index, meal ->

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onMealClicked(meal.idMeal) }
                                .padding(8.dp)
                        ) {

                            Image(
                                painter = rememberImagePainter(data = meal.strMealThumb),
                                contentDescription = meal.strMeal,
                                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(16.dp))
                            )

                            Text(
                                text = meal.strMeal ?: "Meal Name Unavailable",
                                modifier = Modifier.padding(start = 16.dp),
                                fontFamily = louisa
                            )
                        }
                    }
                }
            }
        }
    }
}