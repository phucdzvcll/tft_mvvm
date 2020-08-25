package com.tft_mvvm.data.features.champs.mapper

import com.example.common_jvm.extension.nullable.defaultEmpty
import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.data.features.champs.remote.ClassAndOriginListResponse
import com.tft_mvvm.data.local.model.ClassAndOriginListDBO

class ClassAndOriginDaoEntityMapper : Mapper<ClassAndOriginListResponse?, ClassAndOriginListDBO>() {
    override fun map(input: ClassAndOriginListResponse?): ClassAndOriginListDBO {
        val classAndOriginListDBO =
            input?.feedClassAndOrigin?.classAndOrigins?.filterNotNull().defaultEmpty()
                .map { classAndOrigin ->
                    ClassAndOriginListDBO.ClassAndOrigin(
                        classOrOriginName = classAndOrigin.classOrOriginName?.value.defaultEmpty(),
                        bonus = classAndOrigin.bonus?.value.defaultEmpty(),
                        content = classAndOrigin.content?.value.defaultEmpty()
                    )
                }
        return ClassAndOriginListDBO(classAndOrigins = classAndOriginListDBO)
    }
}