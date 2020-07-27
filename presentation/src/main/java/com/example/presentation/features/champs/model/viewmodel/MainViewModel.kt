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
import com.tft_mvvm.domain.features.champs.usecase.GetChampsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(

    private val champsUseCase: GetChampsUseCase,
    private val champsByOriginUseCase: GetChampsByOriginUseCase,
    private val champsByClassUseCase: GetChampsByClassUseCase,
    private val champListMapper: ChampMapper
) : BaseViewModel() {
    private val champLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val champByOriginLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val champByClassLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getChamps(
    ) =
        viewModelScope.launch(Dispatchers.Main) {
            isLoadingLiveData.value = true
            val champResult = withContext(Dispatchers.IO) {
                champsUseCase.execute(UseCaseParams.Empty)
            }
            champLiveData.value = champListMapper.mapList(champResult.champs)
            isLoadingLiveData.value = false
        }

    fun getChampByOrigin(
        origin:String
    ){
        viewModelScope.launch(Dispatchers.Main) {
            isLoadingLiveData.value = true
            val champResult = withContext(Dispatchers.IO) {
                champsByOriginUseCase.execute(
                    GetChampsByOriginUseCase.GetChampsByOriginUseCaseParam(
                        origin = origin
                    )
                )
            }

            val champs = champListMapper.mapList(champResult.champs)
            champByOriginLiveData.value=champs
        }
    }
    fun getChampByClasss(
        classs:String
    ){
        viewModelScope.launch(Dispatchers.Main) {

            val champResult = withContext(Dispatchers.IO) {
                champsByClassUseCase.execute(
                    GetChampsByClassUseCase.GetChampsByClassUseCaseParam(
                        classs = classs
                    )
                )
            }
            val champs = champListMapper.mapList(champResult.champs)
            champByClassLiveData.value=champs
        }
    }

    fun getChampsLiveData(): LiveData<List<Champ>> {
        return champLiveData
    }
    fun getChampsByOriginLiveData():LiveData<List<Champ>>{
        return champByOriginLiveData
    }
    fun getChampsByClassLiveData():LiveData<List<Champ>>{
        return champByClassLiveData
    }
    fun isRefresh(): LiveData<Boolean> {
        return isLoadingLiveData
    }
}