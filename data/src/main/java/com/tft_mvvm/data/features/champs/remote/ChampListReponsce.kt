package com.tft_mvvm.data.features.champs.remote
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
    @SerializedName("\$t") val value : String?
)

data class Champ (
    @SerializedName("gsx\$name")
        val name: GsxName?,
    @SerializedName("gsx\$linkimg")
        val linkImg : GsxName?,
    @SerializedName("gsx\$cost")
        val cost : GsxName?,
    @SerializedName("gsx\$originandclass")
        val originAndClassName : GsxName?,
    @SerializedName("gsx\$id")
        val id : GsxName?,
    @SerializedName("gsx\$skillname")
        val skillName : GsxName?,
    @SerializedName("gsx\$linkskillavatar")
        val linkSkillAvatar : GsxName?,
    @SerializedName("gsx\$activated")
        val activated : GsxName?,
    @SerializedName("gsx\$rankchamp")
        val rankChamp:GsxName?,
    @SerializedName("gsx\$suitableitem")
        val suitableItem:GsxName?,
    @SerializedName("gsx\$linkchampcover")
        val linkChampCover : GsxName?
)


