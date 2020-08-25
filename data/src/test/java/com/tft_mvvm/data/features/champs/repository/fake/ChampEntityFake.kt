package com.tft_mvvm.data.features.champs.repository.fake

import com.tft_mvvm.domain.features.model.ChampListEntity

object ChampEntityFake {

    fun provideChampEntityList(number: Int) = mutableListOf<ChampListEntity.Champ>().apply {
        repeat(number) { index -> add(
            provideChampEntity(
                index
            )
        ) }
    }.toList()

    fun provideChampEntity(index: Int = 1) = ChampListEntity.Champ(
        name = "name$index",
        id = "123$index",
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

    fun provideChampEntityListByName(number: Int, name: String) =
        mutableListOf<ChampListEntity.Champ>().apply {
            repeat(number) { add(
                provideChampEntityByName(
                    name
                )
            ) }
        }.toList()

    fun provideChampEntityByName(name: String = "1") = ChampListEntity.Champ(
        name = "name$name",
        id = "123$name",
        originAndClassName = listOf("class", name),
        cost = "1$name",
        activated = "activated $name",
        linkChampCover = "http://linkchamp$name.google/",
        linkSkillAvatar = "http://linkskill$name.google/",
        skillName = "skill $name",
        linkImg = "http://linkimg$name.google/",
        rankChamp = "rank$name",
        suitableItem = listOf(),
        star = "1"
    )

    fun provideListChampEntityForMapListTeam(number: Int) =
        mutableListOf<ChampListEntity.Champ>().apply {
            repeat(number) { index -> add(
                provideChampEntityForMapListTeam(
                    index
                )
            ) }
        }.toList()

    fun provideChampEntityForMapListTeam(index: Int = 1) = ChampListEntity.Champ(
        name = "name$index",
        id = "123$index",
        originAndClassName = listOf("origin$index", "class$index"),
        star = "1",
        cost = "1$index",
        activated = "activated $index",
        linkChampCover = "http://linkchamp$index.google/",
        linkSkillAvatar = "http://linkskill$index.google/",
        skillName = "skill $index",
        linkImg = "http://linkimg$index.google/",
        rankChamp = "rank$index",
        suitableItem = ItemEntityFake.provideListItemEntity(2)
    )

    fun provideChampEntityThreeStar(index: Int = 1) = ChampListEntity.Champ(
        name = "name$index",
        id = "123$index",
        originAndClassName = listOf("origin$index", "class$index"),
        star = "3",
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