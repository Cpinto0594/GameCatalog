package com.cpinto.gamecatalog.application.activity.games.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.games.viewmodel.MainGamesViewModel
import com.cpinto.gamecatalog.application.models.games.GamesAdapterDefinition

class MainGamesAdapter(val context: Context, val viewModel: MainGamesViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val NEW = 1
        const val POPULAR = 2
    }

    private val data: MutableList<GamesAdapterDefinition> = mutableListOf()

    fun setData(arrGames: MutableList<GamesAdapterDefinition>) {
        data.clear()
        data.addAll(arrGames)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = data[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.horizontal_games_holder_layout, parent, false
        )
        return HorizontalGamesHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HorizontalGamesHolder).bind(position, viewModel)
    }
}
