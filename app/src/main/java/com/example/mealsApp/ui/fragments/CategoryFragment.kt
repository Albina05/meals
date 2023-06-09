package com.example.mealsApp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mealsApp.R
import com.example.mealsApp.adapters.CategoriesRecyclerAdapter
import com.example.mealsApp.data.aboutMealData.MealCategory
import com.example.mealsApp.databinding.FragmentCategoryBinding
import com.example.mealsApp.mvvm.CategoryMVVM
import com.example.mealsApp.ui.activities.MealActivity
import com.example.mealsApp.util.Constants.Companion.CATEGORY_NAME


class CategoryFragment : Fragment(R.layout.fragment_category) {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var myAdapter:CategoriesRecyclerAdapter
    private lateinit var categoryMvvm:CategoryMVVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAdapter = CategoriesRecyclerAdapter()
        categoryMvvm = ViewModelProviders.of(this)[CategoryMVVM::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeCategories()
        onCategoryClick()
    }

    private fun onCategoryClick() {
       myAdapter.onItemClicked(object : CategoriesRecyclerAdapter.OnItemCategoryClicked{
           override fun onClickListener(category: MealCategory) {
               val intent = Intent(context, MealActivity::class.java)
               intent.putExtra(CATEGORY_NAME,category.strCategory)
               startActivity(intent)
           }
       })
    }

    private fun observeCategories() {
        categoryMvvm.observeCategories().observe(viewLifecycleOwner,object : Observer<List<MealCategory>>{
            override fun onChanged(t: List<MealCategory>?) {
                myAdapter.setCategoryList(t!!)
            }

        })
    }

    private fun prepareRecyclerView() {
        binding.favoriteRecyclerView.apply {
            adapter = myAdapter
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        }
    }


}