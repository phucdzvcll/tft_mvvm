package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.extension.nullable.defaultEmpty
import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.features.champs.model.TeamListResponse
import com.tft_mvvm.data.local.model.TeamListDBO
import com.tft_mvvm.domain.features.champs.model.TeamListEntity

class TeamDaoEntityMapper : Mapper<TeamListResponse?, TeamListDBO>() {
    override fun map(input: TeamListResponse?): TeamListDBO {
        val teamDBO = input?.feedTeam?.team?.filterNotNull().defaultEmpty().map { teamDBO ->
            TeamListDBO.TeamDBO(
                name = teamDBO.name?.value.defaultEmpty(),
                id = teamDBO.id?.value.defaultEmpty(),
                listId = teamDBO.listID?.value.defaultEmpty()
            )
        }
        return TeamListDBO(teamDBOs = teamDBO)
    }
}