package com.example.appMeals.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appMeals.database.Category
import com.example.appMeals.database.MealC
import com.example.appMeals.database.MealDetail
import com.example.appMeals.model.Repository
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing data related to the search screen, such as loading categories, searching for meals, and handling loading state.
 */

class SearchViewModel : ViewModel() {
    private val repository = Repository

    val loading = MutableLiveData<Boolean>()

    private val _meals = MutableLiveData<List<MealDetail>>()
    val meals: LiveData<List<MealDetail>> = _meals

    private val _searchResults = MutableLiveData<List<MealC>>()
    val searchResults: LiveData<List<MealC>> = _searchResults

    fun getCategories(onCategoriesLoaded: (List<Category>) -> Unit) {
        viewModelScope.launch {
            val categories = repository.getCategories()
            onCategoriesLoaded(categories)
        }
    }

    fun searchMeals(query: String) {
        viewModelScope.launch {
            loading.value = true
            val meals = repository.searchMeals(query)
            _searchResults.value = meals
            loading.value = false
        }
    }
}
