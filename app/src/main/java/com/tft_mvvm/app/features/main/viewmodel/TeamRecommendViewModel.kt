package com.tft_mvvm.app.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel

import com.tft_mvvm.app.mapper.TeamBuilderMapper
import com.tft_mvvm.domain.features.usecase.GetListTeamBuilderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamRecommendViewModel(
    private val getListTeamBuilderUseCase: GetListTeamBuilderUseCase,
    private val teamBuilderMapper: TeamBuilderMapper
) : BaseViewModel() {
   // private val listTeamBuilderLiveData: MutableLiveData<List<TeamBuilder>> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getTeams(isForceLoadData: Boolean) =
        viewModelScope.launch(Dispatchers.Main) {
            isLoadingLiveData.value = true
            val champResult = withContext(Dispatchers.IO) {
                getListTeamBuilderUseCase.execute(
                    GetListTeamBuilderUseCase.GetTeamUseCaseParam(
                        isForceLoadData
                    )
                )
            }
            champResult.either({
             //   listTeamBuilderLiveData.value = listOf()
                isLoadingLiveData.value = false
            }) { (teams) ->
           //     listTeamBuilderLiveData.value = teamBuilderMapper.mapList(teams)
                isLoadingLiveData.value = false
            }
        }

//    fun getListTeamBuilderLiveData(): LiveData<List<TeamBuilder>> {
//        return listTeamBuilderLiveData
//    }

    fun isLoading(): LiveData<Boolean> {
        return isLoadingLiveData
    }
}