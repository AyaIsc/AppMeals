package com.example.appMeals.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MealDAO {

    @Insert
    suspend fun insertFavorite(meal: MealDB)

    @Update
    suspend fun updateFavorite(meal:MealDB)



    @Query("SELECT * FROM meal_information WHERE mealId = :id")
    suspend fun getMealById(id: Int): MealDB?


    @Query("DELETE FROM meal_information WHERE mealId =:id")
    suspend fun deleteMealById(id: Int)

    @Delete
    suspend fun deleteMeal(meal:MealDB)

    @Query("SELECT * FROM meal_information order by mealId asc")
    suspend fun getAllSavedMeals(): List<MealDB>


}
