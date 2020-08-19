package com.tft_mvvm.app.features.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.mapper.ChampMapper
import com.tft_mvvm.app.model.Champ
import com.tft_mvvm.domain.features.usecase.GetListChampsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowChampByRankViewModel(
    private val listChampsUseCase: GetListChampsUseCase,
    private val champMapper: ChampMapper
) : BaseViewModel() {

    private val champByRankLiveData: MutableLiveData<List<Champ>> =
        MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getChamps(isForceLoadData: Boolean) =
        viewModelScope.launch(Dispatchers.Main) {
            isLoadingLiveData.value = true
            val champResult = withContext(Dispatchers.IO) {
                listChampsUseCase.execute(
                    GetListChampsUseCase.GetAllChampUseCaseParam(
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