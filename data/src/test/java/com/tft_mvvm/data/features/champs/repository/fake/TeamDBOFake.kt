package com.tft_mvvm.data.features.champs.repository.fake

import com.tft_mvvm.data.local.model.TeamListDBO

object TeamDBOFake {
    fun provideTeamDBOList(number: Int) = mutableListOf<TeamListDBO.TeamDBO>().apply {
        repeat(number) { index -> add(
            provideTeamDBO(
                index
            )
        ) }
    }.toList()

    fun provideTeamDBO(index: Int = 1) = TeamListDBO.TeamDBO(
        nameTeam = "name$index",
        idItemSuitable = "item0,item1/item0,item1//",
        idTeam = "$index",
        listIdChamp = "1230,1231,1232,1233",
        listStar = "1,1,3,3"
    )
}