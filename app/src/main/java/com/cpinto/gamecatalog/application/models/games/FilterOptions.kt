package com.cpinto.gamecatalog.application.models.games

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterOptions(
    var selectedSortingProperty: String? = null,
    var selectedCategoryFilter: String? = null,
    val selectedStarsFilter: ArrayList<String> = arrayListOf()
) : Parcelable