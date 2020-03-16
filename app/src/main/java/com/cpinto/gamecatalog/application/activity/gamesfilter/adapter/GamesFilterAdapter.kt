package com.cpinto.gamecatalog.application.activity.gamesfilter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.gamesfilter.holders.*
import com.cpinto.gamecatalog.application.activity.gamesfilter.viewmodel.GamesFilterViewModel

class GamesFilterAdapter(val context: Context, val viewModel: GamesFilterViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface SectionClickListener {
        fun onSectionClickListener(position: Int)
    }

    companion object {
        const val SEPARATOR = 0
        const val GAME_PROP = 1
        const val GAME_PRICE = 2
        const val GAME_RATING = 3
        const val GAME_UNIVERSE = 4
    }

    val data: MutableList<GamesFilterHolderData> = mutableListOf()
    fun setData(arrSections: MutableList<GamesFilterHolderData>) {
        data.clear()
        data.addAll(arrSections)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            GAME_PROP -> createGamePropHolder(layoutInflater, parent)
            GAME_RATING -> createGameRatingHolder(layoutInflater, parent)
            GAME_UNIVERSE -> createGameCategoryHolder(layoutInflater, parent)
            else -> createSeparatorHolder(layoutInflater, parent)
        }
    }

    override fun getItemViewType(position: Int): Int = data[position].type

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GameFilterPropsHolder -> holder.bind(viewModel, position)
            is GamesRatingHolder -> holder.bind(viewModel, position)
            is GameFilterCategoryHolder -> holder.bind(viewModel, position)
        }
    }

    private fun createGamePropHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): GameFilterPropsHolder {
        val view: ViewDataBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.game_filter_prop_layout,
            parent,
            false
        )
        return GameFilterPropsHolder(view)
    }

    private fun createSeparatorHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): GamesFilterSeparatorHolder {
        val view: View = layoutInflater.inflate(
            R.layout.game_filter_separator_layout,
            parent,
            false
        )
        return GamesFilterSeparatorHolder(view)
    }

    private fun createGameRatingHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): GamesRatingHolder {
        val view: ViewDataBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.game_filter_rating_layout,
            parent,
            false
        )
        return GamesRatingHolder(view)
    }

    private fun createGameCategoryHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): GameFilterCategoryHolder {
        val view: ViewDataBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.game_filter_category_layout,
            parent,
            false
        )
        return GameFilterCategoryHolder(view)
    }


}