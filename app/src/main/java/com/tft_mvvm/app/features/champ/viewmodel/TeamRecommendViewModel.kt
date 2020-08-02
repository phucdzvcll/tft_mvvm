package com.tft_mvvm.app.features.champ.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.champ.model.TeamBuilder
import com.tft_mvvm.app.mapper.TeamBuilderMapper
import com.tft_mvvm.app.mapper.TeamMapper
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.usecase.GetTeamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamRecommendViewModel(
    private val getTeamUseCase: GetTeamUseCase,
    private val teamBuilderMapper: TeamBuilderMapper
) : BaseViewModel() {
    private val listTeamBuilderLiveData: MutableLiveData<List<TeamBuilder>> = MutableLiveData()
    private val isLoadingLiveData:MutableLiveData<Boolean> = MutableLiveData(false)
    fun getTeams() =
        viewModelScope.launch(Dispatchers.Main) {
            isLoadingLiveData.value=true
            val champResult = withContext(Dispatchers.IO) {
                getTeamUseCase.execute(UseCaseParams.Empty)
            }
            champResult.either({
                listTeamBuilderLiveData.value = listOf()
                isLoadingLiveData.value=false
            }) { (teams) ->
                listTeamBuilderLiveData.value = teamBuilderMapper.mapList(teams)
                isLoadingLiveData.value=false
            }
        }

    fun getListTeamBuilderLiveData():LiveData<List<TeamBuilder>>{
        return listTeamBuilderLiveData
    }
    fun isLoading():LiveData<Boolean>{
        return isLoadingLiveData
    }
}