package com.tft_mvvm.app.features.champ.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.features.champ.model.ClassOrOrigin
import com.tft_mvvm.app.mapper.ChampMapper
import com.tft_mvvm.app.mapper.ClassOrOriginMapper
import com.tft_mvvm.domain.features.champs.usecase.GetChampsByClassUseCase
import com.tft_mvvm.domain.features.champs.usecase.GetChampsByOriginUseCase
import com.tft_mvvm.domain.features.champs.usecase.GetClassAndOriginContentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val champsByOriginUseCase: GetChampsByOriginUseCase,
    private val champsByClassUseCase: GetChampsByClassUseCase,
    private val classContent: GetClassAndOriginContentUseCase,
    private val originContent: GetClassAndOriginContentUseCase,
    private val classOrOriginMapper: ClassOrOriginMapper,
    private val champListMapper: ChampMapper
) : BaseViewModel() {
    private val champByOriginLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val champByClassLiveData: MutableLiveData<List<Champ>> = MutableLiveData()
    private val classContentLiveData: MutableLiveData<ClassOrOrigin> = MutableLiveData()
    private val originContentLiveData: MutableLiveData<ClassOrOrigin> = MutableLiveData()

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

    fun getClassContent(isForceLoadData: Boolean, classs: String) =
        viewModelScope.launch(Dispatchers.Main) {
            val dbResult = withContext(Dispatchers.IO) {
                classContent.execute(
                    GetClassAndOriginContentUseCase.GetClassAnOriginContentParam(
                        isForceLoadData,
                        classs
                    )
                )
            }
            dbResult.either({
                //TODO error handle
            })
            {
                classContentLiveData.value = classOrOriginMapper.map(it)
            }
        }

    fun getOriginContent(isForceLoadData: Boolean, origin: String) =
        viewModelScope.launch(Dispatchers.Main) {
            val dbResult = withContext(Dispatchers.IO) {
                originContent.execute(
                    GetClassAndOriginContentUseCase.GetClassAnOriginContentParam(
                        isForceLoadData,
                        origin
                    )
                )
            }
            dbResult.either({
                //TODO error handle
            })
            {
                originContentLiveData.value = classOrOriginMapper.map(it)
            }
        }

    fun getClassContentLiveData(): LiveData<ClassOrOrigin> {
        return classContentLiveData
    }

    fun getOriginContentLiveData(): LiveData<ClassOrOrigin> {
        return originContentLiveData
    }

    fun getChampsByOriginLiveData(): LiveData<List<Champ>> {
        return champByOriginLiveData
    }

    fun getChampsByClassLiveData(): LiveData<List<Champ>> {
        return champByClassLiveData
    }

}