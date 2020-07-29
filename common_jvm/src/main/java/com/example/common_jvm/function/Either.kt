package com.example.common_jvm.function

import com.example.common_jvm.exception.ExceptionInterceptor
import com.example.common_jvm.exception.Failure


/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out Fail, out SuccessResult> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Fail<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Success<out R>(val b: R) : Either<Nothing, R>()

    val isSuccess get() = this is Success<SuccessResult>
    val isFailure get() = this is Either.Fail<Fail>

    fun <Fail> fail(a: Fail) = Either.Fail(a)
    fun <SuccessResult> success(b: SuccessResult) = Either.Success(b)

    fun either(failAction: (Fail) -> Unit, successAction: (SuccessResult) -> Unit) {
        when (this) {
            is Either.Fail -> failAction(a)
            is Success -> successAction(b)
        }
    }

    companion object {
        suspend fun <SuccessResult> runSuspendWithCatchError(
            errorInterceptors: List<ExceptionInterceptor> = listOf(),
            action: suspend () -> (Either<Failure, SuccessResult>)
        ): Either<Failure, SuccessResult> {
            try {
                return action()
            } catch (e: Exception) {
                for (errorInterceptor: ExceptionInterceptor in errorInterceptors) {
                    val failure: Failure? = errorInterceptor.handleException(e)
                    if (failure != null) {
                        return Fail(failure)
                    }
                }

                return Fail(Failure.UnCatchError(e))
            }
        }
    }
}

// Credits to Alex Hart -> https://proandroiddev.com/kotlins-nothing-type-946de7d464fb
// Composes 2 functions
fun <A, B, C> ((A) -> B).compose(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Fail -> Either.Fail(a)
        is Either.Success -> fn(b)
    }

fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.compose(::success))