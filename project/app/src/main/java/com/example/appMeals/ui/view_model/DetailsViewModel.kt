package com.example.appMeals.ui.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appMeals.database.MealDetail
import com.example.appMeals.model.Repository
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing details related to a specific meal, such as retrieving meal details, checking if the meal is a favorite, adding/removing the meal from favorites, and toggling the favorite state.
 */

class DetailsViewModel : ViewModel() {
    fun getMealById(id: Int, onMealLoaded: (MealDetail?) -> Unit) {
        viewModelScope.launch {
            val meal = Repository.getMealById(id)
            onMealLoaded(meal)
        }
    }

    var checkedState by mutableStateOf(false)

    fun checkIfFavorite(meal: MealDetail) {
        viewModelScope.launch {
            checkedState = Repository.checkIfFavorite(meal.idMeal.toInt())
        }
    }

    private fun addToFavorites(meal: MealDetail) {
        viewModelScope.launch {
            Repository.addToFavorites(meal)
            checkedState = true
        }
    }

    private fun removeFromFavorites(meal: MealDetail) {
        viewModelScope.launch {
            Repository.removeFromFavorites(meal.idMeal.toInt())
            checkedState = false
        }
    }

    fun toggleFavorite(meal: MealDetail) {
        if (checkedState) {
            removeFromFavorites(meal)
        } else {
            addToFavorites(meal)
        }
    }


}
