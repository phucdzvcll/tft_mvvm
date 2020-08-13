package com.tft_mvvm.app.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.DetailsChampActivity
import com.tft_mvvm.domain.features.model.ChampListEntity

class DetailsChampMapper(

) : Mapper<ChampListEntity.Champ, DetailsChampActivity.DetailsChamp>() {

    override fun map(input: ChampListEntity.Champ): DetailsChampActivity.DetailsChamp {
        return DetailsChampActivity.DetailsChamp(
            id = input.id,
            name = input.name,
            activated = input.activated,
            skillName = input.skillName,
            origin = input.origin,
            classs = input.classs,
            linkAvatarSkill = input.linkSkillAvatar,
            linkCover = input.linkChampCover,
            //listIdItem = itemMapper.mapList(input.suitableItem),
            cost = input.cost
        )
    }
}