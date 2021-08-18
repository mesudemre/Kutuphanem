package com.mesutemre.kutuphanem.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * @Author: mesutemre.celenk
 * @Date: 17.08.2021
 */
abstract class BaseViewModel(application: Application)
    :AndroidViewModel(application),CoroutineScope {

    val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main;

    open val context:Context = application.baseContext;

    abstract val disposible: CompositeDisposable;

    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }
}