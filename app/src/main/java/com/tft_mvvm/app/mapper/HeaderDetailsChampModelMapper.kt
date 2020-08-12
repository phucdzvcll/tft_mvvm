package com.tft_mvvm.app.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.ui.adapter.HeaderViewHolderModel
import com.tft_mvvm.domain.features.model.ChampListEntity

class HeaderDetailsChampModelMapper(private val itemMapper: ItemMapper) : Mapper<ChampListEntity.Champ, HeaderViewHolderModel>() {
    override fun map(input: ChampListEntity.Champ): HeaderViewHolderModel {
        return HeaderViewHolderModel(
            nameSkill = input.skillName,
            listSuitableItem = itemMapper.mapList(input.suitableItem).toList(),
            linkChampCover = input.linkChampCover,
            activated = input.activated,
            linkAvatarSkill = input.skillName
        )
    }
}