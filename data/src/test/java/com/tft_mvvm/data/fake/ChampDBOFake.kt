package com.tft_mvvm.data.fake

import com.tft_mvvm.data.local.model.ChampListDBO

object ChampDBOFake {

    fun provideChampDBOList(number: Int) = mutableListOf<ChampListDBO.ChampDBO>().apply {
        repeat(number) { index -> add(provideChampDbo(index)) }
    }.toList()

    fun provideChampDbo(index: Int = 1) = ChampListDBO.ChampDBO(
        name = "name$index",
        id = "123$index",
        originAndClassName = "origin$index,class$index",
        cost = "1$index",
        activated = "activated $index",
        linkChampCover = "http://linkchamp$index.google/",
        linkSkillAvatar = "http://linkskill$index.google/",
        skillName = "skill $index",
        linkImg = "http://linkimg$index.google/",
        rankChamp = "rank$index",
        suitableItem = "item$index,item$index"
    )

    fun provideChampDBOListEmpty(number: Int) = mutableListOf<ChampListDBO.ChampDBO>().apply {
        repeat(number) { add(provideChampDboEmpty()) }
    }.toList()

    fun provideChampDboEmpty() = ChampListDBO.ChampDBO(
        name = "",
        linkSkillAvatar = "",
        id = "",
        originAndClassName = "",
        cost = "",
        activated = "",
        linkChampCover = "",
        skillName = "",
        linkImg = "",
        rankChamp = "",
        suitableItem = ""
    )
}