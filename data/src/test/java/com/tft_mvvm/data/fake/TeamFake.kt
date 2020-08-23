package com.tft_mvvm.data.fake

import com.tft_mvvm.domain.features.model.TeamListEntity

object TeamFake {

    fun provideListTeam(number: Int): TeamListEntity {
        val listTeam = mutableListOf<TeamListEntity.Team>().apply {
            repeat(number) { index -> add(provideTeam(index)) }
        }
        return TeamListEntity(listTeam)
    }

    private fun provideTeam(index: Int = 1) = TeamListEntity.Team(
        nameTeam = "name$index",
        listIdSuitable = listOf("item0,item1", "item0,item1"),
        idTeam = "$index",
        listIdChamp = listOf("1230", "1231", "1232", "1233"),
        listIdChampMain = listOf("1230", "1231"),
        listIdChampThreeStar = listOf("1232", "1233")
    )
}