package com.cpinto.gamecatalog.application.activity.games.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.BR
import com.cpinto.gamecatalog.application.activity.games.viewmodel.MainGamesViewModel

class HorizontalGamesHolder(
    private val binding: ViewDataBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(position: Int, viewModel: MainGamesViewModel) {
        binding.setVariable(BR.position, position)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }
}