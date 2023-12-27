package com.linkan.artbookhilt.util

import retrofit2.Response

inline fun <T> safeApiCall(apiFun: () -> Response<T>?): Resource<T> {

    try {
        val response = apiFun.invoke()
        response?.takeIf { it.isSuccessful && it.body() != null }?.let {
            return Resource.success(data = response.body())
        }

        return Resource.error(response?.message() ?: "Error", null)
    } catch (exe: Exception) {
        return Resource.error(exe.message ?: "No Data", null)
    }
}
