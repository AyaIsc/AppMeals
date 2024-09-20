package com.example.appMeals.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.appMeals.database.Category
import com.example.appMeals.ui.view_model.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Composable function that displays a screen for searching meals by category or name.
 * Users can click on a category or meal to view its details.
 *
 * @param onCategoryClick Callback function triggered when a category is clicked.
 * @param onMealClick Callback function triggered when a meal is clicked.
 *
 * @Composable
 * @SearchScreen
 * Displays a screen for searching meals by category or name, allowing users to click on a category or meal to view its details.
 */

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchScreen( onCategoryClick: (String) -> Unit,onMealClick : (Int) -> Unit ) {
    val viewModel: SearchViewModel = viewModel()

    var searchText by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.observeAsState(emptyList())
    var categories by remember { mutableStateOf(emptyList<Category>()) }


    LaunchedEffect(Unit) {
        viewModel.getCategories { loadedCategories ->
            categories = loadedCategories
        }
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(searchText) {
        coroutineScope.launch {
            delay(300)
            viewModel.searchMeals(searchText)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = searchText,
            onValueChange = {
                searchText = it

                viewModel.searchMeals(it)
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (searchText.isEmpty()) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clickable {
                                    onCategoryClick(category.strCategory)
                                }

                        ) {

                            Image(
                                painter = rememberImagePainter(data = category.strCategoryThumb),
                                contentDescription = category.strCategory
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = category.strCategory,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {

            LazyColumn {
                items(searchResults) { meal ->

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onMealClick(meal.idMeal) }
                            .padding(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
                                painter = rememberImagePainter(data = meal.strMealThumb),
                                contentDescription = meal.strMeal,
                                modifier = Modifier.size(100.dp)
                            )


                            Text(
                                text = meal.strMeal ?: "Meal Name Unavailable",
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        }

    }
}

