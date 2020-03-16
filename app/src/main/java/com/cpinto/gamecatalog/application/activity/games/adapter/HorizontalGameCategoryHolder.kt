package com.cpinto.gamecatalog.application.activity.games.adapter

import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cpinto.gamecatalog.BR
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.games.viewmodel.MainGamesViewModel
import kotlinx.android.synthetic.main.game_category_item.view.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.textColor

class HorizontalGameCategoryHolder(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(viewModel: MainGamesViewModel, position: Int) {
        binding.setVariable(BR.position, position)
        binding.setVariable(BR.viewModel, viewModel)

        val selected = viewModel.isCategorySelected(position)
        binding.root.categoryContainer.backgroundDrawable = ContextCompat.getDrawable(
            binding.root.context,
            if (selected) R.drawable.background_pink_button_apply_filter else R.drawable.background_outlined_pink_apply_filter
        )
        binding.root.categoryText.textColor = ContextCompat.getColor(
            binding.root.context,
            if (selected) R.color.white else R.color.colorAccent
        )
        binding.executePendingBindings()
    }
}