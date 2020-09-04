package com.tft_mvvm.app.features.main.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.main.model.ClassAndOriginContent
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity

class ClassAndOriginContentMapper(private val champMapper: ChampMapper) :
    Mapper<ClassAndOriginListEntity.ClassAndOrigin, ClassAndOriginContent>() {
    override fun map(input: ClassAndOriginListEntity.ClassAndOrigin): ClassAndOriginContent {
        return ClassAndOriginContent(
            classOrOriginName = input.classOrOriginName,
            imgUrl = input.imgUrl,
            listChamp = champMapper.mapList(input.champEntity.champs)
        )
    }
}