package com.example.appMeals.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_information")
data class MealDB(
    @PrimaryKey
    @ColumnInfo(name = "mealId")
    val idMeal: Int,
    @ColumnInfo(name = "mealName")
    val strMeal: String,
    @ColumnInfo(name = "mealCountry")
    val strArea: String,
    @ColumnInfo(name = "mealCategory")
    val strCategory: String,
    @ColumnInfo(name = "mealInstruction")
    val strInstructions: String,
    @ColumnInfo(name = "mealThumb")
    val strMealThumb: String,
    @ColumnInfo(name = "mealYoutubeLink")
    val strYoutube: String
)
