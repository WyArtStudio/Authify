package com.wahyuhw.authify.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(private val disposable: CompositeDisposable): ViewModel() {
    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed){
            disposable.dispose()
        }
    }
}