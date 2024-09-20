package com.example.appMeals.ui



import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.appMeals.ui.theme.louisa
import com.example.appMeals.ui.view_model.DiscoveryViewModel

/**
 * Composable function that displays a random meal for discovery.
 * Users can click on the meal image to view its details.
 *
 * @param onImageClicked Callback function triggered when a meal image is clicked.
 *
 * @SuppressLint("UnrememberedMutableState")
 * @Composable
 * @DiscoveryScreen
 * Displays a random meal for discovery, allowing users to click on the image to view its details.
 */

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun DiscoveryScreen(onImageClicked: (Int) -> Unit) {
    val viewModel: DiscoveryViewModel = viewModel()
    val randomMeal by viewModel.randomMeal.observeAsState()


    var isLoading by remember { mutableStateOf(true) }


    LaunchedEffect(key1 = true) {
        viewModel.getRandomMeal()
    }

    LaunchedEffect(randomMeal) {
        randomMeal?.let {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { Text(
        text = "Discovery",
        style = MaterialTheme.typography.h3,
        modifier = Modifier.padding(vertical = 16.dp),
        fontFamily = louisa,
        color = Color.DarkGray
    )

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            randomMeal?.let { meal ->
                Image(
                    painter = rememberImagePainter(data = meal.meals[0].strMealThumb),
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onImageClicked(meal.meals[0].idMeal.toInt())
                        }
                )
                Spacer(modifier = Modifier.height(16.dp))
                val mealName = meal.meals[0].strMeal
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                fontFamily = louisa
                            )
                        ) {
                            append(mealName)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        isLoading = true
                        viewModel.getRandomMeal()
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Refresh")
                }
            }
        }
    }
}
