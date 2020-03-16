package com.cpinto.gamecatalog.application.activity.filteredgames.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.filteredgames.viewmodel.FilteredGamesViewModel
import com.cpinto.gamecatalog.application.models.games.Games
import com.cpinto.gamecatalog.databinding.FilteredgameCardItemBinding
import com.cpinto.gamecatalog.databinding.GameCardItemBinding

class FilteredGamesAdapter(val viewModel: FilteredGamesViewModel) :
    RecyclerView.Adapter<FilteredGamesCardHolder>() {

    val data: MutableList<Games> = mutableListOf()

    fun setData(arrData: MutableList<Games>) {
        data.clear()
        data.addAll(arrData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilteredGamesCardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FilteredgameCardItemBinding>(
            layoutInflater,
            R.layout.filteredgame_card_item,
            parent,
            false
        )
        return FilteredGamesCardHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FilteredGamesCardHolder, position: Int) {
        holder.bind(position, viewModel)
    }
}