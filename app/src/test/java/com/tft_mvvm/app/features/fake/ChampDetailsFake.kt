package com.tft_mvvm.app.features.fake

import com.tft_mvvm.app.features.details.model.ChampDetailsModel
import com.tft_mvvm.domain.features.model.ChampListEntity

object ChampDetailsFake {
}

object ChampForDetailsFake{

    fun provideListChampForDetailsChamp(number: Int) = mutableListOf<ChampDetailsModel.Champ>().apply {
        repeat(number) { index ->
            add(
                provideChampForDetailsChamp(
                    index
                )
            )
        }
    }.toList()

    fun provideChampForDetailsChamp(index:Int = 1) = ChampDetailsModel.Champ(
        name = "name$index",
        id = "id_$index",
        cost = "1$index",
        itemSuitable = listOf(),
        imgUrl = "http://linkimg$index.google/",
        threeStar = "1"
    )

    fun provideListChampForDetailsChampHaveItem(number: Int) = mutableListOf<ChampDetailsModel.Champ>().apply {
        repeat(number) { index ->
            add(
                provideChampForDetailsChampHaveItem(
                    index
                )
            )
        }
    }.toList()

    fun provideChampForDetailsChampHaveItem(index:Int = 1) = ChampDetailsModel.Champ(
        name = "name$index",
        id = "id_$index",
        cost = "1$index",
        itemSuitable = listOf(
            ItemForDetailsFake.provideItemForDetailsChamp(index+1),
            ItemForDetailsFake.provideItemForDetailsChamp(index+2),
            ItemForDetailsFake.provideItemForDetailsChamp(index+3)
        ),
        imgUrl = "http://linkimg$index.google/",
        threeStar = "1"
    )
}
object ItemForDetailsFake{

    fun provideListItemForDetailsChamp(number: Int) = mutableListOf<ChampDetailsModel.Item>().apply {
        repeat(number) { index -> add(
            provideItemForDetailsChamp(index)) }
    }.toList()

    fun provideItemForDetailsChamp(index:Int = 1)=ChampDetailsModel.Item(
        itemName = "itemName$index",
        id = "item$index",
        itemAvatar = "itemAvatar$index"
    )

}