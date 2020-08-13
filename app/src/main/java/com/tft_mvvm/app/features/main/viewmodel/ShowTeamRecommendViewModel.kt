package com.tft_mvvm.app.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.main.adapter.AdapterShowRecommendTeamBuilder
import com.tft_mvvm.app.features.main.mapper.TeamBuilderRecommendMapper
import com.tft_mvvm.domain.features.usecase.GetListTeamBuilderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowTeamRecommendViewModel(
    private val getListTeamBuilderUseCase: GetListTeamBuilderUseCase,
    private val teamBuilderRecommendMapper: TeamBuilderRecommendMapper
) : BaseViewModel() {

    private val listTeamBuilderLiveData: MutableLiveData<List<AdapterShowRecommendTeamBuilder.TeamBuilder>> =
        MutableLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getListTeamBuilder(isForceLoadData: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        isLoading.value = true
        val dbResult = withContext(Dispatchers.IO) {
            getListTeamBuilderUseCase.execute(
                GetListTeamBuilderUseCase.GetTeamUseCaseParam(
                    isForceLoadData
                )
            )
        }
        dbResult.either({
            //TODO handle error
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