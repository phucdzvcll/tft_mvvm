package com.tft_mvvm.data.fake

import com.tft_mvvm.data.local.model.ClassAndOriginListDBO

object ClassAndOriginDBOFake {

    fun provideListClassAndOriginDBOEmpty(number: Int) =
        mutableListOf<ClassAndOriginListDBO.ClassAndOrigin>().apply {
            repeat(number) { add(provideClassAndOriginDBOEmpty()) }
        }.toList()

    fun provideClassAndOriginDBOEmpty() = ClassAndOriginListDBO.ClassAndOrigin(
        classOrOriginName = "",
        bonus = "",
        content = "",
        id = 0
    )

    fun provideListClassAndOriginDBO(number: Int) =
        mutableListOf<ClassAndOriginListDBO.ClassAndOrigin>().apply {
            repeat(number) { index -> add(provideClassAndOriginDBO(index)) }
        }.toList()

    fun provideClassAndOriginDBO(index: Int = 1) = ClassAndOriginListDBO.ClassAndOrigin(
        classOrOriginName = "classOrOriginName$index",
        bonus = "bonus$index",
        content = "content$index",
        id = 0
    )
}