package com.example.mealsApp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.mealsApp.data.aboutMealData.MealDatabase

@Dao
interface Dao {
    @Insert
    fun insertFavorite(meal: MealDatabase)

    @Update
    fun updateFavorite(meal:MealDatabase)

    @Query("SELECT * FROM meal_information order by mealId asc")
    fun getAllSavedMeals():LiveData<List<MealDatabase>>

    @Query("SELECT * FROM meal_information WHERE mealId =:id")
    fun getMealById(id:String):MealDatabase

    @Query("DELETE FROM meal_information WHERE mealId =:id")
    fun deleteMealById(id:String)

    @Delete
    fun deleteMeal(meal:MealDatabase)
}