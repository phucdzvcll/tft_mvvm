package com.tft_mvvm.domain.features.champs.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ClassAndOriginListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class GetClassAndOriginContentUseCase(private val repoRepository: RepoRepository) :
    UseCase<GetClassAndOriginContentUseCase.GetClassAnOriginContentParam, Either<Failure, ClassAndOriginListEntity.ClassAndOrigin>>() {
    override suspend fun executeInternal(params: GetClassAnOriginContentParam): Either<Failure, ClassAndOriginListEntity.ClassAndOrigin> {
        return repoRepository.getClassAndOriginContent(
            isForceLoadData = params.isForceLoadData,
            classOrOriginName = params.classOrOriginName
        )
    }

    data class GetClassAnOriginContentParam(
        val isForceLoadData: Boolean,
        val classOrOriginName: String
    ) : UseCaseParams
}