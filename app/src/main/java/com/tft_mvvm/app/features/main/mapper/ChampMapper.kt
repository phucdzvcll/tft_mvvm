package com.tft_mvvm.app.features.main.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.main.model.ClassAndOriginContent
import com.tft_mvvm.domain.features.model.ChampListEntity

class ChampMapper: Mapper<ChampListEntity.Champ, ClassAndOriginContent.Champ>() {
    override fun map(input: ChampListEntity.Champ): ClassAndOriginContent.Champ {
        return ClassAndOriginContent.Champ(
            imgUrl = input.linkImg,
            cost = input.cost,
            id = input.id,
            classAndOriginName = input.originAndClassName
        )
    }
}