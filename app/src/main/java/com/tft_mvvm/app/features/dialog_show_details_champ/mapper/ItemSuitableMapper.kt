package com.tft_mvvm.app.features.dialog_show_details_champ.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.dialog_show_details_champ.model.ChampDialogModel
import com.tft_mvvm.domain.features.model.ChampListEntity

class ItemSuitableMapper : Mapper<ChampListEntity.Champ.Item, ChampDialogModel.Item>() {
    override fun map(input: ChampListEntity.Champ.Item): ChampDialogModel.Item {
        return ChampDialogModel.Item(
            id = input.itemId,
            itemAvatar = input.itemAvatar,
            itemName = input.itemName
        )
    }
}