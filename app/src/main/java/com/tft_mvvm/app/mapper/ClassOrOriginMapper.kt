package com.tft_mvvm.app.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.features.champ.model.ClassOrOrigin
import com.tft_mvvm.domain.features.champs.model.ClassAndOriginListEntity

class ClassOrOriginMapper() : Mapper<ClassAndOriginListEntity.ClassAndOrigin, ClassOrOrigin>() {
    override fun map(input: ClassAndOriginListEntity.ClassAndOrigin): ClassOrOrigin {
        return ClassOrOrigin(
            classOrOriginName = input.classOrOriginName,
            content = input.content
        )
    }
}