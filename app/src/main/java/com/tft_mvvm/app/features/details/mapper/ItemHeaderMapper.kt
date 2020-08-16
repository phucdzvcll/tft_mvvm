package com.tft_mvvm.app.features.details.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.model.HeaderViewHolderModel
import com.tft_mvvm.domain.features.model.ChampListEntity

class ItemHeaderMapper(private val itemMapper: ItemMapper) : Mapper<ChampListEntity.Champ,HeaderViewHolderModel>() {
    override fun map(input: ChampListEntity.Champ): HeaderViewHolderModel {
        return HeaderViewHolderModel(
            name = input.name,
            linkChampCover = input.linkChampCover,
            activated = input.activated,
            cost = input.cost,
            linkAvatarSkill = input.linkSkillAvatar,
            listSuitableItem = itemMapper.mapList(input.suitableItem),
            nameSkill = input.skillName
        )
    }
}