package com.upnext.scribble.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String?) : Resource<T>(message = message)
    class Loading<T>(data: T? = null, message: String? = null) : Resource<T>(data, message)
}