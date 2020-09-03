package com.tft_mvvm.domain.features.model

data class TeamBuilderListEntity(val teamBuilders: List<TeamsBuilder>) {
    data class TeamsBuilder(
        val name:String,
        val champEntity: ChampListEntity
    )
}
