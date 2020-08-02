package com.tft_mvvm.data.features.champs.service

import com.tft_mvvm.data.features.champs.model.ChampListResponse
import com.tft_mvvm.data.features.champs.model.TeamListResponse

import retrofit2.http.GET


interface ApiService {
    @GET("/feeds/list/1A0SIKZRDM-poLdRGlc0Ov_a5gDsZyH_ojpzYX5gj85A/od6/public/values?alt=json")
    suspend fun getChampList(): ChampListResponse?

    @GET("/feeds/list/1BZKHnl9UVjx03XCMv7FPqylGQX0Q5ncmd5kO1fFkntA/od6/public/values?alt=json")
    suspend fun getTeamList(): TeamListResponse?
}