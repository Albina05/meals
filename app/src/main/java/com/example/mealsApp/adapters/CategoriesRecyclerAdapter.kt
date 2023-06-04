package com.example.mealsApp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealsApp.data.aboutMealData.MealCategory
import com.example.mealsApp.databinding.CategoryCardBinding

class CategoriesRecyclerAdapter : RecyclerView.Adapter<CategoriesRecyclerAdapter.CategoryViewHolder>() {
    private var categoryList:List<MealCategory> = ArrayList()
    private lateinit var onItemClick: OnItemCategoryClicked

    fun setCategoryList(categoryList: List<MealCategory>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }


    fun onItemClicked(onItemClick: OnItemCategoryClicked){
        this.onItemClick = onItemClick
    }

    class CategoryViewHolder(val binding: CategoryCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            tvCategoryName.text = categoryList[position].strCategory

            Glide.with(holder.itemView)
                .load(categoryList[position].strCategoryThumb)
                .into(imgCategory)
        }

        holder.itemView.setOnClickListener {
            onItemClick.onClickListener(categoryList[position])
        }


    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    interface OnItemCategoryClicked{
        fun onClickListener(category:MealCategory)
    }

}