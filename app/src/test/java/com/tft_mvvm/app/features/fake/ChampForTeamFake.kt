package com.tft_mvvm.app.features.fake

import com.tft_mvvm.app.features.main.adapter.AdapterShowChampInTeamBuilder

object ChampForTeamFake {

    fun provideListChampForTeam(number: Int) =
        mutableListOf<AdapterShowChampInTeamBuilder.Champ>().apply {
            repeat(number) { index -> add(provideChampForTeam(index)) }
        }

    fun provideChampForTeam(index: Int = 1) = AdapterShowChampInTeamBuilder.Champ(
        name = "name$index",
        id = "id_$index",
        threeStar = "1",
        cost = "1$index",
        imgUrl = "http://linkchamp$index.google/",
        itemSuitable = listOf()
    )
}