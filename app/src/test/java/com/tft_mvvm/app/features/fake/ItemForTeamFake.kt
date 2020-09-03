package com.tft_mvvm.app.features.fake

import com.tft_mvvm.app.features.main.adapter.AdapterShowChampInTeamBuilder

object ItemForTeamFake {
        fun provideItem(index: Int = 1) = AdapterShowChampInTeamBuilder.Champ.Item(
            itemName = "itemName$index",
            id = "item$index",
            itemAvatar = "itemAvatar$index"
        )
}