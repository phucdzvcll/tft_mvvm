package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.extension.nullable.defaultEmpty
import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.features.champs.model.TeamListResponse
import com.tft_mvvm.data.local.model.TeamListDBO

class TeamDaoEntityMapper : Mapper<TeamListResponse?, TeamListDBO>() {
    override fun map(input: TeamListResponse?): TeamListDBO {
        val teamDBO = input?.feedTeam?.team?.filterNotNull().defaultEmpty().map { teamDBO ->
            TeamListDBO.TeamDBO(
                nameTeam = teamDBO.nameTeam?.value.defaultEmpty(),
                idTeam = teamDBO.idTeam?.value.defaultEmpty(),
                idChampMain = teamDBO.idChampMain?.value.defaultEmpty(),
                listIdThreeStar = teamDBO.listIdThreeStar?.value.defaultEmpty(),
                idItemSuitable = teamDBO.idItemSuitable?.value.defaultEmpty(),
                listIdChamp = teamDBO.listIdChamp?.value.defaultEmpty()
            )
        }
        return TeamListDBO(teamDBOs = teamDBO)
    }
}