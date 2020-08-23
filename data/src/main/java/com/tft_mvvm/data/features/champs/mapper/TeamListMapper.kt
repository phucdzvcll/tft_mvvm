package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.local.model.TeamListDBO
import com.tft_mvvm.domain.features.model.TeamListEntity

class TeamListMapper : Mapper<TeamListDBO.TeamDBO, TeamListEntity.Team>() {
    override fun map(input: TeamListDBO.TeamDBO): TeamListEntity.Team {
        return TeamListEntity.Team(
            nameTeam = input.nameTeam,
            idTeam = input.idTeam,
            listIdChampMain = input.idChampMain.split(","),
            listIdChampThreeStar = (input.listStar).split(","),
            listIdSuitable = input.idItemSuitable.split("/"),
            listIdChamp = input.listIdChamp.split(",")
        )
    }
}