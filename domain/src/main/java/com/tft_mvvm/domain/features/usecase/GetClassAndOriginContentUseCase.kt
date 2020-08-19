package com.tft_mvvm.domain.features.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.model.ClassAndOriginListEntity
import com.tft_mvvm.domain.features.repository.RepoRepository

class GetClassAndOriginContentUseCase(private val repoRepository: RepoRepository) :
    UseCase<GetClassAndOriginContentUseCase.GetClassAnOriginContentParam, Either<Failure, ClassAndOriginListEntity>>() {
    override suspend fun executeInternal(params: GetClassAnOriginContentParam): Either<Failure, ClassAndOriginListEntity> {
        return repoRepository.getClassAndOriginContent(
            isForceLoadData = params.isForceLoadData,
            listClassOrOriginName = params.listClassOrOriginName
        )
    }

    data class GetClassAnOriginContentParam(
        val isForceLoadData: Boolean,
        val listClassOrOriginName: List<String>
    ) : UseCaseParams
}