package com.tft_mvvm.data.fake

import com.tft_mvvm.data.features.champs.model.ClassAndOrigin
import com.tft_mvvm.data.features.champs.model.GsxNameClassAndOrigin

object ClassAndOriginListResponseFake {

    fun provideListClassAndOriginEmpty(number: Int) = mutableListOf<ClassAndOrigin>().apply {
        repeat(number) { add(classAndOriginEmpty()) }
    }

    fun classAndOriginEmpty(index: Int = 1) = ClassAndOrigin(
        classOrOriginName = null,
        bonus = null,
        content = null
    )

    fun provideListClassAndOrigin(number: Int) = mutableListOf<ClassAndOrigin>().apply {
        repeat(number) { index -> add(classAndOrigin(index)) }
    }

    fun classAndOrigin(index: Int = 1) = ClassAndOrigin(
        classOrOriginName = GsxNameClassAndOrigin("classOrOriginName$index"),
        bonus = GsxNameClassAndOrigin("bonus$index"),
        content = GsxNameClassAndOrigin("content$index")
    )
}