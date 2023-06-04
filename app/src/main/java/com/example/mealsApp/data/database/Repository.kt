package com.example.mealsApp.data.database

import androidx.lifecycle.LiveData
import com.example.mealsApp.data.aboutMealData.MealDatabase

class Repository(private val mealDao: Dao) {

    val mealList: LiveData<List<MealDatabase>> = mealDao.getAllSavedMeals()

    fun insertFavoriteMeal(meal: MealDatabase) {
        mealDao.insertFavorite(meal)
    }

    fun getMealById(mealId: String): MealDatabase {
        return mealDao.getMealById(mealId)
    }

    fun deleteMealById(mealId: String) {
        mealDao.deleteMealById(mealId)
    }

    fun deleteMeal(meal: MealDatabase) = mealDao.deleteMeal(meal)


}