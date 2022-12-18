package com.arabin.retrofit.restapihelper


/**
 * @author Arabin
 * @since 12/16/2022
 * Gives api response
 * set data ans response state
 * */
data class RestAPIState<out T>(val status: RestAPIStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): RestAPIState<T> =
            RestAPIState(status = RestAPIStatus.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): RestAPIState<T> =
            RestAPIState(status = RestAPIStatus.ERROR, data = data, message = message)

        fun <T> loading(data: T?, message: String): RestAPIState<T> =
            RestAPIState(status = RestAPIStatus.LOADING, data = data, message = message)
    }
}