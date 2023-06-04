package com.example.mealsApp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealsApp.R
import com.example.mealsApp.databinding.FavMealCardBinding

import com.example.mealsApp.data.aboutMealData.MealDatabase

class FavoriteMealsRecyclerAdapter :
    RecyclerView.Adapter<FavoriteMealsRecyclerAdapter.FavoriteViewHolder>() {
    private var favoriteMeals: List<MealDatabase> = ArrayList()
    private lateinit var onFavoriteClickListener: OnFavoriteClickListener
    private lateinit var onFavoriteLongClickListener: OnFavoriteLongClickListener

    fun setFavoriteMealsList(favoriteMeals: List<MealDatabase>) {
        this.favoriteMeals = favoriteMeals
        notifyDataSetChanged()
    }

    fun getMelaByPosition(position: Int):MealDatabase{
        return favoriteMeals[position]
    }


    fun setOnFavoriteMealClickListener(onFavoriteClickListener: OnFavoriteClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener
    }

    fun setOnFavoriteLongClickListener(onFavoriteLongClickListener: OnFavoriteLongClickListener) {
        this.onFavoriteLongClickListener = onFavoriteLongClickListener
    }

    class FavoriteViewHolder(val binding: FavMealCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FavMealCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.binding.apply {
            tvFavMealName.text = favoriteMeals[position].mealName
            Glide.with(holder.itemView)
                .load(favoriteMeals[position].mealThumb)
                .error(R.drawable.meal)
                .into(imgFavMeal)
        }

        holder.itemView.setOnClickListener {
            onFavoriteClickListener.onFavoriteClick(favoriteMeals[position])
        }

        holder.itemView.setOnLongClickListener {
            onFavoriteLongClickListener.onFavoriteLongCLick(favoriteMeals[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return favoriteMeals.size
    }

    interface OnFavoriteClickListener {
        fun onFavoriteClick(meal: MealDatabase)
    }

    interface OnFavoriteLongClickListener {
        fun onFavoriteLongCLick(meal: MealDatabase)
    }
}