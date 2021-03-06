package com.cpinto.gamecatalog.application.activity.gamesfilter.holders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.BR
import com.cpinto.gamecatalog.application.activity.gamesfilter.viewmodel.GamesFilterViewModel
import kotlinx.android.synthetic.main.game_filter_prop_layout.view.*

class GameFilterCategoryHolder(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        const val DOWNLOADS = 1
        const val DATE_ADDED = 2
        const val PRICE = 3
    }

    fun bind(viewModel: GamesFilterViewModel, position: Int) {
        binding.setVariable(BR.position, position)
        binding.setVariable(BR.viewModel, viewModel)
        binding.root.radioButtonProp.isChecked = viewModel.sectionCategoryIsCheckedState(position)
        binding.root.radioButtonProp.setOnClickListener {
            viewModel.selectCategoryFilter(position)
        }
        binding.executePendingBindings()
    }
}