package com.tft_mvvm.data

import com.tft_mvvm.data.fake.ClassAndOriginDBOFake
import org.junit.Test

class ExampleTest {

    @Test
    fun test(){

        val s =ClassAndOriginDBOFake.provideClassAndOriginDBO(0)
        println(s)
    }
}