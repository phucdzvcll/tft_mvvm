package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.local.model.ClassAndOriginListDBO
import com.tft_mvvm.domain.features.champs.model.ClassAndOriginListEntity

class ClassAndOriginListMapper :
    Mapper<ClassAndOriginListDBO.ClassAndOrigin, ClassAndOriginListEntity.ClassAndOrigin>() {
    override fun map(input: ClassAndOriginListDBO.ClassAndOrigin): ClassAndOriginListEntity.ClassAndOrigin {
        return ClassAndOriginListEntity.ClassAndOrigin(
            classOrOriginName = input.classOrOriginName,
            content = input.content
        )
    }
}