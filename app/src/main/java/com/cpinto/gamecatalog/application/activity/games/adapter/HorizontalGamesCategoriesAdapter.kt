package com.cpinto.gamecatalog.application.activity.games.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.games.viewmodel.MainGamesViewModel
import com.cpinto.gamecatalog.application.models.games.GameCategories

class HorizontalGamesCategoriesAdapter(
    val context: Context,
    val viewModel: MainGamesViewModel
) :
    RecyclerView.Adapter<HorizontalGameCategoryHolder>() {

    interface SelectedCategoryListener {
        fun onSelectCategory(position: Int)
    }

    val data: MutableList<GameCategories> = mutableListOf()

    fun setData(arrData: MutableList<GameCategories>) {
        data.clear()
        data.addAll(arrData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalGameCategoryHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.game_category_item, parent, false)
        return HorizontalGameCategoryHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HorizontalGameCategoryHolder, position: Int) {
        holder.bind(viewModel, position)
    }
}