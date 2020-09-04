package com.tft_mvvm.app.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tft_mvvm.app.base.BaseViewModel
import com.tft_mvvm.app.features.main.mapper.ClassAndOriginContentMapper
import com.tft_mvvm.app.features.main.model.ClassAndOriginContent
import com.tft_mvvm.data.common.AppDispatchers
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.usecase.GetAllClassAndOriginName
import com.tft_mvvm.domain.features.usecase.GetClassAndOriginContentUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowChampByRankViewModel(
    private val appDispatchers: AppDispatchers,
    private val getAllClassAndOriginName: GetAllClassAndOriginName,
    private val getClassAndOriginContentUseCase: GetClassAndOriginContentUseCase,
    private val classAndOriginContentMapper: ClassAndOriginContentMapper
) : BaseViewModel() {

    private val listClassAneOriginContentLiveData: MutableLiveData<List<ClassAndOriginContent>> =
        MutableLiveData()

    fun getAllClassAndOriginName() = viewModelScope.launch(appDispatchers.main) {
        val dbResult = withContext(appDispatchers.io) {
            getAllClassAndOriginName.execute(UseCaseParams.Empty)
        }
        dbResult.either({
            getListClassAndOriginContent(listOf())
        })
        {
            getListClassAndOriginContent(it)
        }
    }

    private fun getListClassAndOriginContent(listName: List<String>) =
        viewModelScope.launch(appDispatchers.main) {
            val dbResult = withContext(appDispatchers.io) {
                getClassAndOriginContentUseCase.execute(
                    GetClassAndOriginContentUseCase.GetClassAnOriginContentParam(
                        listName
                    )
                )
            }
            dbResult.either({
                listClassAneOriginContentLiveData.value = null
            })
            {
                listClassAneOriginContentLiveData.value =
                    classAndOriginContentMapper.mapList(it.listClassAndOrigin)
                        .sortedBy { classAndOrigin -> classAndOrigin.classOrOriginName }
            }
        }

    fun getChampsLiveData(): LiveData<List<ClassAndOriginContent>> {
        return listClassAneOriginContentLiveData
    }
}
