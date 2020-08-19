package com.tft_mvvm.app.features.details.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.model.ChampDetailsModel
import com.tft_mvvm.domain.features.model.TeamBuilderListEntity

class TeamRecommendForChampMapper(private val champOfChampDetailsMapper: ChampOfChampDetailsMapper) :
    Mapper<TeamBuilderListEntity.TeamsBuilder, ChampDetailsModel.TeamRecommend>() {
    override fun map(input: TeamBuilderListEntity.TeamsBuilder): ChampDetailsModel.TeamRecommend {
        return ChampDetailsModel.TeamRecommend(
            name = input.name,
            listChamp = champOfChampDetailsMapper.mapList(input.champs.champs)
        )
    }
}