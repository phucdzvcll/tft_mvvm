package com.tft_mvvm.data.features.champs.service

import com.tft_mvvm.data.features.champs.remote.ChampListResponse
import com.tft_mvvm.data.features.champs.remote.ClassAndOriginListResponse
import com.tft_mvvm.data.features.champs.remote.ItemListResponse
import com.tft_mvvm.data.features.champs.remote.TeamListResponse
import retrofit2.http.GET


interface ApiService {
    @GET("/feeds/list/1A0SIKZRDM-poLdRGlc0Ov_a5gDsZyH_ojpzYX5gj85A/od6/public/values?alt=json")
    suspend fun getChampList(): ChampListResponse?

    @GET("/feeds/list/1A0SIKZRDM-poLdRGlc0Ov_a5gDsZyH_ojpzYX5gj85A/oofczet/public/values?alt=json")
    suspend fun getTeamList(): TeamListResponse?

    @GET("/feeds/list/1A0SIKZRDM-poLdRGlc0Ov_a5gDsZyH_ojpzYX5gj85A/os462da/public/values?alt=json")
    suspend fun getClassAndOriginList(): ClassAndOriginListResponse?

    @GET("/feeds/list/1A0SIKZRDM-poLdRGlc0Ov_a5gDsZyH_ojpzYX5gj85A/otafl6o/public/values?alt=json")
    suspend fun getItemListResponse(): ItemListResponse?
}