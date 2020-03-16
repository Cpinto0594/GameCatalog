package com.cpinto.gamecatalog.modules.viewmodelmodule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    val uiScope = CoroutineScope(Dispatchers.Main)
    val ioScope = CoroutineScope(Dispatchers.IO)

    protected val loadingObserver: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> get() = loadingObserver

    fun handleLoadingObserver(visible: Boolean) {
        uiScope.launch { loadingObserver.value = visible }

    }

}