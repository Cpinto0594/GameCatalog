package com.cpinto.gamecatalog.application.activity.filteredgames.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.BR
import com.cpinto.gamecatalog.application.activity.filteredgames.viewmodel.FilteredGamesViewModel

class FilteredGamesCardHolder(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(position: Int, viewModel: FilteredGamesViewModel) {
        binding.setVariable(BR.position, position)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }
}