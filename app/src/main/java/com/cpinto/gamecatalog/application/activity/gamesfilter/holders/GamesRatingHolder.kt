package com.cpinto.gamecatalog.application.activity.gamesfilter.holders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.BR
import com.cpinto.gamecatalog.application.activity.gamesfilter.viewmodel.GamesFilterViewModel
import kotlinx.android.synthetic.main.game_filter_rating_layout.view.*

class GamesRatingHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(viewModel: GamesFilterViewModel, position: Int) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.position, position)

        binding.root.checkBoxRatingStar.isChecked = viewModel.sectionStarsIsCheckedState(position)
        binding.root.checkBoxRatingStar.setOnClickListener {
            viewModel.selectRatingValue(position)
        }
        binding.executePendingBindings()
    }
}