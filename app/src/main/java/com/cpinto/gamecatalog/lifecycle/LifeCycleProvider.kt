package com.cpinto.gamecatalog.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class LifeCycleProvider:LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)
    override fun getLifecycle() = lifecycleRegistry
}