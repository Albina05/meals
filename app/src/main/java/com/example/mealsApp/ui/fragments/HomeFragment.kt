package com.example.mealsApp.ui.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mealsApp.R
import com.example.mealsApp.adapters.CategoriesRecyclerAdapter
import com.example.mealsApp.adapters.MostPopularRecyclerAdapter
import com.example.mealsApp.adapters.OnItemClick
import com.example.mealsApp.adapters.OnLongItemClick
import com.example.mealsApp.data.aboutMealData.*
import com.example.mealsApp.databinding.DialogProgressBinding
import com.example.mealsApp.databinding.FragmentHomeBinding
import com.example.mealsApp.mvvm.DetailsMVVM
import com.example.mealsApp.mvvm.MainFragMVVM
import com.example.mealsApp.ui.activities.MealActivity
import com.example.mealsApp.ui.MealBottomDialog
import com.example.mealsApp.ui.activities.MealDetailsActivity
import com.example.mealsApp.util.Constants.Companion.CATEGORY_NAME
import com.example.mealsApp.util.Constants.Companion.MEAL_AREA
import com.example.mealsApp.util.Constants.Companion.MEAL_ID
import com.example.mealsApp.util.Constants.Companion.MEAL_NAME
import com.example.mealsApp.util.Constants.Companion.MEAL_STR
import com.example.mealsApp.util.Constants.Companion.MEAL_THUMB


class HomeFragment : Fragment() {
    private lateinit var detailMvvm: DetailsMVVM
    private lateinit var mProgressDialog: Dialog


    private lateinit var myAdapter: CategoriesRecyclerAdapter
    private lateinit var mostPopularFoodAdapter: MostPopularRecyclerAdapter
    lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailMvvm = ViewModelProviders.of(this)[DetailsMVVM::class.java]
        binding = FragmentHomeBinding.inflate(layoutInflater)
        myAdapter = CategoriesRecyclerAdapter()
        mostPopularFoodAdapter = MostPopularRecyclerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainFragMVVM = ViewModelProviders.of(this)[MainFragMVVM::class.java]
        showLoadingCase()


        prepareCategoryRecyclerView()
        preparePopularMeals()


        mainFragMVVM.observeMealByCategory().observe(viewLifecycleOwner
        ) { t ->
            val meals = t!!.meals
            setMealsByCategoryAdapter(meals)
            cancelLoadingCase()
        }

        mainFragMVVM.observeCategories().observe(viewLifecycleOwner
        ) { t ->
            val categories = t!!.categories
            setCategoryAdapter(categories)
        }

        mostPopularFoodAdapter.setOnClickListener(object : OnItemClick {
            override fun onItemClick(meal: Meal) {
                val intent = Intent(activity, MealDetailsActivity::class.java)
                intent.putExtra(MEAL_ID, meal.idMeal)
                intent.putExtra(MEAL_STR, meal.strMeal)
                intent.putExtra(MEAL_THUMB, meal.strMealThumb)
                startActivity(intent)
            }

        })

        myAdapter.onItemClicked(object : CategoriesRecyclerAdapter.OnItemCategoryClicked {
            override fun onClickListener(category: MealCategory) {
                val intent = Intent(activity, MealActivity::class.java)
                intent.putExtra(CATEGORY_NAME, category.strCategory)
                startActivity(intent)
            }

        })

        mostPopularFoodAdapter.setOnLongCLickListener(object : OnLongItemClick {
            override fun onItemLongClick(meal: Meal) {
                detailMvvm.getMealByIdBottomSheet(meal.idMeal)
            }

        })

        detailMvvm.observeMealBottomSheet()
            .observe(viewLifecycleOwner
            ) { t ->
                val bottomSheetFragment = MealBottomDialog()
                val b = Bundle()
                b.putString(CATEGORY_NAME, t!![0].strCategory)
                b.putString(MEAL_AREA, t[0].strArea)
                b.putString(MEAL_NAME, t[0].strMeal)
                b.putString(MEAL_THUMB, t[0].strMealThumb)
                b.putString(MEAL_ID, t[0].idMeal)

                bottomSheetFragment.arguments = b

                bottomSheetFragment.show(childFragmentManager, "BottomSheetDialog")
            }


        // on search icon click
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }


    private fun showLoadingCase() {
        binding.apply {
            header.visibility = View.INVISIBLE

            tvOverPupItems.visibility = View.INVISIBLE
            recViewMealsPopular.visibility = View.INVISIBLE
            tvCategory.visibility = View.INVISIBLE
            categoryCard.visibility = View.INVISIBLE
            showProgressDialog()
        }
    }

    private fun cancelLoadingCase() {
        binding.apply {
            header.visibility = View.VISIBLE
            tvOverPupItems.visibility = View.VISIBLE
            recViewMealsPopular.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE
            categoryCard.visibility = View.VISIBLE
            hideProgressDialog()
        }
    }

    private fun showProgressDialog() {
        val binding = DialogProgressBinding.inflate(LayoutInflater.from(requireContext()))
        mProgressDialog = Dialog(requireContext())
        mProgressDialog.setContentView(binding.root)
        mProgressDialog.show()
    }

    private fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    private fun setMealsByCategoryAdapter(meals: List<Meal>) {
        mostPopularFoodAdapter.setMealList(meals)
    }

    private fun setCategoryAdapter(categories: List<MealCategory>) {
        myAdapter.setCategoryList(categories)
    }

    private fun prepareCategoryRecyclerView() {
        binding.recyclerView.apply {
            adapter = myAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun preparePopularMeals() {
        binding.recViewMealsPopular.apply {
            adapter = mostPopularFoodAdapter
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        }
    }

}