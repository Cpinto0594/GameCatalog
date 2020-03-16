package com.cpinto.gamecatalog.application.models.games

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Games(
    @SerializedName("objectId") @Expose var id: String = "",
    @SerializedName("name") @Expose var name: String = "",
    @SerializedName("createdAt") @Expose var createdAt: Date,
    @SerializedName("updatedAt") @Expose var updatedAt: Date,
    @SerializedName("price") @Expose var price: String = "",
    @SerializedName("imageURL") @Expose var imageURL: String = "",
    @SerializedName("popular") @Expose var popular: Boolean = false,
    @SerializedName("rating") @Expose var rating: String = "",
    @SerializedName("downloads") @Expose var downloads: String = "",
    @SerializedName("description") @Expose var description: String = "",
    @SerializedName("SKU") @Expose var sku: String = "",
    @SerializedName("universe") @Expose var universe: String = "",
    @SerializedName("kind") @Expose var kind: String = ""
) : Parcelable