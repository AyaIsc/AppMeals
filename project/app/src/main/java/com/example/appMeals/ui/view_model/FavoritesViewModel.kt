package com.example.appMeals.ui.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appMeals.database.MealDB
import com.example.appMeals.model.Repository
import kotlinx.coroutines.launch
/**
 * ViewModel responsible for managing data related to the favorites screen, such as loading, removing, and refreshing favorite meals.
 */

class FavoritesViewModel : ViewModel() {
    private val _favoriteMeals = mutableStateOf<List<MealDB>>(emptyList())
    val favoriteMeals: State<List<MealDB>> = _favoriteMeals

    init {
        loadFavoriteMeals()
    }

    private fun loadFavoriteMeals() {
        viewModelScope.launch {
            _favoriteMeals.value = Repository.getAllSavedMeals()
        }
    }

    fun refreshFavorites() {
        viewModelScope.launch {
            val favoriteMealsList = Repository.getAllSavedMeals()
            _favoriteMeals.value = favoriteMealsList
        }
    }
}