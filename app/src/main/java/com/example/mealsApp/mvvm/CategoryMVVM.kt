package com.example.mealsApp.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealsApp.data.aboutMealData.CategoryResponse
import com.example.mealsApp.data.aboutMealData.MealCategory
import com.example.mealsApp.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMVVM : ViewModel() {
    private var categories: MutableLiveData<List<MealCategory>> = MutableLiveData<List<MealCategory>>()

    init {
        getCategories()
    }

    private fun getCategories(){
        RetrofitInstance.foodApi.getCategories().enqueue(object : Callback<CategoryResponse>{
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                categories.value = response.body()!!.categories
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d(TAG,t.message.toString())
            }

        })
    }

    fun observeCategories():LiveData<List<MealCategory>>{
        return categories
    }
}