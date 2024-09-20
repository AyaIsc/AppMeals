package com.example.appMeals.ui.view_model

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appMeals.database.RandomMealResponse
import com.example.appMeals.model.Repository
import kotlinx.coroutines.launch


/**
 * ViewModel responsible for managing data related to the discovery screen, such as fetching a random meal.
 */

class DiscoveryViewModel : ViewModel() {
    private val _randomMeal = MutableLiveData<RandomMealResponse>()
    val randomMeal: LiveData<RandomMealResponse> = _randomMeal

    fun getRandomMeal() {
        viewModelScope.launch {
            try {
                val response = Repository.getRandomMeal()
                _randomMeal.value = response
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to fetch random meal: ${e.message}", e)
            }
        }
    }
}