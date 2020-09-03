package com.tft_mvvm.app.features.fake

import com.tft_mvvm.app.model.Champ

object ChampFake {

    fun provideListChamp(number : Int) = mutableListOf<Champ>().apply {
        repeat(number){number->add(provideChamp(number.toString()))}
    }

    private fun provideChamp(id: String = "1") = Champ(
        name = "name$id",
        id = "id_$id",
        cost = "1$id",
        imgUrl = "http://linkimg$id.google/",
        rank = "rank$id"
    )
}