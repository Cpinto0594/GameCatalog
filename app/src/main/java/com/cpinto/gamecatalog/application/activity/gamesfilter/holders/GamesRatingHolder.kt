package com.cpinto.gamecatalog.application.activity.gamesfilter.holders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.BR
import com.cpinto.gamecatalog.application.activity.gamesfilter.viewmodel.GamesFilterViewModel

class GamesRatingHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(viewModel: GamesFilterViewModel, position: Int) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.position, position)
        binding.executePendingBindings()
    }
}