package com.tft_mvvm.app.features.champ.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.mapper.ChampByGoldMapper
import com.tft_mvvm.app.ui.adapter.AdapterShowByGold
import com.tft_mvvm.domain.features.usecase.GetChampsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val champsUseCase: GetChampsUseCase,
    private val champByGoldMapper: ChampByGoldMapper
) : BaseViewModel() {
    private val champLiveData: MutableLiveData<List<AdapterShowByGold.ItemViewHolder.ChampByGold>> =
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
                champLiveData.value =
                    champByGoldMapper.mapList(champs.sortedBy { champByGold -> champByGold.cost })
                isLoadingLiveData.value = false
            }
        }

    fun getChampsLiveData(): LiveData<List<AdapterShowByGold.ItemViewHolder.ChampByGold>> {
        return champLiveData
    }

    fun isRefresh(): LiveData<Boolean> {
        return isLoadingLiveData
    }
}