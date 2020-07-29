package com.example.presentation.features.champs.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.presentation.base.BaseViewModel
import com.example.presentation.features.champs.model.model.Champ
import com.example.presentation.mapper.ChampMapper
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.usecase.GetChampsByClassUseCase
import com.tft_mvvm.domain.features.champs.usecase.GetChampsByOriginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val champsByClassUseCase: GetChampsByClassUseCase,
    private val champsByOriginUseCase: GetChampsByOriginUseCase,
    private val champListMapper: ChampMapper
) : BaseViewModel() {
    private val champByOriginLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val champByClasssLiveData: MutableLiveData<List<Champ>> = MutableLiveData()

    fun getChampsByOrigin(origin:String) =
        viewModelScope.launch(Dispatchers.Main) {
            val champResult = withContext(Dispatchers.IO) {
                champsByOriginUseCase.execute(
                    GetChampsByOriginUseCase.GetChampsByOriginUseCaseParam(
                        origin = origin
                    )
                )
            }
            champResult.either({
                champByOriginLiveData.value= listOf()
            }){
                val champs = champListMapper.mapList(it.champs)
                champByOriginLiveData.value = champs
            }
        }

    fun getChampsByClass(classs:String) =
        viewModelScope.launch(Dispatchers.Main) {
            val champResult = withContext(Dispatchers.IO) {
                champsByClassUseCase.execute(
                    GetChampsByClassUseCase.GetChampsByClassUseCaseParam(
                        classs = classs
                    )
                )
            }
            champResult.either({
                champByClasssLiveData.value= listOf()
            }){
                val champs = champListMapper.mapList(it.champs)
                champByClasssLiveData.value = champs
            }
        }

    fun getchampByOriginLiveData(): LiveData<List<Champ>> {
        return champByOriginLiveData
    }

    fun getchampByClassLiveData(): LiveData<List<Champ>> {
        return champByClasssLiveData
    }
}