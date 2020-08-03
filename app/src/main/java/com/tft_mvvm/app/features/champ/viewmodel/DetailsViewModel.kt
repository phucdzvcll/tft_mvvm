package com.tft_mvvm.app.features.champ.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.mapper.ChampMapper
import com.tft_mvvm.domain.features.champs.usecase.GetChampsByClassUseCase
import com.tft_mvvm.domain.features.champs.usecase.GetChampsByOriginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val champsByOriginUseCase: GetChampsByOriginUseCase,
    private val champsByClassUseCase: GetChampsByClassUseCase,
    private val champListMapper: ChampMapper
) : BaseViewModel() {
    private val champByOriginLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val champByClassLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val isLoadingByClassLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isLoadingByOriginLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getChampsByOrigin(origin: String) =
        viewModelScope.launch(Dispatchers.Main) {
            val champResult = withContext(Dispatchers.IO) {
                champsByOriginUseCase.execute(
                    GetChampsByOriginUseCase.GetChampsByOriginUseCaseParam(
                        origin = origin
                    )
                )
            }
            champResult.either(
                {
                    //TODO error handle
                })
            {
                val champs = champListMapper.mapList(it.champs)
                champByOriginLiveData.value = champs
            }
        }

    fun getChampsByClass(classs: String) =
        viewModelScope.launch(Dispatchers.Main) {
            val champResult = withContext(Dispatchers.IO) {
                champsByClassUseCase.execute(
                    GetChampsByClassUseCase.GetChampsByClassUseCaseParam(
                        classs = classs
                    )
                )
            }
            champResult.either(
                {
                    //TODO error handle
                })
            {
                val champs = champListMapper.mapList(it.champs)
                champByClassLiveData.value = champs
            }
        }

    fun getChampsByOriginLiveData(): LiveData<List<Champ>> {
        return champByOriginLiveData
    }

    fun getChampsByClassLiveData(): LiveData<List<Champ>> {
        return champByClassLiveData
    }

    fun isLoadingByClass(): LiveData<Boolean> {
        return isLoadingByClassLiveData
    }

    fun isLoadingByOrigin(): LiveData<Boolean> {
        return isLoadingByOriginLiveData
    }
}