package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.extension.nullable.defaultEmpty
import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.features.champs.remote.TeamListResponse
import com.tft_mvvm.data.local.model.TeamListDBO

class TeamDaoEntityMapper : Mapper<TeamListResponse?, TeamListDBO>() {
    override fun map(input: TeamListResponse?): TeamListDBO {
        val teamDBO = input?.feedTeam?.team?.filterNotNull().defaultEmpty().map { teamDBO ->
            TeamListDBO.TeamDBO(
                nameTeam = teamDBO.nameTeam?.value.defaultEmpty(),
                idTeam = teamDBO.idTeam?.value.defaultEmpty(),
                listStar = teamDBO.listStar?.value.defaultEmpty(),
                idItemSuitable = teamDBO.idItemSuitable?.value.defaultEmpty(),
                listIdChamp = teamDBO.listIdChamp?.value.defaultEmpty()
            )
        }
        return TeamListDBO(teamDBOs = teamDBO)
    }
}