package com.example.appMeals.network


import android.util.Log
import com.example.appMeals.database.CategoryResponse
import com.example.appMeals.database.MealsResponse
import com.example.appMeals.database.RandomMealResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


object RetrofitInstance {

    /**
     * Singleton instance of Retrofit HTTP client for making API requests.
     */

    val foodApi:MealService by lazy {
        Log.d("foodApi","passed over here")
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealService::class.java)
    }
}

/**
 * Interface defining the API endpoints for meal-related operations.
 */
interface MealService {
    /**
     * Fetches all meal categories from the API.
     *
     * @return The response containing a list of categories.
     */
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse
    /**
     * Fetches meals in a specific category from the API.
     *
     * @param category The category for which meals are to be fetched.
     * @return The response containing a list of meals in the specified category.
     */
    @GET("filter.php?")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse
    /**
     * Fetches a random meal from the API.
     *
     * @return The response containing the random meal.
     */

    @GET("random.php")
    suspend fun getRandomMeal(): RandomMealResponse
    /**
     * Fetches meal details by ID from the API.
     *
     * @param id The ID of the meal to fetch.
     * @return The response containing the meal details.
     */

    @GET("lookup.php?")
    suspend fun getMealById(@Query("i") id: Int): RandomMealResponse
    /**
     * Searches for meals by name from the API.
     *
     * @param name The name of the meal to search for.
     * @return The response containing a list of meals matching the search query.
     */
    @GET("search.php?")
    suspend fun getMealByName(@Query("s") name: String): MealsResponse
}





