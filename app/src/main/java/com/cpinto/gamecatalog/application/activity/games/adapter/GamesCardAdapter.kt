package com.cpinto.gamecatalog.application.activity.games.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.games.viewmodel.MainGamesViewModel
import com.cpinto.gamecatalog.application.models.games.Games

class GamesCardAdapter(
    val viewModel: MainGamesViewModel,
    val parent: Int
) :
    RecyclerView.Adapter<GameCardHolder>() {

    interface GameClickListener {
        fun onGameClick(game: Games)
    }


    private val data: MutableList<Games> = mutableListOf()

    fun setData(arrGames: MutableList<Games>) {
        data.clear()
        data.addAll(arrGames)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.game_card_item,
            parent,
            false
        )
        return GameCardHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: GameCardHolder, position: Int) {
        holder.bind(viewModel, parent, position)
    }
}