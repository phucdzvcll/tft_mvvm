package com.tft_mvvm.data.fake

import com.tft_mvvm.data.features.champs.remote.GsxNameTeam
import com.tft_mvvm.data.features.champs.remote.Team

object TeamResponseFake {
    fun provideTeamResponseList(number: Int) = mutableListOf<Team>().apply {
        repeat(number) { index -> add(
            provideTeamResponseFake(
                index
            )
        ) }
    }.toList()

    fun provideTeamResponseFake(index: Int = 1) = Team(
        nameTeam = GsxNameTeam("name$index"),
        idItemSuitable = GsxNameTeam("item0,item1/item0,item1//"),
        idTeam = GsxNameTeam("$index"),
        listIdChamp = GsxNameTeam("1230,1231,1232,1233"),
        listStar = GsxNameTeam("1,1,3,3")
    )
}