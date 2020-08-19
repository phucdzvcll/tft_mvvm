package com.tft_mvvm.app.features.details.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.model.ChampDetailsModel
import com.tft_mvvm.domain.features.model.ChampListEntity

class ItemHeaderMapper(private val itemMapper: ItemMapper) : Mapper<ChampListEntity.Champ,ChampDetailsModel.HeaderModel>() {
    override fun map(input: ChampListEntity.Champ): ChampDetailsModel.HeaderModel {
        return ChampDetailsModel.HeaderModel(
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