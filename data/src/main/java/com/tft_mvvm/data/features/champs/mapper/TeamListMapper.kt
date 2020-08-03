package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.local.model.TeamListDBO
import com.tft_mvvm.domain.features.champs.model.TeamListEntity

class TeamListMapper: Mapper<TeamListDBO.TeamDBO, TeamListEntity.Team>() {
    override fun map(input: TeamListDBO.TeamDBO): TeamListEntity.Team {
        return TeamListEntity.Team(
            name = input.name,
            id = input.id,
            listId = input.listId
        )
    }
}