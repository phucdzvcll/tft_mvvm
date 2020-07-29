package com.tft_mvvm.data.features.champs.model
import com.google.gson.annotations.SerializedName

data class Feed(
    @SerializedName("entry")
    val champs: List<Champ?>
)

data class ChampListResponse(
    @SerializedName("feed")
    val feed: Feed
)

data class GsxName (
    @SerializedName("\$t") val value : String
)

data class Champ (
    @SerializedName("gsx\$name")
        val name: GsxName,
    @SerializedName("gsx\$linkimg")
        val linkimg : GsxName,
    @SerializedName("gsx\$coat")
        val coat : GsxName
)


