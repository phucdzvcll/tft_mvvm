package com.tft_mvvm.app.mapper

import com.example.common_jvm.mapper.Mapper

import com.tft_mvvm.data.features.champs.model.Team
import com.tft_mvvm.domain.features.model.TeamListEntity

class TeamMapper : Mapper<TeamListEntity.Team, Team?>() {
    override fun map(input: TeamListEntity.Team): Team? {
        return null
//        Team(
//            name = input.name,
//            id = input.id,
//            listId = input.listId
//        )
    }
}