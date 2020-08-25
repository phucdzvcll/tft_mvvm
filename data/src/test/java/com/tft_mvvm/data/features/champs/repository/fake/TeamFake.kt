package com.tft_mvvm.data.features.champs.repository.fake

import com.tft_mvvm.domain.features.model.TeamListEntity

object TeamFake {

    fun provideListTeam(number: Int): TeamListEntity {
        val listTeam = mutableListOf<TeamListEntity.Team>().apply {
            repeat(number) { index -> add(
                provideTeam(
                    index
                )
            ) }
        }
        return TeamListEntity(listTeam)
    }

    private fun provideTeam(index: Int = 1) = TeamListEntity.Team(
        nameTeam = "name$index",
        listIdSuitable = listOf("item0,item1", "item0,item1", "", ""),
        idTeam = "$index",
        listIdChamp = listOf("1230", "1231", "1232", "1233"),
        listIdChampThreeStar = listOf("1", "1", "3", "3")
    )

    fun provideListTeamListChampEmpty(number: Int): TeamListEntity {
        val listTeam = mutableListOf<TeamListEntity.Team>().apply {
            repeat(number) { index -> add(
                provideTeamListChampEmpty(
                    index
                )
            ) }
        }
        return TeamListEntity(listTeam)
    }

    private fun provideTeamListChampEmpty(index: Int = 1) = TeamListEntity.Team(
        nameTeam = "name$index",
        listIdSuitable = listOf("item0,item1", "item0,item1"),
        idTeam = "$index",
        listIdChamp = listOf(),
        listIdChampThreeStar = listOf()
    )

    private fun provideTeamGetTeamForChamp(index: Int = 1) = TeamListEntity.Team(
        nameTeam = "name$index",
        listIdSuitable = listOf("item0,item1", "item0,item1", "", ""),
        idTeam = "$index",
        listIdChamp = listOf("1230", "1231", "1232", "1233"),
        listIdChampThreeStar = listOf("1", "1", "3", "3")
    )
}