package com.tft_mvvm.data.features.champs.model

import com.google.gson.annotations.SerializedName

data class FeedClassAndOrigin(
    @SerializedName("entry")
    val classAndOrigins: List<ClassAndOrigin?>
)

data class ClassAndOriginListResponse(
    @SerializedName("feed")
    val feedClassAndOrigin: FeedClassAndOrigin
)

data class GsxNameClassAndOrigin(
    @SerializedName("\$t") val value: String?
)

data class ClassAndOrigin(
    @SerializedName("gsx\$originorclass")
    val classOrOriginName: GsxNameClassAndOrigin?,
    @SerializedName("gsx\$content")
    val content: GsxNameClassAndOrigin?
)