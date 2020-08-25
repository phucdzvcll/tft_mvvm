package com.tft_mvvm.data.features.champs.remote

import com.google.gson.annotations.SerializedName

data class FeedItem(
    @SerializedName("entry")
    val items: List<Item?>
)

data class ItemListResponse(
    @SerializedName("feed")
    val feedItem: FeedItem
)

data class GsxNameItem(
    @SerializedName("\$t") val value: String?
)

data class Item(
    @SerializedName("gsx\$id")
    val itemId: GsxNameItem?,
    @SerializedName("gsx\$itemname")
    val itemName: GsxNameItem?,
    @SerializedName("gsx\$itemavatar")
    val itemAvatar: GsxNameItem?
)