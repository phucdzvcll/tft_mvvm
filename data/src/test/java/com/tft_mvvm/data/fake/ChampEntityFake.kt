package com.tft_mvvm.data.fake

import com.tft_mvvm.domain.features.model.ChampListEntity

object ChampEntityFake {

    fun provideChampEntityList(number: Int) = mutableListOf<ChampListEntity.Champ>().apply {
        repeat(number){index -> add(provideChampEntity(index))}
    }.toList()

    fun provideChampEntity(index : Int = 1) = ChampListEntity.Champ(
        name = "name $index",
        id = "123$index",
        originAndClassName = listOf("origin$index","class$index"),
        threeStar = false,
        cost = "1$index",
        activated = "activated $index",
        linkChampCover = "http://linkchamp$index.google/",
        linkSkillAvatar = "http://linkskill$index.google/",
        skillName = "skill $index",
        linkImg = "http://linkimg$index.google/",
        rankChamp = "rank$index",
        suitableItem = listOf()

    )
}