package com.tft_mvvm.app.features.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.main.adapter.AdapterShowByGold
import com.tft_mvvm.app.features.main.mapper.ChampByGoldMapper
import com.tft_mvvm.domain.features.usecase.GetChampsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowChampByGoldViewModel(
    private val champsUseCase: GetChampsUseCase,
    private val champByGoldMapper: ChampByGoldMapper
) : BaseViewModel() {

    private val champByGoldLiveData: MutableLiveData<List<AdapterShowByGold.ItemViewHolder.ChampByGold>> =
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
                champByGoldLiveData.value =
                    champByGoldMapper.mapList(champs.sortedBy { champ -> champ.cost })
                isLoadingLiveData.value = false
            }
        }

    fun getChampsLiveData(): LiveData<List<AdapterShowByGold.ItemViewHolder.ChampByGold>> {
        return champByGoldLiveData
    }

    fun isRefresh(): LiveData<Boolean> {
        return isLoadingLiveData
    }
}