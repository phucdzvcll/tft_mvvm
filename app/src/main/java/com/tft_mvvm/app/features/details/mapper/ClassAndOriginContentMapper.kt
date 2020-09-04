package com.tft_mvvm.app.features.details.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.details.model.ChampDetailsModel
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity

class ClassAndOriginContentMapper(private val champOfChampDetailsMapper: ChampOfChampDetailsMapper) :
    Mapper<ClassAndOriginListEntity.ClassAndOrigin, ChampDetailsModel.ClassAndOriginContent>() {
    override fun map(input: ClassAndOriginListEntity.ClassAndOrigin): ChampDetailsModel.ClassAndOriginContent {
        return ChampDetailsModel.ClassAndOriginContent(
            content = input.content,
            listChamp = champOfChampDetailsMapper.mapList(input.champEntity.champs),
            bonus = input.bonus.split(","),
            classOrOriginName = input.classOrOriginName
        )
    }
}