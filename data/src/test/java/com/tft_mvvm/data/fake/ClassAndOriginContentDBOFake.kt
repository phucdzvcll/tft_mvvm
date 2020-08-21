package com.tft_mvvm.data.fake

import com.tft_mvvm.data.local.model.ClassAndOriginListDBO

object ClassAndOriginContentDBOFake {

    fun provideListClassAndOriginContentDBO(number : Int)= mutableListOf<ClassAndOriginListDBO.ClassAndOrigin>().apply {
        repeat(number){index -> add(provideClassAndOriginContentDBO(index))}
    }

    fun provideClassAndOriginContentDBO(index : Int =1)= ClassAndOriginListDBO.ClassAndOrigin(
        classOrOriginName = "name$index",
        content ="content$index",
        bonus = "bonus$index",
        id = 0
    )
}