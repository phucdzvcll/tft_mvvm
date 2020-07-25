package com.example.presentation.features.champs.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.presentation.base.BaseViewModel
import com.example.presentation.features.champs.model.model.Champ
import com.example.presentation.mapper.ChampMapper
import com.tft_mvvm.domain.features.champs.usecase.GetChampsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChampViewModel(

    private val champsUseCase: GetChampsUseCase,
    private val champListMapper: ChampMapper
) : BaseViewModel() {
    private val champLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getChamps(name: String, linkImg: String, coat: String) =
        viewModelScope.launch(Dispatchers.Main) {
            isLoadingLiveData.value = true
            val champResult = withContext(Dispatchers.IO) {
                champsUseCase.execute(
                    GetChampsUseCase.GetChampsUseCaseParam(
                        name = name,
                        linimg = linkImg,
                        coat = coat
                    )
                )
            }
            champLiveData.value = champListMapper.mapList(champResult.champs)
            isLoadingLiveData.value = false
        }

    fun getChampsLiveData(): LiveData<List<Champ>> {
        return champLiveData
    }

    fun isRefresh(): LiveData<Boolean> {
        return isLoadingLiveData
    }
}