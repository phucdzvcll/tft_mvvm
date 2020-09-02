package com.tft_mvvm.app.features.fake

import com.tft_mvvm.domain.features.model.ChampListEntity

object ChampEntityFake {

    fun provideChampEntityList(number: Int) = mutableListOf<ChampListEntity.Champ>().apply {
        repeat(number) { index -> add(
            provideChampEntity(
                index.toString()
            )
        ) }
    }.toList()

    fun provideChampEntity(index: String = "1") = ChampListEntity.Champ(
        name = "name$index",
        id = "id_$index",
        originAndClassName = listOf("origin$index", "class$index"),
        star = "1",
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