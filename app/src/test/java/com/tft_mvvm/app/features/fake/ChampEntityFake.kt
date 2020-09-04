package com.tft_mvvm.app.features.fake

import com.tft_mvvm.domain.features.model.ChampListEntity

object ChampEntityFake {

    fun provideChampEntityList(number: Int) = mutableListOf<ChampListEntity.Champ>().apply {
        repeat(number) { id ->
            add(
                provideChampEntity(
                    id.toString()
                )
            )
        }
    }.toList()

    fun provideChampEntity(id: String = "1") = ChampListEntity.Champ(
        name = "name$id",
        id = "id_$id",
        originAndClassName = listOf("origin$id", "class$id"),
        star = "1",
        cost = "1$id",
        activated = "activated $id",
        linkChampCover = "http://linkchamp$id.google/",
        linkSkillAvatar = "http://linkskill$id.google/",
        skillName = "skill $id",
        linkImg = "http://linkimg$id.google/",
        rankChamp = "rank$id",
        suitableItem = listOf()
    )

    fun provideChampEntityListHaveItem(number: Int) = mutableListOf<ChampListEntity.Champ>().apply {
        repeat(number) { index ->
            add(
                provideChampEntityHaveItem(
                    index
                )
            )
        }
    }.toList()

    private fun provideChampEntityHaveItem(index: Int = 1) = ChampListEntity.Champ(
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
        suitableItem = listOf(
            ItemEntityFake.provideItemEntity(index + 1),
            ItemEntityFake.provideItemEntity(index + 2),
            ItemEntityFake.provideItemEntity(index + 3)
        )
    )

    fun provideChampEntityListByOrigin(number: Int, index: Int) =
        mutableListOf<ChampListEntity.Champ>().apply {
            repeat(number) { add(provideChampEntityByOrigin(index)) }
        }.toList()

    private fun provideChampEntityByOrigin(index: Int = 1) = ChampListEntity.Champ(
        name = "name$index",
        id = "id_$index",
        originAndClassName = listOf("origin$index", "class"),
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

    fun provideChampEntityListByClass(number: Int, index: Int) =
        mutableListOf<ChampListEntity.Champ>().apply {
            repeat(number) { add(provideChampEntityByClass(index)) }
        }.toList()

    private fun provideChampEntityByClass(index: Int = 1) = ChampListEntity.Champ(
        name = "name$index",
        id = "id_$index",
        originAndClassName = listOf("origin", "class$index"),
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