package com.wahyuhw.authify.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wahyuhw.authify.domain.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> LiveData<Resource<T>>.observe(
	lifecycleOwner: LifecycleOwner,
	onLoading: () -> Unit,
	onSuccess: (items: T?) -> Unit,
	onError: (errorMessage: String) -> Unit,
) {
	observe(lifecycleOwner) {
		when (it) {
			is Resource.Default<T> -> {
				//Do nothing
			}
			is Resource.Loading<T> -> onLoading.invoke()
			is Resource.Success<T> -> onSuccess.invoke(it.data)
			else -> onError.invoke(it.errorMessage.orEmpty())
		}
	}
}

fun <T> CoroutineScope.collectResult(liveData: MutableLiveData<T>, block: suspend () -> Flow<T>) {
	this.launch {
		val result = block.invoke()
		result.collect {
			liveData.postValue(it)
		}
	}
}