package com.example.appMeals.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appMeals.database.MealC
import com.example.appMeals.model.Repository
import kotlinx.coroutines.launch
/**
 * ViewModel responsible for fetching meals by category from the repository and providing them to the UI.
 */

class CategoryViewModel : ViewModel() {


    private val repository = Repository

    fun getMealsByCategory(
        categoryId: String,
        onCategoryLoaded: (List<MealC>?) -> Unit
    ) {
        viewModelScope.launch {
            val meals = repository.getMealsByCategory(categoryId)
            onCategoryLoaded(meals)
        }
    }

}