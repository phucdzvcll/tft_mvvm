package com.tft_mvvm.app.features.champ.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.mapper.ChampMapper
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.mapper.LoadChampByRankMapper
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.usecase.GetChampsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val champsUseCase: GetChampsUseCase,
    private val loadChampByRankMapper: LoadChampByRankMapper,
    private val champListMapper: ChampMapper
) : BaseViewModel() {
    private val champLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val loadChampByRankLiveData: MutableLiveData<Map<String, List<Champ>>> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getChamps() =
        viewModelScope.launch(Dispatchers.Main) {
            isLoadingLiveData.value = true
            val champResult = withContext(Dispatchers.IO) {
                champsUseCase.execute(UseCaseParams.Empty)
            }
            champResult.either({
                //TODO error handle
                isLoadingLiveData.value = false
            }) { (champs) ->
                champLiveData.value = champListMapper.mapList(champs)
                loadChampByRankLiveData.value = loadChampByRankMapper.map(champs)
                isLoadingLiveData.value = false
            }
        }

    fun getChampsLiveData(): LiveData<List<Champ>> {
        return champLiveData
    }

    fun loadChampByRank(): LiveData<Map<String, List<Champ>>> {
        return loadChampByRankLiveData
    }

    fun isRefresh(): LiveData<Boolean> {
        return isLoadingLiveData
    }
}