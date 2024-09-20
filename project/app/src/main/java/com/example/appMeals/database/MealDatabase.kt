package com.example.appMeals.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MealDB::class], version = 1, exportSchema = false)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDAO

    companion object {
        private const val DATABASE_NAME = "meal_db"
        private var sInstance: MealDatabase? = null

        fun getInstance(context: Context): MealDatabase {
            if (sInstance == null) {
                synchronized(MealDatabase::class) {
                    if (sInstance == null) {
                        val dbBuilder = Room.databaseBuilder(
                            context.applicationContext,
                            MealDatabase::class.java,
                            DATABASE_NAME
                        )
                        sInstance = dbBuilder.build()
                    }
                }
            }
            return sInstance!!
        }
    }
}
