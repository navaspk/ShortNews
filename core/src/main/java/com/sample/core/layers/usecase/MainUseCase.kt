package com.sample.core.layers.usecase

import com.google.gson.Gson
import com.sample.core.layers.entity.*
import com.sample.core.layers.controller.ItemResult
import com.sample.core.extensions.safeGet
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

abstract class MainUseCase<T : BaseResponse, Params> {

    companion object {
        private const val SOMETHING_WENT_WRONG =
            "Something went wrong , please try again after some time"
    }

    abstract suspend fun createUseCase(params: Params?): Response<T>

    suspend fun execute(
        params: Params? = null,
    ): ItemResult<T> {

        try {
            val response = createUseCase(params)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ItemResult.Success(it)
                }
                return error(" ${handleNetworkRequestError(HttpException(response))}")
            } else {
                return error(" ${handleNetworkRequestError(HttpException(response))}")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }


    private fun handleNetworkRequestError(
        exception: Throwable
    ): String {
        var errorResponse: BaseResponse = BaseError()
        when (exception) {
            is HttpException -> {
                exception.response()?.errorBody().run {
                    val error = this?.string().safeGet()

                    errorResponse = when (exception.response()?.code()) {
                        429 -> handleResponseError(
                            error,
                            ResponseErrors.HTTP_TOO_MANY_REQUEST
                        )
                        HttpURLConnection.HTTP_INTERNAL_ERROR -> handleResponseError(
                            error,
                            ResponseErrors.INTERNAL_SERVER_ERROR
                        )
                        HttpURLConnection.HTTP_UNAUTHORIZED -> handleResponseError(
                            error,
                            ResponseErrors.HTTP_UNAUTHORIZED
                        )
                        else -> handleResponseError(
                            error = error
                        )
                    }
                }
            }

            is ServerNotAvailableException -> {
                errorResponse = BaseError(
                    errorMessage = "Server not available",
                    errorCode = ResponseErrors.HTTP_UNAVAILABLE
                )
            }

            is HTTPNotFoundException -> {
                errorResponse = BaseError(
                    errorMessage = "Server not available",
                    errorCode = ResponseErrors.HTTP_NOT_FOUND
                )
            }

            is UnknownHostException,
            is NetworkException,
            is ConnectException,
            -> {
                BaseError(
                    errorMessage = "Internet not available",
                    errorCode = ResponseErrors.CONNECTIVITY_EXCEPTION
                )
            }

            is IOException,
            is TimeoutException,
            -> {
                errorResponse = if (exception.localizedMessage != null) {
                    BaseError(
                        errorMessage = exception.localizedMessage!!,
                        errorCode = ResponseErrors.UNKNOWN_EXCEPTION
                    )
                } else {
                    BaseError(
                        errorMessage = SOMETHING_WENT_WRONG,
                        errorCode = ResponseErrors.UNKNOWN_EXCEPTION
                    )
                }
            }

            is HTTPBadRequest -> {
                BaseError(
                    errorMessage = SOMETHING_WENT_WRONG,
                    errorCode = ResponseErrors.HTTP_BAD_REQUEST
                )

            }
        }

        return errorResponse.message
    }

    private fun getErrorMessageFromResponse(error: String): String {
        var errorMessage: String
        try {
            val baseError = Gson().fromJson(
                error,
                BaseError::class.java
            )
            if (baseError != null) {
                baseError.apply {
                    errorMessage = this.message.ifBlank {
                        this.errorMessage
                    }
                }
            } else {
                errorMessage = SOMETHING_WENT_WRONG
            }

        } catch (e: Exception) {
            errorMessage = SOMETHING_WENT_WRONG
        }
        return errorMessage
    }

    private fun handleResponseError(
        error: String,
        errorCode: ResponseErrors = ResponseErrors.RESPONSE_ERROR,
    ): BaseResponse {

        val errorResponse = if (error.contains("encryptedData")) {
            Gson().fromJson(
                error,
                EncryptedResponse::class.java
            )

        } else {
            BaseError(
                errorBody = error,
                errorMessage = getErrorMessageFromResponse(error),
                errorCode = errorCode
            )
        }

        return errorResponse
    }

}
