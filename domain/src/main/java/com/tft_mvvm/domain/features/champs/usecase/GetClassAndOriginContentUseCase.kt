package com.tft_mvvm.domain.features.champs.usecase

import com.example.common_jvm.exception.Failure
import com.example.common_jvm.function.Either
import com.tft_mvvm.domain.base.usecase.UseCase
import com.tft_mvvm.domain.base.usecase.UseCaseParams
import com.tft_mvvm.domain.features.champs.model.ClassAndOriginListEntity
import com.tft_mvvm.domain.features.champs.repository.RepoRepository

class GetClassAndOriginContentUseCase(private val repoRepository: RepoRepository) :
    UseCase<GetClassAndOriginContentUseCase.GetClassAnOriginContentParam, Either<Failure, ClassAndOriginListEntity>>() {
    override suspend fun executeInternal(params: GetClassAnOriginContentParam): Either<Failure, ClassAndOriginListEntity> {
        return repoRepository.getClassAndOriginContent(
            classOrOriginName = params.classOrOriginName
        )
    }
    data class GetClassAnOriginContentParam(
        val classOrOriginName: String
    ) : UseCaseParams
}