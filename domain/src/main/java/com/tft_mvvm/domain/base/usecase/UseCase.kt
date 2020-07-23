package com.tft_mvvm.domain.base.usecase

abstract class UseCase<Params : UseCaseParams, Result>() {

    suspend fun execute(params: Params): Result {
        return executeInternal(params)
    }
    protected abstract suspend fun executeInternal(params: Params):  Result
}