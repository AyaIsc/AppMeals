package com.example.appMeals.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.appMeals.ui.theme.louisa
import com.example.appMeals.ui.view_model.FavoritesViewModel

/**
 * Composable function that displays a list of favorite meals.
 * Users can click on a meal to view its details.
 *
 * @param onMealClicked Callback function triggered when a meal is clicked.
 *
 * @Composable
 * @FavoritesScreen
 * Displays a list of favorite meals, allowing users to click on a meal to view its details.
 */

@OptIn(ExperimentalCoilApi::class)
@Composable
fun FavoritesScreen(onMealClicked: (Int) -> Unit) {
    val viewModel: FavoritesViewModel = viewModel()
    val favoriteMeals by viewModel.favoriteMeals

    LaunchedEffect(viewModel) {
        viewModel.refreshFavorites()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Favorites",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(vertical = 16.dp),
            fontFamily = louisa,
            color = Color.DarkGray
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(favoriteMeals) { favoriteMeal ->

                Card(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onMealClicked(favoriteMeal.idMeal) },
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(data = favoriteMeal.strMealThumb),
                            contentDescription = favoriteMeal.strMeal,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = favoriteMeal.strMeal ,
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center,
                            fontFamily = louisa,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
        }
    }
}
