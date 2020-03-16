package com.cpinto.gamecatalog.application.activity.onboarding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.cpinto.gamecatalog.R
import kotlinx.android.synthetic.main.onboard_template_layout.view.*
import org.jetbrains.anko.backgroundDrawable

/**
 *
 * ViewPagerAdapter
 *
 * This class is for OnBoarding Pager 
 *
 * @author Carlos Pinto
 * @version 1
 * @since 1.0
 *
 */
class ViewPagerAdapter(private val context: Context, private val onPagerButtonListener: OnPagerButtonListener) :
    PagerAdapter() {

    interface OnPagerButtonListener {
        fun onButtonClick(position: Int, lastView: Boolean)
    }

    private val arrImages = arrayListOf(
        R.drawable.link,
        R.drawable.charizard,
        R.drawable.boxer
    )
    private val arrTexts = arrayListOf(
        R.string.onboard_first_text,
        R.string.onboard_second_text,
        R.string.onboard_third_text
    )

    private val arrButtonText = arrayListOf(
        R.string.next,
        R.string.get_started
    )

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.onboard_template_layout, container, false)
        view.onBoardImage.backgroundDrawable =
            ContextCompat.getDrawable(context, arrImages[position])
        view.onBoardText.text = context.getString(arrTexts[position])
        view.onBoardButton.text = context.getText(
            when (position == arrImages.size - 1) {
                true -> arrButtonText[1]
                else -> arrButtonText[0]
            }
        )
        view.onBoardButton.setBackgroundResource(
            when (position == arrImages.size - 1) {
                true -> R.drawable.background_pink_button
                else -> R.drawable.background_outlined_pink_button
            }
        )
        view.onBoardButton.setOnClickListener {
            onPagerButtonListener.onButtonClick(
                position,
                position == ( arrImages.size - 1 )
            )
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, objects: Any) {
        container.removeView(objects as LinearLayout)
    }

    override fun isViewFromObject(view: View, objects: Any): Boolean =
        view == (objects as LinearLayout)

    override fun getCount(): Int = arrImages.size
}