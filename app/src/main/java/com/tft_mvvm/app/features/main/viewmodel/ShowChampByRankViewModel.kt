package com.tft_mvvm.app.features.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.mapper.ChampMapper
import com.tft_mvvm.app.model.Champ
import com.tft_mvvm.data.common.AppDispatchers
import com.tft_mvvm.domain.features.usecase.GetListChampsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowChampByRankViewModel(
    private val listChampsUseCase: GetListChampsUseCase,
    private val appDispatchers: AppDispatchers,
    private val champMapper: ChampMapper
) : BaseViewModel() {

    private val champByRankLiveData: MutableLiveData<List<Champ>> =
        MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getChamps(isForceLoadData: Boolean) =
        viewModelScope.launch(appDispatchers.main) {
            isLoadingLiveData.value = true
            val champResult = withContext(appDispatchers.io) {
                listChampsUseCase.execute(
                    GetListChampsUseCase.GetAllChampUseCaseParam(
                        isForceLoadData
                    )
                )
            }
            champResult.either({
                champByRankLiveData.value = null
                isLoadingLiveData.value = false
            }) { (champs) ->
                champByRankLiveData.value =
                    champMapper.mapList(champs.sortedBy { champ -> champ.rankChamp })
                isLoadingLiveData.value = false
            }
        }

    fun getChampsLiveData(): LiveData<List<Champ>> {
        return champByRankLiveData
    }

    fun isRefresh(): LiveData<Boolean> {
        return isLoadingLiveData
    }
}