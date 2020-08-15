package com.tft_mvvm.data.features.champs.model

import com.google.gson.annotations.SerializedName

data class FeedTeam(
    @SerializedName("entry")
    val team: List<Team?>
)

data class TeamListResponse(
    @SerializedName("feed")
    val feedTeam: FeedTeam
)

data class GsxNameTeam(
    @SerializedName("\$t") val value: String?
)

data class Team(
    @SerializedName("gsx\$name")
    val name: GsxNameTeam?,
    @SerializedName("gsx\$id")
    val id: GsxNameTeam?,
    @SerializedName("gsx\$idchampmain")
    val idChampMain: GsxNameTeam?,
    @SerializedName("gsx\$listiditem")
    val idItemSuitable: GsxNameTeam?,
    @SerializedName("gsx\$listid")
    val listID: GsxNameTeam?

)