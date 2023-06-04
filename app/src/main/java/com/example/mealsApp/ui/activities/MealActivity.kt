package com.example.mealsApp.ui.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mealsApp.databinding.ActivityCategoriesBinding
import com.example.mealsApp.databinding.DialogProgressBinding

import com.example.mealsApp.adapters.MealRecyclerAdapter
import com.example.mealsApp.adapters.SetOnMealClickListener
import com.example.mealsApp.data.aboutMealData.Meal
import com.example.mealsApp.mvvm.MealActivityMVVM
import com.example.mealsApp.util.Constants.Companion.CATEGORY_NAME
import com.example.mealsApp.util.Constants.Companion.MEAL_ID
import com.example.mealsApp.util.Constants.Companion.MEAL_STR
import com.example.mealsApp.util.Constants.Companion.MEAL_THUMB

class MealActivity : AppCompatActivity() {
    private lateinit var mealActivityMvvm: MealActivityMVVM
    private lateinit var binding: ActivityCategoriesBinding
    private lateinit var myAdapter: MealRecyclerAdapter
    private lateinit var mProgressDialog: Dialog


    private var categoryNme = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealActivityMvvm = ViewModelProviders.of(this)[MealActivityMVVM::class.java]
        showProgressDialog()
        prepareRecyclerView()
        mealActivityMvvm.getMealsByCategory(getCategory())
        mealActivityMvvm.observeMeal().observe(this
        ) { t ->
            if (t == null) {
                hideProgressDialog()
                Toast.makeText(applicationContext, "No meals in this category", Toast.LENGTH_SHORT)
                    .show()
                onBackPressed()
            } else {
                myAdapter.setCategoryList(t!!)
                binding.tvCategoryCount.text = categoryNme + " : " + t.size.toString()
                hideProgressDialog()
            }
        }

        myAdapter.setOnMealClickListener(object : SetOnMealClickListener {
            override fun setOnClickListener(meal: Meal) {
                val intent = Intent(applicationContext, MealDetailsActivity::class.java)
                intent.putExtra(MEAL_ID, meal.idMeal)
                intent.putExtra(MEAL_STR, meal.strMeal)
                intent.putExtra(MEAL_THUMB, meal.strMealThumb)
                startActivity(intent)
            }
        })
    }

    private fun showProgressDialog() {
        val binding = DialogProgressBinding.inflate(layoutInflater)
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(binding.root)
        mProgressDialog.show()
    }

    private fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }


    private fun getCategory(): String {
        val tempIntent = intent
        val x = intent.getStringExtra(CATEGORY_NAME)!!
        categoryNme = x
        return x
    }

    private fun prepareRecyclerView() {
        myAdapter = MealRecyclerAdapter()
        binding.mealRecyclerview.apply {
            adapter = myAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}