package com.tft_mvvm.app.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.features.champs.model.Champ
import com.tft_mvvm.domain.features.model.ChampListEntity

class ChampMapper() : Mapper<ChampListEntity.Champ, Champ?>() {
    override fun map(input: ChampListEntity.Champ): Champ? {
        return null
//        Champ(
//            name = input.name,
//            linkImg = input.linkImg,
//            cost = input.cost,
//            classs = input.classs,
//            origin = input.origin,
//            id = input.id,
//            activated = input.activated,
//            skillName = input.skillName,
//            linkSkilAvatar = input.linkSkillAvatar,
//            rankChamp = input.rankChamp,
//            suitableItem = itemMapper.mapList(input.suitableItem),
//            linkChampCover = input.linkChampCover
//        )
    }
}