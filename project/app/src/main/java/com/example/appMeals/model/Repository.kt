package com.example.appMeals.model

import android.content.Context
import com.example.appMeals.database.Category
import com.example.appMeals.database.MealC
import com.example.appMeals.database.MealDAO
import com.example.appMeals.database.MealDB
import com.example.appMeals.database.MealDatabase
import com.example.appMeals.database.MealDetail
import com.example.appMeals.database.RandomMealResponse
import com.example.appMeals.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository object responsible for managing data operations, such as fetching meals by category, getting meal details by ID, checking if a meal is a favorite, adding/removing meals to/from favorites, fetching a random meal, getting all saved meals, fetching categories, and searching meals.
 */

object Repository {

private var database: MealDatabase? = null
    private var mealDAO: MealDAO? = null
    /**
     * Initializes the database instance and DAO.
     *
     * @param context The application context.
     */
    fun initDatabase(context: Context) {
        if (database == null) {
            database = MealDatabase.getInstance(context)
            mealDAO = database!!.mealDao()
        }
    }
    /**
     * Fetches meals by category from the API.
     *
     * @param categoryId The ID of the category.
     * @return A list of meals in the specified category, or null if an error occurs.
     */
    suspend fun getMealsByCategory(categoryId: String): List<MealC>? {
        return try {
            val response = RetrofitInstance.foodApi.getMealsByCategory(categoryId)
            response.meals
        } catch (e: Exception) {
            null
        }
    }
    /**
     * Fetches meal details by ID from the API.
     *
     * @param id The ID of the meal.
     * @return The meal details, or null if an error occurs.
     */
    suspend fun getMealById(id: Int): MealDetail? {
        return try {
            val response = RetrofitInstance.foodApi.getMealById(id)
            response.meals.getOrNull(0)
        } catch (e: Exception) {
            null
        }
    }
    /**
     * Checks if a meal is in the favorites database.
     *
     * @param mealId The ID of the meal.
     * @return True if the meal is a favorite, false otherwise.
     */
    suspend fun checkIfFavorite(mealId: Int): Boolean {
        val meal = mealDAO?.getMealById(mealId)
        return meal != null
    }
    /**
     * Adds a meal to the favorites database.
     *
     * @param meal The meal details to be added.
     */
    suspend fun addToFavorites(meal: MealDetail) {
        val mealDB = MealDB(
            idMeal = meal.idMeal.toInt(),
            strMeal = meal.strMeal,
            strArea = meal.strArea,
            strCategory = meal.strCategory,
            strInstructions = meal.strInstructions,
            strMealThumb = meal.strMealThumb,
            strYoutube = meal.strYoutube
        )
        mealDAO?.insertFavorite(mealDB)
    }
    /**
     * Removes a meal from the favorites database by ID.
     *
     * @param mealId The ID of the meal to be removed.
     */
    suspend fun removeFromFavorites(mealId: Int) {
        mealDAO?.deleteMealById(mealId)
    }
    /**
     * Fetches a random meal from the API.
     *
     * @return The random meal response.
     */
    suspend fun getRandomMeal(): RandomMealResponse {
        return RetrofitInstance.foodApi.getRandomMeal()
    }

    /**
     * Fetches all saved meals from the favorites database.
     *
     * @return A list of all saved meals.
     */
    suspend fun getAllSavedMeals(): List<MealDB> {
        return mealDAO?.getAllSavedMeals() ?: emptyList()
    }

    /**
     * Fetches all categories from the API.
     *
     * @return A list of categories.
     */
    suspend fun getCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.foodApi.getCategories()
                response.categories
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    /**
     * Searches for meals by name from the API.
     *
     * @param query The search query.
     * @return A list of meals that match the search query.
     */
    suspend fun searchMeals(query: String): List<MealC> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.foodApi.getMealByName(query)
                response.meals
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}
