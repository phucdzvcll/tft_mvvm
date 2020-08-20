package com.tft_mvvm.data.fake

import com.tft_mvvm.data.features.champs.model.Champ
import com.tft_mvvm.data.features.champs.model.GsxName

object ChampResponseFake {

    fun provideChampResponseList(number: Int) = mutableListOf<Champ>().apply {
        repeat(number) { index -> add(provideChampResponse(index)) }
    }.toList()

    fun provideChampResponse(index: Int = 1) = Champ(
        name = GsxName(value = "name$index"),
        id = GsxName("123$index"),
        originAndClassName = GsxName("origin$index,class$index"),
        cost = GsxName("1$index"),
        activated = GsxName("activated $index"),
        linkChampCover = GsxName("http://linkchamp$index.google/"),
        linkSkillAvatar = GsxName("http://linkskill$index.google/"),
        skillName = GsxName("skill $index"),
        linkImg = GsxName("http://linkimg$index.google/"),
        rankChamp = GsxName("rank$index"),
        suitableItem = GsxName("item$index,item$index")
    )


    fun provideChampResponseListNull(number: Int) = mutableListOf<Champ>().apply {
        repeat(number) { index -> add(provideChampResponseNull()) }
    }.toList()

    fun provideChampResponseNull() = Champ(
        name = null,
        suitableItem = null,
        rankChamp = null,
        linkImg = null,
        skillName = null,
        linkChampCover = null,
        activated = null,
        cost = null,
        originAndClassName = null,
        id = null,
        linkSkillAvatar = null
    )
}