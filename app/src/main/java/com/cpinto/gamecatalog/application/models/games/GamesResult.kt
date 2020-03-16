package com.cpinto.gamecatalog.application.models.games

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GamesResult(
    @SerializedName("results") @Expose var results: MutableList<Games> = mutableListOf()
) : Parcelable