package com.tft_mvvm.app.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.main.adapter.AdapterShowRecommendTeamBuilder
import com.tft_mvvm.app.features.main.mapper.TeamBuilderRecommendMapper
import com.tft_mvvm.data.common.AppDispatchers
import com.tft_mvvm.domain.features.usecase.GetListTeamBuilderUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowTeamRecommendViewModel(
    private val getListTeamBuilderUseCase: GetListTeamBuilderUseCase,
    private val appDispatchers: AppDispatchers,
    private val teamBuilderRecommendMapper: TeamBuilderRecommendMapper
) : BaseViewModel() {

    private val listTeamBuilderLiveData: MutableLiveData<List<AdapterShowRecommendTeamBuilder.TeamBuilder>> =
        MutableLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getListTeamBuilder(isForceLoadData: Boolean) = viewModelScope.launch(appDispatchers.main) {
        isLoading.value = true
        val dbResult = withContext(appDispatchers.io) {
            getListTeamBuilderUseCase.execute(
                GetListTeamBuilderUseCase.GetTeamUseCaseParam(
                    isForceLoadData
                )
            )
        }
        dbResult.either({
            listTeamBuilderLiveData.value = null
            isLoading.value = false
        }) {
            listTeamBuilderLiveData.value = teamBuilderRecommendMapper.mapList(it.teamBuilders)
            isLoading.value = false
        }
    }

    fun getListTeamBuilderLiveData(): LiveData<List<AdapterShowRecommendTeamBuilder.TeamBuilder>> {
        return listTeamBuilderLiveData
    }

    fun isRefresh(): LiveData<Boolean> {
        return isLoading
    }

}