package com.tft_mvvm.app.mapper

import com.example.common_jvm.mapper.Mapper
import com.tft_mvvm.app.ui.adapter.ItemDetailsViewHolderModel
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity

class ClassOrOriginMapper() :
    Mapper<ClassAndOriginListEntity.ClassAndOrigin, ItemDetailsViewHolderModel.ClassOrOrigin>() {
    override fun map(input: ClassAndOriginListEntity.ClassAndOrigin): ItemDetailsViewHolderModel.ClassOrOrigin {
        return ItemDetailsViewHolderModel.ClassOrOrigin(
            classOrOriginName = input.classOrOriginName,
            bonus = input.bonus.split(","),
            content = input.content
        )
    }
}