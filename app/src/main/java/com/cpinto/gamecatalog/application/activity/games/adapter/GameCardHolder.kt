package com.cpinto.gamecatalog.application.activity.games.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.BR
import com.cpinto.gamecatalog.application.activity.games.viewmodel.MainGamesViewModel

class GameCardHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(viewModel: MainGamesViewModel, parent: Int, child: Int) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.parent, parent)
        binding.setVariable(BR.position, child)
        binding.executePendingBindings()
    }
}