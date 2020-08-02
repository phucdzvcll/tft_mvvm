package com.tft_mvvm.app.mapper

import com.example.common_jvm.extension.nullable.defaultEmpty
import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.champ.model.TeamBuilder
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.model.TeamBuilderListEntity

class TeamBuilderMapper(
    private val champMapper: ChampMapper
) : Mapper<TeamBuilderListEntity.TeamsBuilder, TeamBuilder>() {
    override fun map(input: TeamBuilderListEntity.TeamsBuilder): TeamBuilder {
        return TeamBuilder(
            name = input.name,
            champs = champMapper.mapList(input.champs.champs)
        )
    }
}