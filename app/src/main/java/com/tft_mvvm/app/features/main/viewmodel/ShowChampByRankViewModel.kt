package com.tft_mvvm.app.features.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.main.adapter.AdapterShowChampByRank
import com.tft_mvvm.app.features.main.mapper.ChampByRankMapper
import com.tft_mvvm.domain.features.usecase.GetChampsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowChampByRankViewModel(
    private val champsUseCase: GetChampsUseCase,
    private val champByRankMapper: ChampByRankMapper
) : BaseViewModel() {

    private val champByRankLiveData: MutableLiveData<List<AdapterShowChampByRank.ChampByRank>> =
        MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getChamps(isForceLoadData: Boolean) =
        viewModelScope.launch(Dispatchers.Main) {
            isLoadingLiveData.value = true
            val champResult = withContext(Dispatchers.IO) {
                champsUseCase.execute(
                    GetChampsUseCase.GetAllChampUseCaseParam(
                        isForceLoadData
                    )
                )
            }
            champResult.either({
                //TODO error handle
                isLoadingLiveData.value = false
            }) { (champs) ->
                Log.d("PHUC", "$champs")
                champByRankLiveData.value =
                    champByRankMapper.mapList(champs.sortedByDescending { champ -> champ.rankChamp })
                isLoadingLiveData.value = false
            }
        }

    fun getChampsLiveData(): LiveData<List<AdapterShowChampByRank.ChampByRank>> {
        return champByRankLiveData
    }

    fun isRefresh(): LiveData<Boolean> {
        return isLoadingLiveData
    }
}