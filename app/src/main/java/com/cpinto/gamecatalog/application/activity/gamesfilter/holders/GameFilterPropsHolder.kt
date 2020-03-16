package com.cpinto.gamecatalog.application.activity.gamesfilter.holders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.BR
import com.cpinto.gamecatalog.application.activity.gamesfilter.viewmodel.GamesFilterViewModel
import kotlinx.android.synthetic.main.game_filter_prop_layout.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class GameFilterPropsHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        const val DOWNLOADS = 1
        const val DATE_ADDED = 2
        const val PRICE = 3
    }

    fun bind(viewModel: GamesFilterViewModel, position: Int) {
        binding.setVariable(BR.position, position)
        binding.setVariable(BR.viewModel, viewModel)
        binding.root.radioButtonProp.isChecked = viewModel.sectionPropsIsCheckedState(position)
        binding.root.radioButtonProp.setOnClickListener {
            viewModel.selectPropsSorter(position)
        }
        binding.executePendingBindings()
    }
}