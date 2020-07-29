package com.tft_mvvm.data.features.champs.service

import com.tft_mvvm.data.features.champs.model.ChampListResponse
import retrofit2.http.GET

interface ChampApiService {
    @GET("/feeds/list/1A0SIKZRDM-poLdRGlc0Ov_a5gDsZyH_ojpzYX5gj85A/od6/public/values?alt=json")
    suspend fun getChampList() : ChampListResponse?
}