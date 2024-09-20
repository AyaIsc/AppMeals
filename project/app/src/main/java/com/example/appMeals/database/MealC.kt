package com.example.appMeals.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_information")
data class MealC(
    @PrimaryKey
    val idMeal: Int,
    val strMeal : String?,
    val strMealThumb : String?
)