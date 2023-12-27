package com.linkan.artbookhilt.util

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

fun <T> CoroutineScope.runOnBackgroundDispatcher(
    startLoader : () -> Unit,
    backgroundFunc : suspend () -> T,
    callback : (T) -> Unit) {

    startLoader.invoke()
    this.launch(Dispatchers.IO) {
            Log.d("safeApiCall", "I'm working on Thread = ${Thread.currentThread().name}")
            val result = backgroundFunc.invoke()
            withContext(Dispatchers.Main){
                Log.d("safeApiCall", "I'm working on Thread = ${Thread.currentThread().name}")
                callback.invoke(result)
            }
    }
}
