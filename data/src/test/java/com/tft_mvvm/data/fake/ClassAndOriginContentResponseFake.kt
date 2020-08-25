package com.tft_mvvm.data.fake

import com.tft_mvvm.data.features.champs.remote.ClassAndOrigin
import com.tft_mvvm.data.features.champs.remote.GsxNameClassAndOrigin

object ClassAndOriginContentResponseFake {

    fun provideListClassAndOriginContentResponse(number: Int) =
        mutableListOf<ClassAndOrigin>().apply {
            repeat(number) { index ->
                add(
                    provideClassAndOriginContentResponse(
                        index
                    )
                )
            }
        }

    fun provideClassAndOriginContentResponse(index: Int = 1) = ClassAndOrigin(
        classOrOriginName = GsxNameClassAndOrigin("name$index"),
        bonus = GsxNameClassAndOrigin("bonus$index"),
        content = GsxNameClassAndOrigin("content$index")
    )
}