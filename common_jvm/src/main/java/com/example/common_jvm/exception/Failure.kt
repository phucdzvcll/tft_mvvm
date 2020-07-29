package com.example.common_jvm.exception

sealed class Failure {
    object InternetError : Failure()
    object ConnectionTimeout : Failure()
    data class ApiError(val httpCode: Int, val errorMsg: String) : Failure()
    data class ServerError(val errorCode: Int, val errorMsg: String) : Failure()
    data class UnCatchError(val exception: Exception) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}